package com.dartmedia.iotinfusionmonitoringapp.utils

sealed class ResultState<out T: Any> {
    data class Success<T: Any>(val data: T) : ResultState<T>()
    data class Failed(val error: String, val code: Int?) : ResultState<Nothing>()
    object Empty : ResultState<Nothing>()
}
