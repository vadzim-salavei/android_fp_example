package com.github.vadzim_salavei.android_fp_example.ui

import android.app.Activity
import arrow.effects.IODispatchers
import com.github.vadzim_salavei.android_fp_example.domain.DomainDependencies
import com.github.vadzim_salavei.android_fp_example.domain.DomainDependenciesImpl
import com.github.vadzim_salavei.android_fp_example.ui.navigation.Navigator
import com.github.vadzim_salavei.android_fp_example.ui.navigation.NavigatorImpl
import kotlin.coroutines.CoroutineContext

class UiDependenciesImpl private constructor(
    override val navigator: Navigator,
    override val mainCoroutineContext: CoroutineContext,
    domainDependencies: DomainDependencies
) : UiDependencies, DomainDependencies by domainDependencies {

    constructor(activity: Activity) : this(
        NavigatorImpl(activity),
        IODispatchers.mainThread,
        DomainDependenciesImpl(activity)
    )
}