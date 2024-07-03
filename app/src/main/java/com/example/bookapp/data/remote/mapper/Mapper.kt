package com.example.bookapp.data.remote.mapper

import com.example.bookapp.data.remote.dto.BookDTO
import com.example.bookapp.domain.model.Book

fun BookDTO.toDomainModel(): Book {
    return Book(
        title = title,
        subtitle = subtitle,
        isbn13 = isbn13,
        price = price,
        image = image,
        url = url
    )
}