package com.example.bookapp.domain.model

data class BookListWithTotal(
    val total: Int,
    val books: List<Book>
)