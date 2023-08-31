package com.dartmedia.iotinfusionmonitoringapp.di

import com.dartmedia.iotinfusionmonitoringapp.data.ImplAuthRepository
import com.dartmedia.iotinfusionmonitoringapp.data.ImplDeviceRepository
import com.dartmedia.iotinfusionmonitoringapp.data.ImplPatientRepository
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.repository.AuthRepository
import com.dartmedia.iotinfusionmonitoringapp.domain.main.repository.DeviceRepository
import com.dartmedia.iotinfusionmonitoringapp.domain.main.repository.PatientRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(implAuthRepository: ImplAuthRepository): AuthRepository

    @Binds
    abstract fun provideDeviceRepository(implDeviceRepository: ImplDeviceRepository): DeviceRepository

    @Binds
    abstract fun providePatientRepository(implPatientRepository: ImplPatientRepository): PatientRepository

}