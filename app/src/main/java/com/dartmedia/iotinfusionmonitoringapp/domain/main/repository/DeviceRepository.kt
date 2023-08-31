package com.dartmedia.iotinfusionmonitoringapp.domain.main.repository

import androidx.paging.PagingData
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.ProductsItemAllDeviceResponse
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceById
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceCount
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {

    fun getAllDevicePagination(): Flow<PagingData<ProductsItemAllDeviceResponse>>

    fun getAllOfflineDevicePagination(): Flow<PagingData<ProductsItemAllDeviceResponse>>

    fun getAllOnlineDevicePagination(): Flow<PagingData<ProductsItemAllDeviceResponse>>

    suspend fun getCountAllDevice(): Flow<ResultState<DeviceCount>>

    suspend fun getCountAllOfflineDevice(): Flow<ResultState<DeviceCount>>

    suspend fun getCountAllOnlineDevice(): Flow<ResultState<DeviceCount>>

    suspend fun getDeviceById(id: String): Flow<ResultState<DeviceById>>

}