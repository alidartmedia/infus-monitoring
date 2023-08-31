package com.dartmedia.iotinfusionmonitoringapp.di

import android.content.Context
import com.dartmedia.iotinfusionmonitoringapp.BuildConfig
import com.dartmedia.iotinfusionmonitoringapp.data.remote.AuthAuthenticator
import com.dartmedia.iotinfusionmonitoringapp.data.remote.AuthInterceptor
import com.dartmedia.iotinfusionmonitoringapp.utils.SessionManager
import com.dartmedia.iotinfusionmonitoringapp.data.remote.api.AuthApiService
import com.dartmedia.iotinfusionmonitoringapp.data.remote.api.MainApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Singleton
    @Provides
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(sessionManager: SessionManager): AuthInterceptor {
        return AuthInterceptor(sessionManager)
    }

    @Singleton
    @Provides
    fun provideAuthAuthenticator(sessionManager: SessionManager): AuthAuthenticator {
        return AuthAuthenticator(sessionManager)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor, authAuthenticator: AuthAuthenticator): OkHttpClient {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()

    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideNetworkAuthService(retrofit: Retrofit.Builder): AuthApiService {
        return retrofit
            .build()
            .create(AuthApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkMainService(retrofit: Retrofit.Builder, okHttpClient: OkHttpClient): MainApiService {
        return retrofit
            .client(okHttpClient)
            .build()
            .create(MainApiService::class.java)
    }

}