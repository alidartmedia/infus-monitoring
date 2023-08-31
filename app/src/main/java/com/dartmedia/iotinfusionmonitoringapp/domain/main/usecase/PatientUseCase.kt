package com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase

import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient.AddPatientRequest
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface PatientUseCase {

    suspend fun addPatient(addPatientRequest: AddPatientRequest): Flow<ResultState<String>>

}