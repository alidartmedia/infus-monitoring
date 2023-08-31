package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.detaildevice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceById
import com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase.DeviceUseCase
import com.dartmedia.iotinfusionmonitoringapp.utils.ErrorType
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailDeviceViewModel @Inject constructor(private val deviceUseCase: DeviceUseCase): ViewModel() {

    private val _resultDeviceById = MutableLiveData<DeviceById>()
    val resultDeviceById: LiveData<DeviceById> get() = _resultDeviceById

    private val _error = MutableLiveData<ErrorType>()
    val error: LiveData<ErrorType> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getDeviceById(id: String) {
        _isLoading.value = true

        viewModelScope.launch {
            deviceUseCase.getDeviceById(id).collect {
                _isLoading.value = false
                when (it) {
                    is ResultState.Success -> _resultDeviceById.postValue(it.data)
                    is ResultState.Failed -> _error.postValue(ErrorType(it.code, it.error))
                    else -> {}
                }
            }
        }
    }

}