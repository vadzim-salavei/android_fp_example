package com.github.vadzim_salavei.android_fp_example.domain

import com.github.vadzim_salavei.android_fp_example.api.android.PreferenceApi
import com.google.gson.Gson
import kotlin.coroutines.CoroutineContext

interface DomainDependencies {
    val gson: Gson
    val preferenceApi: PreferenceApi
    val ioCoroutineContext: CoroutineContext
}