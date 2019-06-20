package com.github.vadzim_salavei.android_fp_example.domain

interface TodosDelegate {
    fun getTodos(): List<Todo>
    fun setTodos(todos: List<Todo>)
    fun createTodo(todo: Todo)
    fun updateTodo(todo: Todo)
}