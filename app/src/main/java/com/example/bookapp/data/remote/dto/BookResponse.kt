package com.example.bookapp.data.remote.dto

data class BookResponse(
    val error: String,
    val total: String,
    val books: List<BookDTO>
)