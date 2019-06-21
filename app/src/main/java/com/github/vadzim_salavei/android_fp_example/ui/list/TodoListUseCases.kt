package com.github.vadzim_salavei.android_fp_example.ui.list

import arrow.core.Either
import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import com.github.vadzim_salavei.android_fp_example.domain.getTodosUseCase
import com.github.vadzim_salavei.android_fp_example.domain.model.Todo
import com.github.vadzim_salavei.android_fp_example.domain.updateTodoChecked
import com.github.vadzim_salavei.android_fp_example.ui.list.model.TodoListItem

fun getTodoListItemsUseCase(): Reader<TodoListDependencies, Unit> =
    ReaderApi.ask<TodoListDependencies>().flatMap { todoListDependencies ->
        val mainCoroutineContext = todoListDependencies.mainCoroutineContext
        val todoListView = todoListDependencies.todoListView

        todoListView.showProgress()

        getTodosUseCase<TodoListDependencies>().map { todosIo ->
            todosIo
                .continueOn(mainCoroutineContext)
                .unsafeRunAsync { todosEither ->
                    todoListView.hideProgress()
                    todoListView.showTodosEither(todosEither)
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

fun updateTodoListItemUseCase(todoListItemId: Long, checked: Boolean): Reader<TodoListDependencies, Unit> =
    ReaderApi.ask<TodoListDependencies>().flatMap { todoListDependencies ->
        val mainCoroutineContext = todoListDependencies.mainCoroutineContext
        val todoListView = todoListDependencies.todoListView

        todoListView.showProgress()

        updateTodoChecked<TodoListDependencies>(todoListItemId, checked)
            .flatMap {
                getTodosUseCase<TodoListDependencies>()
            }
            .map { todosIo ->
                todosIo
                    .continueOn(mainCoroutineContext)
                    .unsafeRunAsync { todosEither ->
                        todoListView.hideProgress()
                        todoListView.showTodosEither(todosEither)
                    }
            }
    }

private fun TodoListView.showTodosEither(todosEither: Either<Throwable, List<Todo>>): Unit =
    todosEither
        .map(::mapTodosToTodoListItems)
        .mapLeft(::mapThrowableToString)
        .fold(::showErrorMessage, ::showTodoListItems)

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