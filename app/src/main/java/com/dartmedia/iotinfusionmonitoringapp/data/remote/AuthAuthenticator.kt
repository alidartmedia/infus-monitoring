package com.dartmedia.iotinfusionmonitoringapp.data.remote

import com.dartmedia.iotinfusionmonitoringapp.BuildConfig
import com.dartmedia.iotinfusionmonitoringapp.data.remote.api.AuthApiService
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.updatetoken.UpdateAccessTokenRequest
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.auth.updatetoken.UpdateAccessTokenResponse
import com.dartmedia.iotinfusionmonitoringapp.utils.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(private val sessionManager: SessionManager): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = sessionManager.fetchRefreshToken()

        return runBlocking {
            val newToken = getNewToken(token!!)

            if (!newToken.isSuccessful || newToken.body() == null) {
                sessionManager.deleteRefreshToken()
                sessionManager.deleteAccessToken()
            }

            newToken.body()?.let {
                sessionManager.saveAccessToken(it.data?.accessToken!!)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.data.accessToken}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String): retrofit2.Response<UpdateAccessTokenResponse> {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = retrofit.create(AuthApiService::class.java)
        return service.updateAccessToken(UpdateAccessTokenRequest(refreshToken))
    }

}