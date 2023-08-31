package com.dartmedia.iotinfusionmonitoringapp.data.remote.api

import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.AllDeviceResponse
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.DeviceByIdResponse
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.ProductsItemAllDeviceResponse
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient.AddPatientRequest
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.patient.AddPatientResponse
import retrofit2.Response
import retrofit2.http.*

interface MainApiService {

    @GET("device")
    suspend fun getAllDevicePagination(@Query("page") page: Int): AllDeviceResponse

    @GET("device/status/false")
    suspend fun getAllOfflineDevicePagination(@Query("page") page: Int): AllDeviceResponse

    @GET("device/status/true")
    suspend fun getAllOnlineDevicePagination(@Query("page") page: Int): AllDeviceResponse

    @GET("device")
    suspend fun getAllDevice(): Response<AllDeviceResponse>

    @GET("device/status/false")
    suspend fun getAllOfflineDevice(): Response<AllDeviceResponse>

    @GET("device/status/true")
    suspend fun getAllOnlineDevice(): Response<AllDeviceResponse>

    @GET("device/{idDevice}")
    suspend fun getDeviceById(@Path("idDevice") idDevice: String): Response<DeviceByIdResponse>

    @POST("passient")
    suspend fun addPatient(@Body addPatientRequest: AddPatientRequest): Response<AddPatientResponse>

}