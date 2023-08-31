package com.dartmedia.iotinfusionmonitoringapp.data

import com.dartmedia.iotinfusionmonitoringapp.data.remote.api.AuthApiService
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.login.LoginRequest
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.login.LoginResponse
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.logout.LogoutRequest
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.logout.LogoutResponse
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.Login
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.model.Logout
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.repository.AuthRepository
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ImplAuthRepository @Inject constructor(private val authApiService: AuthApiService):
    AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): Flow<ResultState<Login>> {
        return flow {
            try {
                val response = authApiService.login(loginRequest)
                if (response.isSuccessful) {
                    val data = response.body()!!.toDomain()
                    emit(ResultState.Success(data))
                } else {
                    response.errorBody()?.let {
                        val er = Gson().fromJson(it.charStream(), LoginResponse::class.java)
                        emit(ResultState.Failed(er.message.toString(), response.code()))
                    }
                }
            } catch (e: Exception) {
                emit(ResultState.Failed("Unknown error. Please try again.", null))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun logout(logoutRequest: LogoutRequest): Flow<ResultState<Logout>> {
        return flow {
            try {
                val response = authApiService.logout(logoutRequest)
                if (response.isSuccessful) {
                    val data = response.body()!!.toDomain()
                    emit(ResultState.Success(data))
                } else {
                    response.errorBody()?.let {
                        val er = Gson().fromJson(it.charStream(), LogoutResponse::class.java)
                        emit(ResultState.Failed(er.message.toString(), response.code()))
                    }
                }
            } catch (e: Exception) {
                emit(ResultState.Failed("Unknown error. Please try again.", null))
            }
        }.flowOn(Dispatchers.IO)
    }

}