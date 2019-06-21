package com.github.vadzim_salavei.android_fp_example.ui.list.widget

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.vadzim_salavei.android_fp_example.ui.list.model.TodoListItem

class TodoListRecyclerViewAdapter(
    private val context: Context,
    private val onTodoListItemClickListener: ((Long) -> Unit)? = null,
    private val onTodoListItemCheckedChangeListener: ((Long, Boolean) -> Unit)? = null
) : RecyclerView.Adapter<TodoListRecyclerViewViewHolder>() {

    init {
        setHasStableIds(true)
    }

    var todoListItems: List<TodoListItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoListRecyclerViewViewHolder {
        return TodoListRecyclerViewViewHolder(
            context,
            viewGroup,
            onTodoListItemClickListener,
            onTodoListItemCheckedChangeListener
        )
    }

    override fun onBindViewHolder(todoListRecyclerViewViewHolder: TodoListRecyclerViewViewHolder, position: Int) {
        todoListRecyclerViewViewHolder.bindTodosListItem(todoListItems[position])
    }

    override fun getItemCount(): Int {
        return todoListItems.size
    }

    override fun getItemId(position: Int): Long {
        return todoListItems[position].id
    }
}