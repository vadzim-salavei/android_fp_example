package com.github.vadzim_salavei.android_fp_example.ui.details

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.vadzim_salavei.android_fp_example.R
import kotlinx.android.synthetic.main.activity_todo_details.*

class TodoDetailsActivity : AppCompatActivity(), TodoDetailsView {

    private lateinit var todoDetailsDependencies: TodoDetailsDependencies

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_details)

        todoDetailsDependencies = TodoDetailsDependencies(this)

        createOrUpdateTodoDetailsAppCompatButton.setOnClickListener {
            val id = intent.todoDetailsId
            val title = todoDetailsTitleAppCompatEditText.text.toString()
            val content = todoDetailsContentAppCompatEditText.text.toString()

            createOrUpdateTodoDetailsUseCase(id, title, content).run(todoDetailsDependencies)
        }
        deleteTodoDetailsAppCompatButton.setOnClickListener {
            deleteTodoDetailsUseCase(intent.todoDetailsId).run(todoDetailsDependencies)
        }
        getTodoDetailsViewState(intent.todoDetailsId).run(todoDetailsDependencies)
    }

    override fun showProgress() {
        progressDialog?.dismiss()
        progressDialog = ProgressDialog.show(this, "", getString(R.string.all_progress_dialog_message_loading))
    }

    override fun hideProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    override fun showErrorMessage(errorMessage: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.all_title_error)
            .setMessage(errorMessage)
            .setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    override fun setState(state: TodoDetailsView.State) {
        when (state) {
            is TodoDetailsView.State.Creation -> {
                supportActionBar?.setTitle(R.string.activity_todo_details_title_creation)
                createOrUpdateTodoDetailsAppCompatButton.setText(R.string.activity_todo_details_button_create)
                deleteTodoDetailsAppCompatButton.visibility = View.GONE
            }
            is TodoDetailsView.State.Edit -> {
                supportActionBar?.setTitle(R.string.activity_todo_details_title_edit)
                todoDetailsTitleAppCompatEditText.setText(state.todoDetails.title)
                todoDetailsContentAppCompatEditText.setText(state.todoDetails.content)
                createOrUpdateTodoDetailsAppCompatButton.setText(R.string.activity_todo_details_button_edit)
                deleteTodoDetailsAppCompatButton.setText(R.string.activity_todo_details_button_delete)
            }
        }
    }

    companion object {

        private const val EXTRA_TODO_DETAILS_ID = "EXTRA_TODO_DETAILS_ID"

        fun getCallingIntent(context: Context, todoDetailsId: Long? = null): Intent {
            return Intent(context, TodoDetailsActivity::class.java).apply {
                this.todoDetailsId = todoDetailsId
            }
        }

        private var Intent.todoDetailsId: Long?
            get() {
                return if (extras?.containsKey(EXTRA_TODO_DETAILS_ID) == true) {
                    extras?.getLong(EXTRA_TODO_DETAILS_ID)
                } else {
                    null
                }
            }
            set(value) {
                if (value == null) {
                    // does nothing
                } else {
                    putExtra(EXTRA_TODO_DETAILS_ID, value)
                }
            }
    }
}