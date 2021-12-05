package com.demoachitecture.common.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Singleton

@Singleton
class TokenExpiredHandler {
    private val _tokenExpiredFlow = MutableStateFlow(false)
    val tokenExpiredFlow: StateFlow<Boolean> = _tokenExpiredFlow

    fun setTokenExpired(isTokenExpired: Boolean) {
        _tokenExpiredFlow.value = isTokenExpired
    }
}