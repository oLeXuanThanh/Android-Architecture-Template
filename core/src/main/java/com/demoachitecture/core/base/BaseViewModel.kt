package com.demoachitecture.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demoachitecture.common.network.TokenExpiredHandler
import com.demoachitecture.core.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val tokenExpiredHandler: TokenExpiredHandler): ViewModel() {

    // FOR TOKEN EXPIRED HANDLER
    private val _tokenExpired = MutableStateFlow(Event(false))
    val tokenExpired: StateFlow<Event<Boolean>> get() = _tokenExpired

    init {
        observeTokenExpired()
    }

    private fun observeTokenExpired() {
        viewModelScope.launch(Dispatchers.IO) {
            tokenExpiredHandler.tokenExpiredFlow.collect { isExpired ->
                _tokenExpired.value = Event(isExpired)
            }

        }
    }

}