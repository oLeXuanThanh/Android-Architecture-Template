package com.demoachitecture.repository.data.profile

import com.demoachitecture.common.network.Resource
import com.demoachitecture.domain.profile.Profile
import com.demoachitecture.domain.profile.ProfileRepository
import com.demoachitecture.local.data.profile.ProfileDao
import com.demoachitecture.remote.data.profile.ProfileModel
import com.demoachitecture.remote.data.profile.ProfileService
import com.demoachitecture.remote.util.RateLimiter
import com.demoachitecture.remote.util.safeApiCall
import com.demoachitecture.repository.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileService: ProfileService,
    private val profileDao: ProfileDao
): ProfileRepository {

    private val profileRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    override suspend fun getProfile(id: String): Flow<Resource<Profile>> {
        return networkBoundResource<Profile, Resource<ProfileModel>>(
            loadFromDb = { getLocalProfileBy(id) },
            fetchRemote = { fetchRemote(id) },
            saveFetchResult = { response ->
                val profile = response.data?.toProfileEntity()
                if (profile != null) {
                    profileDao.insert(profile)
                }
            },
            shouldFetch = { profile: Profile? ->
                profile == null || profileRateLimit.shouldFetch(id)
            },
            onFetchFailed = {
                profileRateLimit.reset(id)
            },
            clearData = {}
        )
    }

    private fun getLocalProfileBy(id: String): Flow<Profile> = flow {
        emit(profileDao.getProfileBy(id).toProfile())
    }

    private suspend fun fetchRemote(id: String): Resource<ProfileModel> {
        return safeApiCall(profileService.getProfileBy(id))
    }

}