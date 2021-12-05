package com.demoachitecture.remote.data.friend

import com.demoachitecture.remote.ApiConstants
import com.demoachitecture.remote.base.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FriendService {
    @GET(ApiConstants.Friend.GET_FRIEND_BY_ID)
    suspend fun getFriendBy(@Path("id") id: String): Response<ApiResponse<FriendModel>>
}