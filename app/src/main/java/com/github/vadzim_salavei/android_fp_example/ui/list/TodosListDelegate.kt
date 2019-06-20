package com.github.vadzim_salavei.android_fp_example.ui.list

interface TodosListDelegate {
    fun showProgress()
    fun hideProgress()
    fun showTodosListItems(todosListItems: List<TodosListItem>)
    fun showErrorMessage(errorMessage: String)
}