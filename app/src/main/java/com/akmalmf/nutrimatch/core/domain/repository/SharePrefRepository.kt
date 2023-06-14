package com.akmalmf.nutrimatch.core.domain.repository

/**
 * Created by Akmal Muhamad Firdaus on 2023/06/01 23:34
 * akmalmf007@gmail.com
 */
interface SharePrefRepository {
    fun isOnLogin(): Boolean

    fun getAccessToken(): String
    fun setAccessToken(value: String)

    fun setIsOnRegister(value: Boolean)
    fun getIsOnRegister(): Boolean

    fun clearPref()
}