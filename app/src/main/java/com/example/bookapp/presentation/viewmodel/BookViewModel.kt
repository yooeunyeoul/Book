package com.example.bookapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.usecase.GetNewBooksUseCase
import com.example.bookapp.domain.usecase.SearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getNewBooksUseCase: GetNewBooksUseCase,
    private val searchBooksUseCase: SearchBooksUseCase
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    var lastQuery: String = ""
    private var totalBooks: Int? = null // 전체 책의 수
    private var pageSize: Int = 10 // 페이지당 항목 수, 기본값은 10

    fun getNewBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            getNewBooksUseCase.execute().collect { result ->
                _books.value = result.books
                totalBooks = result.total
                _isLoading.value = false
            }
        }
    }

    fun searchBooks(query: String, isLoadMore: Boolean = false) {
        if (isLoadMore) {
            _isLoading.value = true
        } else {
            lastQuery = query
            _books.value = emptyList()
            _isLoading.value = true
            totalBooks = null // 새로운 검색 시 전체 책의 수 초기화
        }

        viewModelScope.launch {
            searchBooksUseCase.execute(
                query,
                if (isLoadMore) (_books.value.size / pageSize) + 1 else 1
            ).collect { result ->
                _books.value = if (isLoadMore) _books.value + result.books else result.books
                _isLoading.value = false
                totalBooks = result.total // 전체 책의 수 저장
                pageSize = result.books.size // 현재 페이지에서 반환된 항목 수를 페이지 크기로 설정
            }
        }
    }

    fun shouldLoadMore(): Boolean {
        return !_isLoading.value && (_books.value.size < (totalBooks ?: Int.MAX_VALUE))
    }
}
