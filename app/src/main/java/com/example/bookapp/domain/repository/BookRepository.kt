package com.example.bookapp.domain.repository

import com.example.bookapp.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getNewBooks(): Flow<List<Book>>
    fun getBookDetail(isbn13: String): Flow<Book>
}