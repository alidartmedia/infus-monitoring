package com.dartmedia.iotinfusionmonitoringapp.presentation.fragment.onlinedevice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceCount
import com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase.DeviceUseCase
import com.dartmedia.iotinfusionmonitoringapp.utils.ErrorType
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllOnlineDeviceViewModel @Inject constructor(private val deviceUseCase: DeviceUseCase): ViewModel() {

    //Getting data from paging 3
    val getAllOnlineDevicePagination = deviceUseCase.getAllOnlineDevicePagination().cachedIn(viewModelScope)

    private val _resultCountAllOnlineDevice = MutableLiveData<DeviceCount>()
    val resultCountAllOnlineDevice: LiveData<DeviceCount> get() = _resultCountAllOnlineDevice

    private val _error = MutableLiveData<ErrorType>()
    val error: LiveData<ErrorType> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getCountAllOnlineDevice() {
        _isLoading.value = true

        viewModelScope.launch {
            deviceUseCase.getCountAllOnlineDevice().collect {
                when (it) {
                    is ResultState.Success -> _resultCountAllOnlineDevice.postValue(it.data)
                    is ResultState.Failed -> {
                        _error.postValue(ErrorType(it.code, it.error))
                        _isLoading.value = false
                    }
                    else -> {}
                }
            }
        }
    }

}