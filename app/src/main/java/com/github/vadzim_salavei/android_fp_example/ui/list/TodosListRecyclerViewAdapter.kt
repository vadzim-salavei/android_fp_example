package com.github.vadzim_salavei.android_fp_example.ui.list

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TodosListRecyclerViewAdapter(
    private val context: Context,
    private val onTodoListItemClickListener: ((Long) -> Unit)? = null,
    private val onTodoListItemCheckedChangeListener: ((Long, Boolean) -> Unit)? = null
) : RecyclerView.Adapter<TodosListRecyclerViewViewHolder>() {

    init {
        setHasStableIds(true)
    }

    var todosListItems: List<TodosListItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodosListRecyclerViewViewHolder {
        return TodosListRecyclerViewViewHolder(
            context,
            viewGroup,
            onTodoListItemClickListener,
            onTodoListItemCheckedChangeListener
        )
    }

    override fun onBindViewHolder(todosListRecyclerViewViewHolder: TodosListRecyclerViewViewHolder, position: Int) {
        todosListRecyclerViewViewHolder.bindTodosListItem(todosListItems[position])
    }

    override fun getItemCount(): Int {
        return todosListItems.size
    }

    override fun getItemId(position: Int): Long {
        return todosListItems[position].id
    }
}