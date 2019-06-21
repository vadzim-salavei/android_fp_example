package com.github.vadzim_salavei.android_fp_example.api.android

import android.content.Context
import android.content.SharedPreferences
import java.io.IOException

class PreferenceApiImpl private constructor(
    private val sharedPreferences: SharedPreferences
) : PreferenceApi {

    constructor(context: Context) : this(
        context.getSharedPreferences(NAME_PREFERENCE_API, Context.MODE_PRIVATE)
    )

    override fun getString(key: String, defaultValue: String): String {
        simulateIoDelaysAndErrors()
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun setString(key: String, value: String) {
        simulateIoDelaysAndErrors()
        sharedPreferences.edit().putString(key, value).apply()
    }

    private fun simulateIoDelaysAndErrors() {
        val currentTimeMillis = System.currentTimeMillis()
        val delayMillis = currentTimeMillis % 3000 + 1000 // 1..3 seconds

        Thread.sleep(delayMillis)

        when (currentTimeMillis % 5) {
            0L -> throw IOException()
            // 1L..4L means succeed call
            else -> Unit
        }
    }

    private companion object {
        private const val NAME_PREFERENCE_API = "NAME_PREFERENCE_API"
    }
}