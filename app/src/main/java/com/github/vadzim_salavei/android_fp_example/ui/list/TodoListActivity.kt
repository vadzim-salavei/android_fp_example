package com.github.vadzim_salavei.android_fp_example.ui.list

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vadzim_salavei.android_fp_example.R
import com.github.vadzim_salavei.android_fp_example.ui.list.model.TodoListItem
import com.github.vadzim_salavei.android_fp_example.ui.list.widget.TodoListRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_todo_list.*

class TodoListActivity : AppCompatActivity(), TodoListView {

    private lateinit var todoListDependencies: TodoListDependencies

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        todoListDependencies = TodoListDependencies(this)

        supportActionBar?.setTitle(R.string.activity_todo_list_title)

        todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        todoListRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        todoListRecyclerView.adapter =
            TodoListRecyclerViewAdapter(
                context = this,
                onTodoListItemClickListener = { todoListItemId ->
                    editTodoListItemUseCase(todoListItemId).run(todoListDependencies)
                },
                onTodoListItemCheckedChangeListener = { todoListItemId, isTodoListItemChecked ->
                    updateTodoListItemUseCase(todoListItemId, isTodoListItemChecked).run(todoListDependencies)
                }
            )
        swipeRefreshTodoListLayout.setOnRefreshListener {
            getTodoListItemsUseCase().run(todoListDependencies)
        }
        createTodoListItemFloatingActionButton.setOnClickListener {
            createTodoListItemUseCase().run(todoListDependencies)
        }
    }

    override fun onResume() {
        super.onResume()
        getTodoListItemsUseCase().run(todoListDependencies)
    }

    override fun showProgress() {
        if (swipeRefreshTodoListLayout.isRefreshing) {
            // does nothing
        } else {
            progressDialog?.dismiss()
            progressDialog = ProgressDialog.show(this, "", getString(R.string.all_progress_dialog_message_loading))
        }
    }

    override fun hideProgress() {
        if (swipeRefreshTodoListLayout.isRefreshing) {
            swipeRefreshTodoListLayout.isRefreshing = false
        } else {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    override fun showTodoListItems(todoListItems: List<TodoListItem>) {
        todoListRecyclerView.todoListRecyclerViewAdapter.todoListItems = todoListItems
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

    private val RecyclerView.todoListRecyclerViewAdapter: TodoListRecyclerViewAdapter
        get() {
            return adapter as? TodoListRecyclerViewAdapter
                ?: throw IllegalStateException()
        }
}