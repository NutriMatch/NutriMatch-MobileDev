package com.akmalmf.nutrimatch.core.data.source.local

import android.content.Context
import com.akmalmf.nutrimatch.abstraction.data.Constant
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/01 23:39
 * akmalmf007@gmail.com
 */
@Singleton
class SharedPrefImpl @Inject constructor(
    @ApplicationContext context: Context
){
    private val sharePref = context.getSharedPreferences(Constant.MY_APP_KEY, Context.MODE_PRIVATE)

    fun isOnLogin(): Boolean {
        return getAccessToken().isNotEmpty()
    }

    fun getAccessToken(): String = sharePref.getString(Constant.ACCESS_TOKEN, "") ?: ""

    fun setAccessToken(value: String) = sharePref.edit().putString(Constant.ACCESS_TOKEN, value).apply()

    fun setIsOnRegister(value: Boolean) = sharePref.edit().putBoolean(Constant.IS_REGISTER, value).apply()

    fun getIsOnRegister(): Boolean = sharePref.getBoolean(Constant.IS_REGISTER, false)

    fun clearPref() {
        sharePref.edit().clear().apply()
    }
}