package com.github.vadzim_salavei.android_fp_example.domain.model

import gsonpath.AutoGsonAdapter

@AutoGsonAdapter
data class Todo(
    val id: Long,
    val title: String,
    val content: String,
    val checked: Boolean
)