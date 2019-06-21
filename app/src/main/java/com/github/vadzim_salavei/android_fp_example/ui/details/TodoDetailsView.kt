package com.github.vadzim_salavei.android_fp_example.ui.details

import com.github.vadzim_salavei.android_fp_example.ui.details.model.TodoDetails

interface TodoDetailsView {
    fun showProgress()
    fun hideProgress()
    fun showErrorMessage(errorMessage: String)
    fun setState(state: State)

    sealed class State {

        object Creation : State()

        data class Edit(
            val todoDetails: TodoDetails
        ) : State()
    }
}