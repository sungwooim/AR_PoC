package com.example.ar_poc.di

import com.example.ar_poc.Config
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevModeManager @Inject constructor() {
    private val _devModeState = MutableStateFlow(Config.DEV_MODE)
    val devModeState = _devModeState.asStateFlow()

    val isDevMode: Boolean get() = _devModeState.value

    fun toggle() {
        _devModeState.value = !_devModeState.value
    }
}
