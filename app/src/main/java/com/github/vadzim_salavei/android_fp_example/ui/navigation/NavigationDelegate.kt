package com.github.vadzim_salavei.android_fp_example.ui.navigation

interface NavigationDelegate {
    fun close()
    fun createTodoDetails()
    fun editTodoDetails(todoDetailsId: Long)
}