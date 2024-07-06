package com.example.bookapp.presentation.model

data class BookUiModel(
    val title: String,
    val subtitle: String,
    val isbn13: String,
    val price: String,
    val image: String,
    val url: String
)