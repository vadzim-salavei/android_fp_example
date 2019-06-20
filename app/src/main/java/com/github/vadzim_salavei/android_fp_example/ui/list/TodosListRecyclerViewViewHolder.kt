package com.github.vadzim_salavei.android_fp_example.ui.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.vadzim_salavei.android_fp_example.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_todos_list_item.view.*

class TodosListRecyclerViewViewHolder private constructor(
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
        LayoutInflater.from(context).inflate(R.layout.view_todos_list_item, viewGroup, false)
    )

    fun bindTodosListItem(todosListItem: TodosListItem) {
        itemView.todoAppCompatTextView.text = todosListItem.title
        itemView.todoAppCompatCheckBox.isChecked = todosListItem.checked
        itemView.setOnClickListener {
            onTodosListItemClickListener?.invoke(todosListItem.id)
        }
        itemView.todoAppCompatCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onTodosListItemCheckedChangeListener?.invoke(todosListItem.id, isChecked)
        }
    }
}