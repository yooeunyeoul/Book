package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.model.BookListWithTotal
import com.example.bookapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(private val bookRepository: BookRepository) {
    fun execute(query: String, page: Int): Flow<BookListWithTotal> = bookRepository.searchBooks(query,page)
}