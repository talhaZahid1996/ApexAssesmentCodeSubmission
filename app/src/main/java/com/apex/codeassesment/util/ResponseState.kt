package com.apex.codeassesment.util

data class ResponseState<T>(
    val data: T? = null,
    val error: String = "",
    val isLoading: Boolean = false
)