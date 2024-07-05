package com.example.bookapp.domain.usecase

import com.example.bookapp.domain.model.BookDetail
import com.example.bookapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookDetailUseCase @Inject constructor(private val bookRepository: BookRepository) {
    fun execute(isbn13: String): Flow<BookDetail> = bookRepository.getBookDetail(isbn13)
}