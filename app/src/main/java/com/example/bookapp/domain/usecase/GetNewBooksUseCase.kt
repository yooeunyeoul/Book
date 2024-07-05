package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.model.BookListWithTotal
import com.example.bookapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewBooksUseCase @Inject constructor(private val bookRepository: BookRepository) {
    fun execute(): Flow<BookListWithTotal> {
        return bookRepository.getNewBooks()
    }
}