package com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.Device
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceById
import com.dartmedia.iotinfusionmonitoringapp.domain.main.model.DeviceCount
import com.dartmedia.iotinfusionmonitoringapp.domain.main.repository.DeviceRepository
import com.dartmedia.iotinfusionmonitoringapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImplDeviceUseCase @Inject constructor(private val deviceRepository: DeviceRepository): DeviceUseCase {

    override fun getAllDevicePagination(): Flow<PagingData<Device>> {
        return deviceRepository.getAllDevicePagination().map { pagingData ->
            pagingData.map { itemResponse ->
                Device(
                    itemResponse.roomName,
                    itemResponse.deviceId,
                    itemResponse.noBad,
                    itemResponse.status
                )
            }
        }
    }

    override fun getAllOfflineDevicePagination(): Flow<PagingData<Device>> {
        return deviceRepository.getAllOfflineDevicePagination().map { pagingData ->
            pagingData.map { itemResponse ->
                Device(
                    itemResponse.roomName,
                    itemResponse.deviceId,
                    itemResponse.noBad,
                    itemResponse.status
                )
            }
        }
    }

    override fun getAllOnlineDevicePagination(): Flow<PagingData<Device>> {
        return deviceRepository.getAllOnlineDevicePagination().map { pagingData ->
            pagingData.map { itemResponse ->
                Device(
                    itemResponse.roomName,
                    itemResponse.deviceId,
                    itemResponse.noBad,
                    itemResponse.status
                )
            }
        }
    }

    override suspend fun getCountAllDevice(): Flow<ResultState<DeviceCount>> {
        return deviceRepository.getCountAllDevice()
    }

    override suspend fun getCountAllOfflineDevice(): Flow<ResultState<DeviceCount>> {
        return deviceRepository.getCountAllOfflineDevice()
    }

    override suspend fun getCountAllOnlineDevice(): Flow<ResultState<DeviceCount>> {
        return deviceRepository.getCountAllOnlineDevice()
    }

    override suspend fun getDeviceById(id: String): Flow<ResultState<DeviceById>> {
        return deviceRepository.getDeviceById(id)
    }

}