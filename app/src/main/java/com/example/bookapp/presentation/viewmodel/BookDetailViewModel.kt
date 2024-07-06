package com.example.bookapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.usecase.GetBookDetailUseCase
import com.example.bookapp.presentation.model.BookDetailUiModel
import com.example.bookapp.presentation.util.ResultState
import com.example.bookapp.presentation.util.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookDetailUseCase: GetBookDetailUseCase
) : ViewModel() {

    private val _bookDetailState = MutableStateFlow(BookDetailUiModel(isLoading = true))
    val bookDetailState: StateFlow<BookDetailUiModel> = _bookDetailState

    fun getBookDetail(isbn13: String) {
        viewModelScope.launch {
            getBookDetailUseCase.execute(isbn13)
                .asResult()
                .collect { result ->
                    _bookDetailState.update {
                        when (result) {
                            is ResultState.Success -> result.data.copy(isLoading = false, errorMessage = null)
                            is ResultState.Error -> it.copy(isLoading = false, errorMessage = result.exception.message)
                            is ResultState.Loading -> it.copy(isLoading = true)
                        }
                    }
                }
        }
    }
}
