package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.mapper.toUiModel
import com.example.bookapp.domain.model.BookList
import com.example.bookapp.domain.repository.BookRepository
import com.example.bookapp.presentation.model.BookListUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNewBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    fun execute(): Flow<BookListUiModel> {
        return bookRepository.getNewBooks().map { it.toUiModel() }
    }
}