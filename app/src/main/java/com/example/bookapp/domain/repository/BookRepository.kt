package com.example.bookapp.domain.repository

import com.example.bookapp.domain.model.BookDetail
import com.example.bookapp.domain.model.BookList
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getNewBooks(): Flow<BookList>
    fun getBookDetail(isbn13: String): Flow<BookDetail>
    fun searchBooks(query: String, page: Int): Flow<BookList>
}