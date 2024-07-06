package com.example.bookapp.presentation.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.io.IOException

const val RETRY_ATTEMPT_COUNT = 3
const val RETRY_TIME_IN_MILLIS = 1000L

sealed class ResultState<out T> {
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: Throwable) : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
}

fun <T> Flow<T>.asResult(): Flow<ResultState<T>> {
    return this
        .map<T, ResultState<T>> {
            ResultState.Success(it)
        }
        .onStart {
            emit(ResultState.Loading)
        }
        .retryWhen { cause, attempt ->
            if (cause is IOException && attempt < RETRY_ATTEMPT_COUNT) {
                delay(RETRY_TIME_IN_MILLIS)
                true
            } else {
                false
            }
        }
        .catch { emit(ResultState.Error(it)) }
}
