package com.github.vadzim_salavei.android_fp_example.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.vadzim_salavei.android_fp_example.R

class TodoDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_details)

        supportActionBar?.setTitle(R.string.activity_todo_details_title_creation)
    }

    companion object {
        fun getCallingIntent(context: Context, todoDetailsId: Long? = null): Intent {
            return Intent(context, TodoDetailsActivity::class.java)
        }
    }
}