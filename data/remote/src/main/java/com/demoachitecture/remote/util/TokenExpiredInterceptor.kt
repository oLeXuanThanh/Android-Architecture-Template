package com.demoachitecture.remote.util

import com.demoachitecture.common.network.TokenExpiredHandler
import okhttp3.Interceptor
import okhttp3.Response

class TokenExpiredInterceptor(private val tokenExpiredHandler: TokenExpiredHandler): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val initialResponse = chain.proceed(originalRequest)
        when (initialResponse.code) {
            403, 401 -> {
                tokenExpiredHandler.setTokenExpired(true)
                throw Exception("Section token expired")
            }
            else -> return initialResponse
        }
    }
}