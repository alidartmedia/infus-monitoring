package com.dartmedia.iotinfusionmonitoringapp.data

import com.dartmedia.iotinfusionmonitoringapp.data.remote.api.MainApiService
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.login.LoginResponse
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient.AddPatientRequest
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient.AddPatientResponse
import com.dartmedia.iotinfusionmonitoringapp.domain.main.repository.PatientRepository
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ImplPatientRepository @Inject constructor(private val mainApiService: MainApiService): PatientRepository {

    override suspend fun addPatient(addPatientRequest: AddPatientRequest): Flow<ResultState<String>> {
        return flow {
            try {
                val response = mainApiService.addPatient(addPatientRequest)
                if (response.isSuccessful) {
                    val data = response.body()?.message
                    emit(ResultState.Success(data!!))
                } else {
                    response.errorBody()?.let {
                        val er = Gson().fromJson(it.charStream(), AddPatientResponse::class.java)
                        emit(ResultState.Failed(er.message.toString(), response.code()))
                    }
                }
            } catch (e: Exception) {
                emit(ResultState.Failed("Unknown error. Please try again.", null))
            }
        }.flowOn(Dispatchers.IO)
    }

}