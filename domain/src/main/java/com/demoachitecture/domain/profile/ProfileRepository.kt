package com.demoachitecture.domain.profile

import com.demoachitecture.common.network.Resource
import kotlinx.coroutines.flow.Flow


interface ProfileRepository {

    suspend fun getProfile(id: String): Flow<Resource<Profile>>
}