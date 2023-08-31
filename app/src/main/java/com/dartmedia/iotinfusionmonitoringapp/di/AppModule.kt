package com.dartmedia.iotinfusionmonitoringapp.di

import com.dartmedia.iotinfusionmonitoringapp.domain.auth.usecase.AuthUseCase
import com.dartmedia.iotinfusionmonitoringapp.domain.auth.usecase.ImplAuthUseCase
import com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase.DeviceUseCase
import com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase.ImplDeviceUseCase
import com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase.ImplPatientUseCase
import com.dartmedia.iotinfusionmonitoringapp.domain.main.usecase.PatientUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideAuthUseCase(implAuthUseCase: ImplAuthUseCase): AuthUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideDeviceUseCase(implDeviceUseCase: ImplDeviceUseCase): DeviceUseCase

    @Binds
    @ViewModelScoped
    abstract fun providePatientUseCase(implPatientUseCase: ImplPatientUseCase): PatientUseCase

}