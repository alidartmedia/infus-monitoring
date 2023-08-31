package com.dartmedia.iotinfusionmonitoringapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dartmedia.iotinfusionmonitoringapp.data.paging.AllDevicePagingSource
import com.dartmedia.iotinfusionmonitoringapp.data.paging.AllOfflineDevicePagingSource
import com.dartmedia.iotinfusionmonitoringapp.data.paging.AllOnlineDevicePagingSource
import com.dartmedia.iotinfusionmonitoringapp.data.remote.api.MainApiService
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.login.LoginResponse
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.AllDeviceResponse
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.DeviceByIdResponse
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.ProductsItemAllDeviceResponse
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceById
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceCount
import com.dartmedia.iotinfusionmonitoringapp.domain.main.repository.DeviceRepository
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ImplDeviceRepository @Inject constructor(private val mainApiService: MainApiService): DeviceRepository {

    override fun getAllDevicePagination(): Flow<PagingData<ProductsItemAllDeviceResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AllDevicePagingSource(mainApiService)
            }
        ).flow
    }

    override fun getAllOfflineDevicePagination(): Flow<PagingData<ProductsItemAllDeviceResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AllOfflineDevicePagingSource(mainApiService)
            }
        ).flow
    }

    override fun getAllOnlineDevicePagination(): Flow<PagingData<ProductsItemAllDeviceResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                AllOnlineDevicePagingSource(mainApiService)
            }
        ).flow
    }

    override suspend fun getCountAllDevice(): Flow<ResultState<DeviceCount>> {
        return flow {
            try {
                val response = mainApiService.getAllDevice()
                if (response.isSuccessful) {
                    val data = response.body()!!.toDomainDeviceCount()
                    emit(ResultState.Success(data))
                } else {
                    response.errorBody()?.let {
                        val er = Gson().fromJson(it.charStream(), AllDeviceResponse::class.java)
                        emit(ResultState.Failed(er.message.toString(), response.code()))
                    }
                }
            } catch (e: Exception) {
                emit(ResultState.Failed("Unknown error. Please try again.", null))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCountAllOfflineDevice(): Flow<ResultState<DeviceCount>> {
        return flow {
            try {
                val response = mainApiService.getAllOfflineDevice()
                if (response.isSuccessful) {
                    val data = response.body()!!.toDomainDeviceCount()
                    emit(ResultState.Success(data))
                } else {
                    response.errorBody()?.let {
                        val er = Gson().fromJson(it.charStream(), AllDeviceResponse::class.java)
                        emit(ResultState.Failed(er.message.toString(), response.code()))
                    }
                }
            } catch (e: Exception) {
                emit(ResultState.Failed("Unknown error. Please try again.", null))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCountAllOnlineDevice(): Flow<ResultState<DeviceCount>> {
        return flow {
            try {
                val response = mainApiService.getAllOnlineDevice()
                if (response.isSuccessful) {
                    val data = response.body()!!.toDomainDeviceCount()
                    emit(ResultState.Success(data))
                } else {
                    response.errorBody()?.let {
                        val er = Gson().fromJson(it.charStream(), AllDeviceResponse::class.java)
                        emit(ResultState.Failed(er.message.toString(), response.code()))
                    }
                }
            } catch (e: Exception) {
                emit(ResultState.Failed("Unknown error. Please try again.", null))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDeviceById(id: String): Flow<ResultState<DeviceById>> {
        return flow {
            try {
                val response = mainApiService.getDeviceById(id)
                if (response.isSuccessful) {
                    val data = response.body()!!.toDomain()
                    emit(ResultState.Success(data))
                } else {
                    response.errorBody()?.let {
                        val er = Gson().fromJson(it.charStream(), DeviceByIdResponse::class.java)
                        emit(ResultState.Failed(er.message.toString(), response.code()))
                    }
                }
            } catch (e: Exception) {
                emit(ResultState.Failed("Unknown error. Please try again.", null))
            }
        }.flowOn(Dispatchers.IO)
    }

}