package com.dartmedia.iotinfusionmonitoringapp.presentation.activity.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient.AddPatientRequest
import com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase.PatientUseCase
import com.dartmedia.iotinfusionmonitoringapp.utils.ErrorType
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormPatientViewModel @Inject constructor(private val patientUseCase: PatientUseCase): ViewModel() {

    private val _resultAdd = MutableLiveData<String>()
    val resultAdd: LiveData<String> get() = _resultAdd

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<ErrorType>()
    val error: LiveData<ErrorType> get() = _error

    fun addPatient(addPatientRequest: AddPatientRequest) {
        _isLoading.value = true

        viewModelScope.launch {
            patientUseCase.addPatient(addPatientRequest).collect {
                _isLoading.value = false
                when (it) {
                    is ResultState.Success -> _resultAdd.postValue(it.data)
                    is ResultState.Failed -> _error.postValue(ErrorType(it.code, it.error))
                    else -> {}
                }
            }
        }
    }

}