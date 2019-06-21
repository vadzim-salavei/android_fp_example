package com.github.vadzim_salavei.android_fp_example.ui.details

import com.github.vadzim_salavei.android_fp_example.ui.UiDependencies
import com.github.vadzim_salavei.android_fp_example.ui.UiDependenciesImpl

class TodoDetailsDependencies private constructor(
    val todoDetailsView: TodoDetailsView,
    uiDependencies: UiDependencies
) : UiDependencies by uiDependencies {

    constructor(todoDetailsActivity: TodoDetailsActivity) : this(
        todoDetailsActivity,
        UiDependenciesImpl(todoDetailsActivity)
    )
}