package com.example.bookapp.presentation.model

import com.example.bookapp.presentation.util.ResultState

data class BookListUiModel(
    val total: Int = 0,
    val books: List<BookUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val lastQuery: String = "",
    val totalBooks: Int? = null,
    val pageSize: Int = 10
)

fun BookListUiModel.handleResultState(
    result: ResultState<BookListUiModel>,
    isLoadMore: Boolean = false
): BookListUiModel {
    return when (result) {
        is ResultState.Success -> {
            val currentBooks =
                if (isLoadMore) this.books + result.data.books else result.data.books
            result.data.copy(
                books = currentBooks,
                isLoading = false,
                errorMessage = null,
                lastQuery = this.lastQuery,
                totalBooks = result.data.total,
                pageSize = result.data.books.size
            )
        }

        is ResultState.Error -> this.copy(
            isLoading = false,
            errorMessage = result.exception.message
        )

        is ResultState.Loading -> this.copy(isLoading = true)
    }
}
