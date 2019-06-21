package com.github.vadzim_salavei.android_fp_example.ui.list

import com.github.vadzim_salavei.android_fp_example.ui.UiDependencies
import com.github.vadzim_salavei.android_fp_example.ui.UiDependenciesImpl

class TodoListDependencies(
    val todoListView: TodoListView,
    uiDependencies: UiDependencies
) : UiDependencies by uiDependencies {

    constructor(todoListActivity: TodoListActivity) : this(
        todoListActivity,
        UiDependenciesImpl(todoListActivity)
    )
}