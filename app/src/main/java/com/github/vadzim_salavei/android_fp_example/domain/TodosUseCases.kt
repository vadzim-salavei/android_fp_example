package com.github.vadzim_salavei.android_fp_example.domain

import arrow.core.Either
import arrow.data.ReaderT
import arrow.effects.ForIO
import arrow.effects.IO

fun <D : TodosContext> getTodosUseCase(): ReaderT<ForIO, D, IO<Either<Throwable, List<Todo>>>> = TODO()