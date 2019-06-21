package com.github.vadzim_salavei.android_fp_example.ui.list.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.vadzim_salavei.android_fp_example.R
import com.github.vadzim_salavei.android_fp_example.ui.list.model.TodoListItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_todo_list_item.view.*

class TodoListRecyclerViewViewHolder private constructor(
    override val containerView: View?,
    private val onTodosListItemClickListener: ((Long) -> Unit)?,
    private val onTodosListItemCheckedChangeListener: ((Long, Boolean) -> Unit)?,
    itemView: View
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    constructor(
        context: Context,
        viewGroup: ViewGroup,
        onTodoListItemClickListener: ((Long) -> Unit)? = null,
        onTodoListItemCheckedChangeListener: ((Long, Boolean) -> Unit)? = null
    ) : this(
        viewGroup,
        onTodoListItemClickListener,
        onTodoListItemCheckedChangeListener,
        LayoutInflater.from(context).inflate(R.layout.view_todo_list_item, viewGroup, false)
    )

    fun bindTodosListItem(todoListItem: TodoListItem) {
        itemView.todoAppCompatTextView.text = todoListItem.title
        itemView.todoAppCompatCheckBox.isChecked = todoListItem.checked
        itemView.setOnClickListener {
            onTodosListItemClickListener?.invoke(todoListItem.id)
        }
        itemView.todoAppCompatCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onTodosListItemCheckedChangeListener?.invoke(todoListItem.id, isChecked)
        }
    }
}