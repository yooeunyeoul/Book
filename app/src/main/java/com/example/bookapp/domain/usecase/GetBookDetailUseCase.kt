package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.mapper.toUiModel
import com.example.bookapp.domain.repository.BookRepository
import com.example.bookapp.presentation.model.BookDetailUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBookDetailUseCase @Inject constructor(private val bookRepository: BookRepository) {
    fun execute(isbn13: String): Flow<BookDetailUiModel> {
        return bookRepository.getBookDetail(isbn13).map { it.toUiModel() }
    }
}