package com.example.bookapp.data.remote.api

import com.example.bookapp.data.remote.dto.BookDetailResponse
import com.example.bookapp.data.remote.dto.BookResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApiService {
    @GET("new")
    suspend fun getNewBooks(): BookResponse

    @GET("books/{isbn13}")
    suspend fun getBookDetail(@Path("isbn13") isbn13: String): BookDetailResponse

    @GET("search/{query}/{page}")
    suspend fun searchBooks(
        @Path("query") query: String,
        @Path("page") page: Int
    ): BookResponse

}