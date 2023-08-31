package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.logout.LogoutRequest
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.Logout
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.usecase.AuthUseCase
import com.dartmedia.iotinfusionmonitoringapp.utils.ErrorType
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val authUseCase: AuthUseCase): ViewModel() {

    private val _resultLogout = MutableLiveData<Logout>()
    val resultLogout: LiveData<Logout> get() = _resultLogout

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<ErrorType>()
    val error: LiveData<ErrorType> get() = _error

    fun doLogout(token: String) {
        _isLoading.value = true
        val logoutRequest = LogoutRequest(token)

        viewModelScope.launch {
            authUseCase.logout(logoutRequest).collect {
                _isLoading.value = false
                when (it) {
                    is ResultState.Success -> _resultLogout.postValue(it.data)
                    is ResultState.Failed -> _error.postValue(ErrorType(it.code, it.error))
                    else -> {}
                }
            }
        }
    }

}