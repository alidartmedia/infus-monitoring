package com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase

import androidx.paging.PagingData
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.Device
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceById
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceCount
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface DeviceUseCase {

    fun getAllDevicePagination(): Flow<PagingData<Device>>

    fun getAllOfflineDevicePagination(): Flow<PagingData<Device>>

    fun getAllOnlineDevicePagination(): Flow<PagingData<Device>>

    suspend fun getCountAllDevice(): Flow<ResultState<DeviceCount>>

    suspend fun getCountAllOfflineDevice(): Flow<ResultState<DeviceCount>>

    suspend fun getCountAllOnlineDevice(): Flow<ResultState<DeviceCount>>

    suspend fun getDeviceById(id: String): Flow<ResultState<DeviceById>>

}