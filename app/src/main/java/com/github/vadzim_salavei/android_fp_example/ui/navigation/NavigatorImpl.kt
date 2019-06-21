package com.github.vadzim_salavei.android_fp_example.ui.navigation

import android.app.Activity
import com.github.vadzim_salavei.android_fp_example.ui.details.TodoDetailsActivity

class NavigatorImpl(
    private val activity: Activity
) : Navigator {

    override fun close() {
        activity.finish()
    }

    override fun createTodoDetails() {
        activity.startActivity(TodoDetailsActivity.getCallingIntent(activity))
    }

    override fun editTodoDetails(todoDetailsId: Long) {
        activity.startActivity(TodoDetailsActivity.getCallingIntent(activity, todoDetailsId))
    }
}