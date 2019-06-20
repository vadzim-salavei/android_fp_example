package com.github.vadzim_salavei.android_fp_example.ui.list

import com.github.vadzim_salavei.android_fp_example.domain.TodosDelegate
import com.github.vadzim_salavei.android_fp_example.domain.TodosDelegateImpl
import com.github.vadzim_salavei.android_fp_example.domain.TodosContext
import com.github.vadzim_salavei.android_fp_example.ui.navigation.NavigationDelegate
import com.github.vadzim_salavei.android_fp_example.ui.navigation.NavigationDelegateImpl
import com.github.vadzim_salavei.android_fp_example.ui.navigation.NavigationContext

data class TodosListContext(
    override val navigationDelegate: NavigationDelegate,
    override val todosDelegate: TodosDelegate,
    val todosListDelegate: TodosListDelegate
) : NavigationContext, TodosContext {

    constructor(todosListActivity: TodosListActivity) : this(
        NavigationDelegateImpl(todosListActivity),
        TodosDelegateImpl(todosListActivity),
        todosListActivity
    )
}