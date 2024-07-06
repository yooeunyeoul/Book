package com.example.bookapp.domain.mapper

import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.model.BookDetail
import com.example.bookapp.domain.model.BookList
import com.example.bookapp.presentation.model.BookDetailUiModel
import com.example.bookapp.presentation.model.BookListUiModel
import com.example.bookapp.presentation.model.BookUiModel

fun Book.toUiModel(): BookUiModel {
    return BookUiModel(
        title = this.title,
        subtitle = this.subtitle,
        isbn13 = this.isbn13,
        price = this.price,
        image = this.image,
        url = this.url
    )
}

fun BookList.toUiModel(): BookListUiModel {
    val books = this.books.map { it.toUiModel() }
    return BookListUiModel(
        total = this.total,
        books = books
    )
}

fun BookDetail.toUiModel(): BookDetailUiModel {
    return BookDetailUiModel(
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