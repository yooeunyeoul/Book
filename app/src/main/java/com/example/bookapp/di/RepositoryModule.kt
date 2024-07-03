package com.example.bookapp.di

import com.example.bookapp.data.remote.api.BookApiService
import com.example.bookapp.data.repositoryimpl.BookRepositoryImpl
import com.example.bookapp.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBookRepository(bookApiService: BookApiService): BookRepository {
        return BookRepositoryImpl(bookApiService)
    }
}