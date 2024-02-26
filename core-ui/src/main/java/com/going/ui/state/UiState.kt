package com.going.ui.state

sealed interface UiState<out T> {
    object Empty : UiState<Nothing>

    object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T,
    ) : UiState<T>

    data class Failure(
        val msg: String,
    ) : UiState<Nothing>
}
