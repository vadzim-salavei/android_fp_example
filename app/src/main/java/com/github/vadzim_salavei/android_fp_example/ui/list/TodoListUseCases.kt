package com.github.vadzim_salavei.android_fp_example.ui.list

import arrow.core.ForId
import arrow.core.Id
import arrow.core.extensions.id.monad.monad
import arrow.data.*
import arrow.data.extensions.kleisli.monad.flatMap
import com.github.vadzim_salavei.android_fp_example.domain.getTodoUseCase
import com.github.vadzim_salavei.android_fp_example.domain.getTodosUseCase
import com.github.vadzim_salavei.android_fp_example.domain.model.Todo
import com.github.vadzim_salavei.android_fp_example.domain.updateTodoUseCase
import com.github.vadzim_salavei.android_fp_example.ui.list.model.TodoListItem

fun getTodoListItemsUseCase(): Reader<TodoListDependencies, Unit> =
    ReaderApi.ask<TodoListDependencies>().flatMap { todoListDependencies ->
        val mainCoroutineContext = todoListDependencies.mainCoroutineContext
        val todoListView = todoListDependencies.todoListView

        getTodosUseCase<TodoListDependencies>().map { todosIo ->
            todoListView.showProgress()

            todosIo
                .continueOn(mainCoroutineContext)
                .unsafeRunAsync { todosEither ->
                    todoListView.hideProgress()

                    todosEither
                        .map(::mapTodosToTodoListItems)
                        .mapLeft(::mapThrowableToString)
                        .fold(
                            todoListView::showErrorMessage,
                            todoListView::showTodoListItems
                        )
                }
        }
    }

fun createTodoListItemUseCase(): Reader<TodoListDependencies, Unit> =
    ReaderApi.ask<TodoListDependencies>().map { todoListDependencies ->
        todoListDependencies.navigator.createTodoDetails()
    }

fun editTodoListItemUseCase(todoListItemId: Long): Reader<TodoListDependencies, Unit> =
    ReaderApi.ask<TodoListDependencies>().map { todoListDependencies ->
        todoListDependencies.navigator.editTodoDetails(todoListItemId)
    }

fun updateTodoListItemUseCase(todoListItemId: Long, isChecked: Boolean): Reader<TodoListDependencies, Unit> =
    ReaderApi.ask<TodoListDependencies>().flatMap { todoListDependencies ->
        val mainCoroutineContext = todoListDependencies.mainCoroutineContext
        val todoListView = todoListDependencies.todoListView

        getTodoUseCase<TodoListDependencies>(todoListItemId).flatMap { todoIo ->
            val todo: Todo = TODO()

            updateTodoUseCase<TodoListDependencies>(todo.copy(
                checked = isChecked
            ))
        }.map { Unit }
    }

private fun mapTodosToTodoListItems(todos: List<Todo>): List<TodoListItem> =
    todos.map { todo ->
        TodoListItem(
            id = todo.id,
            title = todo.title,
            checked = todo.checked
        )
    }

private fun mapThrowableToString(throwable: Throwable): String =
    throwable.message ?: throwable::class.java.simpleName