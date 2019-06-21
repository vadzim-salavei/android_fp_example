package com.github.vadzim_salavei.android_fp_example.domain

import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import arrow.effects.IO
import com.github.vadzim_salavei.android_fp_example.domain.model.Todo
import com.google.gson.reflect.TypeToken

private const val KEY_TODOS = "KEY_TODOS"
private const val DEFAULT_VALUE_TODOS = "[]"

fun <D : DomainDependencies> getTodoUseCase(todoId: Long): Reader<D, IO<Todo>> =
    getTodosUseCase<D>().map { todosIo ->
        todosIo.map { todos ->
            todos.first { todo ->
                todo.id == todoId
            }
        }
    }

fun <D : DomainDependencies> getTodosUseCase(): Reader<D, IO<List<Todo>>> =
    ReaderApi.ask<D>().map { domainDependencies ->
        val gson = domainDependencies.gson
        val preferenceApi = domainDependencies.preferenceApi
        val ioCoroutineContext = domainDependencies.ioCoroutineContext

        IO(ioCoroutineContext) {
            preferenceApi
                .getString(KEY_TODOS, DEFAULT_VALUE_TODOS)
                .let { todosJson ->
                    gson.fromJson<List<Todo>>(todosJson, object : TypeToken<List<Todo>>() {}.type)
                }
        }
    }

fun <D : DomainDependencies> setTodosUseCase(todos: List<Todo>): Reader<D, IO<Unit>> =
    ReaderApi.ask<D>().map { domainDependencies ->
        val gson = domainDependencies.gson
        val preferenceApi = domainDependencies.preferenceApi
        val ioCoroutineContext = domainDependencies.ioCoroutineContext

        IO(ioCoroutineContext) {
            gson.toJson(todos).let { todosJson ->
                preferenceApi.setString(KEY_TODOS, todosJson)
            }
        }
    }

fun <D : DomainDependencies> updateTodoUseCase(todo: Todo): Reader<D, IO<Unit>> =
    getTodosUseCase<D>().zip flatMap { todosIo ->
        todosIo.map { todos ->
            setTodosUseCase<D>()
        }
    }