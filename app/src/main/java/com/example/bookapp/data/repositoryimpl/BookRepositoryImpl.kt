package com.example.bookapp.data.repositoryimpl

import com.example.bookapp.data.remote.api.BookApiService
import com.example.bookapp.data.remote.mapper.toDomainModel
import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookRepositoryImpl(private val bookApiService: BookApiService) : BookRepository {

    override fun getNewBooks(): Flow<List<Book>> = flow {
        val books = bookApiService.getNewBooks().books.map { it.toDomainModel() }
        emit(books)
    }

    override fun getBookDetail(isbn13: String): Flow<Book> = flow {
        val book = bookApiService.getBookDetail(isbn13).toDomainModel()
        emit(book)
    }
}