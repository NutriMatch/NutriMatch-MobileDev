package com.akmalmf.nutrimatch.core.data.source.local

import android.content.Context
import com.akmalmf.nutrimatch.abstraction.data.Constant
import com.akmalmf.nutrimatch.core.domain.repository.SharePrefRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/01 23:39
 * akmalmf007@gmail.com
 */
class SharedPrefImpl @Inject constructor(
    @ApplicationContext context: Context
): SharePrefRepository {
    private val sharePref = context.getSharedPreferences(Constant.MY_APP_KEY, Context.MODE_PRIVATE)

    override fun isOnLogin(): Boolean {
        return getAccessToken().isNotEmpty()
    }

    override fun getAccessToken(): String = sharePref.getString(Constant.ACCESS_TOKEN, "") ?: ""

    override fun setAccessToken(value: String) = sharePref.edit().putString(Constant.ACCESS_TOKEN, value).apply()

    override fun setIsOnRegister(value: Boolean) = sharePref.edit().putBoolean(Constant.IS_REGISTER, value).apply()

    override fun getIsOnRegister(): Boolean = sharePref.getBoolean(Constant.IS_REGISTER, false)

    override fun clearPref() {
        sharePref.edit().clear().apply()
    }
}