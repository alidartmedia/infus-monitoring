package com.dartmedia.iotinfusionmonitoringapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.dartmedia.iotinfusionmonitoringapp.utils.Utility

class SessionManager(private val context: Context) {

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(Utility.APP_NAME, Context.MODE_PRIVATE)

    fun saveRefreshToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Utility.REFRESH_TOKEN, token)
        editor.apply()
    }

    fun fetchRefreshToken(): String? {
        return sharedPreferences.getString(Utility.REFRESH_TOKEN, null)
    }

    fun deleteRefreshToken() {
        val editor = sharedPreferences.edit()
        editor.remove(Utility.REFRESH_TOKEN)
        editor.apply()
    }

    fun saveAccessToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Utility.ACCESS_TOKEN, token)
        editor.apply()
    }

    fun fetchAccessToken(): String? {
        return sharedPreferences.getString(Utility.ACCESS_TOKEN, null)
    }

    fun deleteAccessToken() {
        val editor = sharedPreferences.edit()
        editor.remove(Utility.ACCESS_TOKEN)
        editor.apply()
    }

}