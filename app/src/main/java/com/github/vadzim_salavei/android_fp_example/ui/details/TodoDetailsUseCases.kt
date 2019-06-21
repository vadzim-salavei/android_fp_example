package com.github.vadzim_salavei.android_fp_example.ui.details

import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import com.github.vadzim_salavei.android_fp_example.domain.createTodoUseCase
import com.github.vadzim_salavei.android_fp_example.domain.deleteTodoByIdUseCase
import com.github.vadzim_salavei.android_fp_example.domain.getTodosUseCase
import com.github.vadzim_salavei.android_fp_example.domain.model.Todo
import com.github.vadzim_salavei.android_fp_example.domain.updateTodoTitleAndContentUseCase
import com.github.vadzim_salavei.android_fp_example.ui.details.model.TodoDetails

fun getTodoDetailsViewState(todoDetailsId: Long?): Reader<TodoDetailsDependencies, Unit> =
    ReaderApi.ask<TodoDetailsDependencies>().flatMap { todoDetailsDependencies ->
        val mainCoroutineContext = todoDetailsDependencies.mainCoroutineContext
        val todoDetailsView = todoDetailsDependencies.todoDetailsView

        getTodosUseCase<TodoDetailsDependencies>().map { todosIo ->
            todoDetailsView.showProgress()

            todosIo
                .continueOn(mainCoroutineContext)
                .map { todos ->
                    mapTodosToTodoDetailsViewState(todos, todoDetailsId)
                }
                .unsafeRunAsync { stateEither ->
                    todoDetailsView.hideProgress()

                    stateEither
                        .mapLeft(::mapThrowableToString)
                        .fold(todoDetailsView::showErrorMessage, todoDetailsView::setState)
                }
        }
    }

fun deleteTodoDetailsUseCase(todoDetailsId: Long?): Reader<TodoDetailsDependencies, Unit> =
    ReaderApi.ask<TodoDetailsDependencies>().flatMap { todoDetailsDependencies ->
        val navigator = todoDetailsDependencies.navigator
        val mainCoroutineContext = todoDetailsDependencies.mainCoroutineContext
        val todoDetailsView = todoDetailsDependencies.todoDetailsView

        deleteTodoByIdUseCase<TodoDetailsDependencies>(todoDetailsId).map { unitIo ->
            todoDetailsView.showProgress()

            unitIo
                .continueOn(mainCoroutineContext)
                .unsafeRunAsync { unitEither ->
                    todoDetailsView.hideProgress()

                    unitEither
                        .mapLeft(::mapThrowableToString)
                        .fold(todoDetailsView::showErrorMessage) {
                            navigator.close()
                        }
                }
        }
    }

fun createOrUpdateTodoDetailsUseCase(
    todoDetailsId: Long?,
    title: String,
    content: String
): Reader<TodoDetailsDependencies, Unit> =
    ReaderApi.ask<TodoDetailsDependencies>().flatMap { todoDetailsDependencies ->
        val navigator = todoDetailsDependencies.navigator
        val mainCoroutineContext = todoDetailsDependencies.mainCoroutineContext
        val todoDetailsView = todoDetailsDependencies.todoDetailsView
        val useCase = if (todoDetailsId == null) {
            createTodoUseCase<TodoDetailsDependencies>(title, content)
        } else {
            updateTodoTitleAndContentUseCase(todoDetailsId, title, content)
        }

        useCase.map { unitIo ->
            todoDetailsView.showProgress()

            unitIo
                .continueOn(mainCoroutineContext)
                .unsafeRunAsync { unitEither ->
                    todoDetailsView.hideProgress()

                    unitEither
                        .mapLeft(::mapThrowableToString)
                        .fold(todoDetailsView::showErrorMessage) {
                            navigator.close()
                        }
                }
        }
    }

fun mapTodosToTodoDetailsViewState(todos: List<Todo>, todoId: Long?): TodoDetailsView.State =
    todos
        .firstOrNull { todo ->
            todo.id == todoId
        }
        ?.let { todo ->
            TodoDetailsView.State.Edit(
                TodoDetails(
                    id = todo.id,
                    title = todo.title,
                    content = todo.content
                )
            )
        }
        ?: TodoDetailsView.State.Creation

private fun mapThrowableToString(throwable: Throwable): String =
    throwable.message ?: throwable::class.java.simpleName