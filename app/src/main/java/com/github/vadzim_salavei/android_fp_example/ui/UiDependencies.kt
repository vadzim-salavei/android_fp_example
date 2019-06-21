package com.github.vadzim_salavei.android_fp_example.ui

import com.github.vadzim_salavei.android_fp_example.domain.DomainDependencies
import com.github.vadzim_salavei.android_fp_example.ui.navigation.Navigator
import kotlin.coroutines.CoroutineContext

interface UiDependencies : DomainDependencies {
    val navigator: Navigator
    val mainCoroutineContext: CoroutineContext
}