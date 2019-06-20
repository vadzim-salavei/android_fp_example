package com.github.vadzim_salavei.android_fp_example.ui.navigation

import arrow.data.Reader
import arrow.data.ReaderApi
import arrow.data.map

fun <D : NavigationContext> closeUseCase(): Reader<D, Unit> =
    ReaderApi.ask<D>().map { navigationDependencies ->
        navigationDependencies.navigationDelegate.close()
    }

fun <D : NavigationContext> createTodoDetailsUseCase(): Reader<D, Unit> =
    ReaderApi.ask<D>().map { navigationDependencies ->
        navigationDependencies.navigationDelegate.createTodoDetails()
    }

fun <D : NavigationContext> editTodoDetailsUseCase(todoDetailsId: Long): Reader<D, Unit> =
    ReaderApi.ask<D>().map { navigationDependencies ->
        navigationDependencies.navigationDelegate.editTodoDetails(todoDetailsId)
    }