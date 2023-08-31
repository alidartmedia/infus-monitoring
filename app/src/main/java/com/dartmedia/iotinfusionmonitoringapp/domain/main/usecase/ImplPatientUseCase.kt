package com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase

import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient.AddPatientRequest
import com.dartmedia.iotinfusionmonitoringapp.domain.main.repository.PatientRepository
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImplPatientUseCase @Inject constructor(private val patientRepository: PatientRepository): PatientUseCase {

    override suspend fun addPatient(addPatientRequest: AddPatientRequest): Flow<ResultState<String>> {
        return patientRepository.addPatient(addPatientRequest)
    }

}