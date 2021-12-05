package com.demoachitecture.remote.data.profile

import com.demoachitecture.remote.ApiConstants
import com.demoachitecture.remote.base.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {
    @GET(ApiConstants.Profile.GET_PROFILE_BY_ID)
    suspend fun getProfileBy(@Path("id") id: String): ApiResponse<ProfileModel>
}