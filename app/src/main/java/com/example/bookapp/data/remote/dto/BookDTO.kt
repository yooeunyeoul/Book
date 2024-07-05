package com.example.bookapp.data.remote.dto

data class BookDTO(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val image: String,
    val url: String
)
