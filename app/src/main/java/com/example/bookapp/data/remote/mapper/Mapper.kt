package com.example.bookapp.data.remote.mapper

import com.example.bookapp.data.remote.dto.BookDTO
import com.example.bookapp.data.remote.dto.BookDetailResponse
import com.example.bookapp.data.remote.dto.BookResponse
import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.model.BookDetail
import com.example.bookapp.domain.model.BookListWithTotal


fun BookDTO.toDomainModel(): Book {
    return Book(
        title = this.title,
        subtitle = this.subtitle,
        isbn13 = this.isbn13,
        price = this.price,
        image = this.image,
        url = this.url
    )
}

fun BookResponse.toDomainModel(): BookListWithTotal {
    val totalBooks = this.total.toIntOrNull() ?: 0
    val books = this.books.map { it.toDomainModel() }
    return BookListWithTotal(total = totalBooks, books = books)
}

fun BookDetailResponse.toDomainModel(): BookDetail {
    return BookDetail(
        title = this.title,
        subtitle = this.subtitle,
        authors = this.authors,
        publisher = this.publisher,
        isbn10 = this.isbn10,
        isbn13 = this.isbn13,
        pages = this.pages,
        year = this.year,
        rating = this.rating,
        desc = this.desc,
        price = this.price,
        image = this.image,
        url = this.url,
        pdf = this.pdf
    )
}