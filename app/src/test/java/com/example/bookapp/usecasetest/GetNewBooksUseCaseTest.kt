package com.example.bookapp.usecasetest

import com.example.bookapp.domain.mapper.toUiModel
import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.model.BookList
import com.example.bookapp.domain.repository.BookRepository
import com.example.bookapp.domain.usecase.GetNewBooksUseCase
import com.example.bookapp.presentation.model.BookListUiModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class GetNewBooksUseCaseTest {
    @Mock
    private lateinit var bookRepository: BookRepository

    private lateinit var getNewBooksUseCase: GetNewBooksUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getNewBooksUseCase = GetNewBooksUseCase(bookRepository)
    }

    @Test
    fun `execute should return new books as UiModel`() = runTest {
        // Given
        val bookList = listOf(
            Book(title = "Title1", subtitle = "Subtitle1", isbn13 = "ISBN1", price = "$10", image = "Image1", url = "Url1"),
            Book(title = "Title2", subtitle = "Subtitle2", isbn13 = "ISBN2", price = "$20", image = "Image2", url = "Url2")
        )
        val bookListUiModel = BookListUiModel(total = 80,books = bookList.map { it.toUiModel() })

        `when`(bookRepository.getNewBooks()).thenReturn(flowOf(BookList(total = 80,bookList)))

        // When
        val result: Flow<BookListUiModel> = getNewBooksUseCase.execute()

        // Then
        result.collect { uiModel ->
            assertEquals(bookListUiModel, uiModel)
        }

        verify(bookRepository).getNewBooks()
    }
}