package com.github.vadzim_salavei.android_fp_example.ui.list

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.vadzim_salavei.android_fp_example.R
import kotlinx.android.synthetic.main.activity_todos_list.*

class TodosListActivity : AppCompatActivity(), TodosListDelegate {

    private lateinit var todosListDependencies: TodosListContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos_list)

        todosListDependencies = TodosListContext(this)

        supportActionBar?.setTitle(R.string.activity_todos_list_title)

        todosListRecyclerView.layoutManager = LinearLayoutManager(this)
        todosListRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        todosListRecyclerView.adapter = TodosListRecyclerViewAdapter(
            context = this,
            onTodoListItemClickListener = { todoListItemId ->
                editTodosListItemUseCase(todoListItemId).run(todosListDependencies)
            },
            onTodoListItemCheckedChangeListener = { todoListItemId, isTodoListItemChecked ->
                updateTodosListItemUseCase(todoListItemId, isTodoListItemChecked).run(todosListDependencies)
            }
        )
        swipeRefreshTodosListLayout.setOnRefreshListener {
            getTodosListItemsUseCase().run(todosListDependencies)
        }
        addTodosListItemFloatingActionButton.setOnClickListener {
            createTodosListItemUseCase().run(todosListDependencies)
        }
    }

    override fun onResume() {
        super.onResume()
        getTodosListItemsUseCase().run(todosListDependencies)
    }

    override fun showProgress() {
        swipeRefreshTodosListLayout.isRefreshing = true
    }

    override fun hideProgress() {
        swipeRefreshTodosListLayout.isRefreshing = false
    }

    override fun showTodosListItems(todosListItems: List<TodosListItem>) {
        todosListRecyclerView.todosListRecyclerViewAdapter.todosListItems = todosListItems
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

    private val RecyclerView.todosListRecyclerViewAdapter: TodosListRecyclerViewAdapter
        get() {
            return adapter as? TodosListRecyclerViewAdapter ?: throw IllegalStateException()
        }
}