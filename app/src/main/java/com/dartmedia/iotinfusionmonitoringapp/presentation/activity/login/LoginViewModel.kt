package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.login.LoginRequest
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.Login
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.usecase.AuthUseCase
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authUseCase: AuthUseCase): ViewModel() {

    private val _resultLogin = MutableLiveData<Login>()
    val resultLogin: LiveData<Login> get() = _resultLogin

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun doLogin(id: String, password: String) {
        _isLoading.value = true
        val loginRequest = LoginRequest(id, password)

        viewModelScope.launch {
            authUseCase.login(loginRequest).collect {
                _isLoading.value = false
                when (it) {
                    is ResultState.Success -> _resultLogin.postValue(it.data)
                    is ResultState.Failed -> _error.postValue(it.error)
                    else -> {}
                }
            }
        }
    }

}