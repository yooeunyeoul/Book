package com.example.bookapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.usecase.GetBookDetailUseCase
import com.example.bookapp.presentation.model.BookDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookDetailUseCase: GetBookDetailUseCase
) : ViewModel() {

    private val _bookDetail = MutableStateFlow<BookDetailUiModel?>(null)
    val bookDetail: StateFlow<BookDetailUiModel?> = _bookDetail

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