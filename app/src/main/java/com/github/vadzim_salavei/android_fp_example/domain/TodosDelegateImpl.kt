package com.github.vadzim_salavei.android_fp_example.domain

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import gsonpath.GsonPath
import java.io.IOException

class TodosDelegateImpl private constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : TodosDelegate {

    constructor(context: Context) : this(
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE),
        GsonBuilder()
            .registerTypeAdapterFactory(GsonPath.createTypeAdapterFactory(TodosAutoGsonAdapterFactory::class.java))
            .create()
    )

    override fun getTodos(): List<Todo> {
        simulateUnstableNetwork()

        return sharedPreferences
            .getString(KEY_TODOS, DEFAULT_VALUE_TODOS)
            ?.let { jsonString ->
                gson.fromJson<List<Todo>>(jsonString, object : TypeToken<List<Todo>>() {}.type)
            }
            ?: listOf()
    }

    override fun setTodos(todos: List<Todo>) {
        simulateUnstableNetwork()

        sharedPreferences
            .edit()
            .putString(KEY_TODOS, gson.toJson(todos))
            .apply()
    }

    override fun createTodo(todo: Todo) {
        getTodos()
            .toMutableList()
            .plus(todo)
            .apply {
                setTodos(this)
            }
    }

    override fun updateTodo(todo: Todo) {
        getTodos()
            .toMutableList()
            .apply {
                replaceAll { _todo ->
                    if (_todo.id == todo.id) {
                        todo
                    } else {
                        _todo
                    }
                }
                setTodos(this)
            }
    }

    private fun simulateUnstableNetwork() {
        val currentTimeMillis = System.currentTimeMillis()
        val delayMillis = currentTimeMillis % 3000 + 1000 // 1..3 seconds

        Thread.sleep(delayMillis)

        when (currentTimeMillis % 10) {
            0L -> throw IOException()
            1L -> throw JsonParseException("")
            // 2..9 means succeed call
            else -> Unit
        }
    }

    private companion object {
        private const val NAME = "TODOS_DELEGATE_IMPL"
        private const val KEY_TODOS = "KEY_TODOS"
        private const val DEFAULT_VALUE_TODOS = "[]"
    }
}