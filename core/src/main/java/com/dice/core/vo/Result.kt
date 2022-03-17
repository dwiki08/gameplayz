package com.dice.core.vo

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val code: Int? = null,
        val errorMessage: String? = null
    ) : Result<Nothing>()
}