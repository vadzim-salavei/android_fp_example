package com.github.vadzim_salavei.android_fp_example.api.android

interface PreferenceApi {
    fun getString(key: String, defaultValue: String): String
    fun setString(key: String, value: String)
}