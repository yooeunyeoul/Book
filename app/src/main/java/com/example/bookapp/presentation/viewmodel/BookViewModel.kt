package com.example.bookapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.usecase.GetNewBooksUseCase
import com.example.bookapp.domain.usecase.SearchBooksUseCase
import com.example.bookapp.presentation.model.BookListUiModel
import com.example.bookapp.presentation.model.handleResultState
import com.example.bookapp.presentation.util.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getNewBooksUseCase: GetNewBooksUseCase,
    private val searchBooksUseCase: SearchBooksUseCase
) : ViewModel() {

    private val _booksState = MutableStateFlow(BookListUiModel(isLoading = true))
    val booksState: StateFlow<BookListUiModel> = _booksState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    init {
        getNewBooks()
    }

    fun getNewBooks() {
        viewModelScope.launch {
            getNewBooksUseCase.execute()
                .asResult()
                .collect { result ->
                    _booksState.update { it.handleResultState(result) }
                }
        }
    }

    fun searchBooks(query: String, isLoadMore: Boolean = false) {
        val nextPage = if (isLoadMore) (_booksState.value.books.size / _booksState.value.pageSize) + 1 else FIRST_PAGE
        _booksState.update {
            if (isLoadMore) {
                it.copy(isLoading = true)
            } else {
                it.copy(isLoading = true, lastQuery = query, totalBooks = null)
            }
        }

        viewModelScope.launch {
            searchBooksUseCase.execute(query, nextPage)
                .asResult()
                .collect { result ->
                    _booksState.update { it.handleResultState(result, isLoadMore) }
                }
        }
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun shouldLoadMore(): Boolean {
        return !_booksState.value.isLoading && (_booksState.value.books.size < (_booksState.value.totalBooks ?: Int.MAX_VALUE))
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}
