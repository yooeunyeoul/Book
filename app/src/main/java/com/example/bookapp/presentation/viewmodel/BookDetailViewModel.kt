package com.example.bookapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.model.BookDetail
import com.example.bookapp.domain.usecase.GetBookDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookDetailUseCase: GetBookDetailUseCase
) : ViewModel() {

    private val _bookDetail = MutableStateFlow<BookDetail?>(null)
    val bookDetail: StateFlow<BookDetail?> = _bookDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getBookDetail(isbn13: String) {
        viewModelScope.launch {
            _isLoading.value = true
            getBookDetailUseCase.execute(isbn13).collect { bookDetail ->
                _bookDetail.value = bookDetail
                _isLoading.value = false
            }
        }
    }
}
