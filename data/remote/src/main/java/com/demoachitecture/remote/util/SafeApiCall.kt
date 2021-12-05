package com.demoachitecture.remote.util

import com.demoachitecture.common.network.Resource
import com.demoachitecture.remote.base.*

inline fun <reified T : Any> safeApiCall(apiResponse: ApiResponse<T>): Resource<T> {
    return when(apiResponse) {
        is ApiSuccessResponse -> {
            apiResponse.body is String
            Resource.Success(apiResponse.body)
        }
        is ApiEmptyResponse -> { // using Any type is the best choice
            Resource.Success(T::class.java.newInstance())
        }
        is ApiErrorResponse -> {
            Resource.Error(apiResponse.errorMessage)
        }
    }
}

