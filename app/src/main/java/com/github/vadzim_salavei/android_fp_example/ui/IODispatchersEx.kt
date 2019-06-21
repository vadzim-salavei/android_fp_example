package com.github.vadzim_salavei.android_fp_example.ui

import android.os.Handler
import android.os.Looper
import arrow.effects.IODispatchers
import kotlin.coroutines.*

val IODispatchers.mainThread: CoroutineContext
    get() {
        return EmptyCoroutineContext + object : AbstractCoroutineContextElement(ContinuationInterceptor),
            ContinuationInterceptor {
            override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
                object : Continuation<T> {

                    private val handler = Handler(Looper.getMainLooper())

                    override val context: CoroutineContext
                        get() = continuation.context

                    override fun resumeWith(result: Result<T>) {
                        handler.post { continuation.resumeWith(result) }
                    }
                }
        }
    }