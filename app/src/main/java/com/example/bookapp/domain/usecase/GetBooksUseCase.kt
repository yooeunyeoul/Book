package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(private val bookRepository: BookRepository) {
    fun execute(): Flow<List<Book>> {
        return bookRepository.getNewBooks()
    }
}