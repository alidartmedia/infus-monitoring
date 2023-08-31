package com.dartmedia.iotinfusionmonitoringapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dartmedia.iotinfusionmonitoringapp.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(private val sessionManager: SessionManager): ViewModel() {

    private val _accessToken = MutableLiveData<String?>()
    val accessToken: LiveData<String?> get() = _accessToken

    private val _refreshToken = MutableLiveData<String?>()
    val refreshToken: LiveData<String?> get() = _refreshToken

    init {
        _accessToken.value = sessionManager.fetchAccessToken()
        _refreshToken.value = sessionManager.fetchRefreshToken()
    }

    fun saveRefreshToken(token: String) {
        sessionManager.saveRefreshToken(token)
    }

    fun deleteRefreshToken() {
        sessionManager.deleteRefreshToken()
    }

    fun saveAccessToken(token: String) {
        sessionManager.saveAccessToken(token)
    }

    fun deleteAccessToken() {
        sessionManager.deleteAccessToken()
    }

}