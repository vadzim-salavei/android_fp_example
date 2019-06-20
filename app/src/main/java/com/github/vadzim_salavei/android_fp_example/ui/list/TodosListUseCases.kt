package com.github.vadzim_salavei.android_fp_example.ui.list

import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.flatMap
import arrow.data.map
import com.github.vadzim_salavei.android_fp_example.ui.navigation.createTodoDetailsUseCase
import com.github.vadzim_salavei.android_fp_example.ui.navigation.editTodoDetailsUseCase

fun getTodosListItemsUseCase(): Reader<TodosListContext, Unit> =
    ReaderApi.ask<TodosListContext>()
        .map { (_, _, view) ->
            view.showTodosListItems(
                listOf(
                    TodosListItem(
                        id = 1,
                        title = "Title 1",
                        checked = false
                    ),
                    TodosListItem(
                        id = 2,
                        title = "Title 2",
                        checked = true
                    ),
                    TodosListItem(
                        id = 3,
                        title = "Title 3",
                        checked = false
                    )
                )
            )
        }

fun createTodosListItemUseCase(): Reader<TodosListContext, Unit> =
    ReaderApi.ask<TodosListContext>().flatMap {
        createTodoDetailsUseCase<TodosListContext>()
    }

fun editTodosListItemUseCase(todosListItemId: Long): Reader<TodosListContext, Unit> =
    ReaderApi.ask<TodosListContext>()
        .flatMap {
            editTodoDetailsUseCase<TodosListContext>(todosListItemId)
        }

fun updateTodosListItemUseCase(todosListItemId: Long, isTodosListItemChecked: Boolean): Reader<TodosListContext, Unit> =
    ReaderApi.ask<TodosListContext>()
        .flatMap {
            editTodoDetailsUseCase<TodosListContext>(todosListItemId)
        }
        .flatMap {
            getTodosListItemsUseCase()
        }