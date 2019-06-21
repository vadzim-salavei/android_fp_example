package com.github.vadzim_salavei.android_fp_example.domain

import android.content.Context
import arrow.effects.IODispatchers
import com.github.vadzim_salavei.android_fp_example.AutoGsonAdapterFactory
import com.github.vadzim_salavei.android_fp_example.api.android.PreferenceApi
import com.github.vadzim_salavei.android_fp_example.api.android.PreferenceApiImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import gsonpath.GsonPath
import kotlin.coroutines.CoroutineContext

class DomainDependenciesImpl private constructor(
    override val gson: Gson,
    override val preferenceApi: PreferenceApi,
    override val ioCoroutineContext: CoroutineContext
) : DomainDependencies {

    constructor(context: Context) : this(
        GsonBuilder().registerTypeAdapterFactory(GsonPath.createTypeAdapterFactory(AutoGsonAdapterFactory::class.java)).create(),
        PreferenceApiImpl(context),
        IODispatchers.CommonPool
    )
}