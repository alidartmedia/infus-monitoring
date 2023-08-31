package com.dartmedia.iotinfusionmonitoringapp.domain.main.repository

import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient.AddPatientRequest
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface PatientRepository {

    suspend fun addPatient(addPatientRequest: AddPatientRequest): Flow<ResultState<String>>

}