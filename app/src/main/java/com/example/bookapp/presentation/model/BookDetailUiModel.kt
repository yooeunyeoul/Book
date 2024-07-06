package com.example.bookapp.presentation.model

data class BookDetailUiModel(
    val title: String = "",
    val subtitle: String = "",
    val authors: String = "",
    val publisher: String = "",
    val isbn10: String = "",
    val isbn13: String = "",
    val pages: String = "",
    val year: String = "",
    val rating: String = "",
    val desc: String = "",
    val price: String = "",
    val image: String = "",
    val url: String = "",
    val pdf: Map<String, String>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)