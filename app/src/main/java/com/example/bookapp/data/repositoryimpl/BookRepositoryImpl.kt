package com.example.bookapp.data.repositoryimpl

import com.example.bookapp.data.remote.api.BookApiService
import com.example.bookapp.data.remote.mapper.toDomainModel
import com.example.bookapp.domain.model.BookDetail
import com.example.bookapp.domain.model.BookListWithTotal
import com.example.bookapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookRepositoryImpl(private val bookApiService: BookApiService) : BookRepository {

    override fun getNewBooks(): Flow<BookListWithTotal> = flow {
        val books = bookApiService.getNewBooks().toDomainModel()
        emit(books)
    }

    override fun getBookDetail(isbn13: String): Flow<BookDetail> = flow {
        val bookDetail = bookApiService.getBookDetail(isbn13).toDomainModel()
        emit(bookDetail)
    }

    override fun searchBooks(query: String, page: Int): Flow<BookListWithTotal> = flow {
        val books = bookApiService.searchBooks(query, page).toDomainModel()
        emit(books)
    }
}