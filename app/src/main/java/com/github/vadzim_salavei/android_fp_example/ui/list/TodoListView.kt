package com.github.vadzim_salavei.android_fp_example.ui.list

import com.github.vadzim_salavei.android_fp_example.ui.list.model.TodoListItem

interface TodoListView {
    fun showProgress()
    fun hideProgress()
    fun showTodoListItems(todoListItems: List<TodoListItem>)
    fun showErrorMessage(errorMessage: String)
}