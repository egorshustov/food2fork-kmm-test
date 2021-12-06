package com.egorshustov.food2forkkmm.domain.util

sealed class Result {

    data class Success<out T>(val data: T) : Result()
    data class Error(val exception: Exception) : Result()
    object Loading : Result()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}