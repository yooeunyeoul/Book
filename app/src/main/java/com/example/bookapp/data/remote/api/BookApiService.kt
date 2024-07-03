package com.example.bookapp.data.remote.api

import com.example.bookapp.data.remote.dto.BookDTO
import com.example.bookapp.data.remote.dto.BookResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApiService {
    @GET("new")
    suspend fun getNewBooks(): BookResponse

    @GET("books/{isbn13}")
    suspend fun getBookDetail(@Path("isbn13") isbn13: String): BookDTO
}