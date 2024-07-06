import com.example.bookapp.domain.mapper.toUiModel
import com.example.bookapp.domain.model.Book
import com.example.bookapp.domain.usecase.GetNewBooksUseCase
import com.example.bookapp.domain.usecase.SearchBooksUseCase
import com.example.bookapp.presentation.model.BookListUiModel
import com.example.bookapp.presentation.viewmodel.BookViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class BookViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var getNewBooksUseCase: GetNewBooksUseCase

    @Mock
    private lateinit var searchBooksUseCase: SearchBooksUseCase

    private lateinit var bookViewModel: BookViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        bookViewModel = BookViewModel(getNewBooksUseCase, searchBooksUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

    @Test
    fun `getNewBooks should update booksState with new books`() = runTest {
        // Given
        val bookList = listOf(
            Book(title = "Title1", subtitle = "Subtitle1", isbn13 = "ISBN1", price = "$10", image = "Image1", url = "Url1"),
            Book(title = "Title2", subtitle = "Subtitle2", isbn13 = "ISBN2", price = "$20", image = "Image2", url = "Url2"),
            Book(title = "Title1", subtitle = "Subtitle1", isbn13 = "ISBN1", price = "$10", image = "Image1", url = "Url1"),
            Book(title = "Title2", subtitle = "Subtitle2", isbn13 = "ISBN2", price = "$20", image = "Image2", url = "Url2"),
            Book(title = "Title1", subtitle = "Subtitle1", isbn13 = "ISBN1", price = "$10", image = "Image1", url = "Url1"),
            Book(title = "Title2", subtitle = "Subtitle2", isbn13 = "ISBN2", price = "$20", image = "Image2", url = "Url2"),
            Book(title = "Title1", subtitle = "Subtitle1", isbn13 = "ISBN1", price = "$10", image = "Image1", url = "Url1"),
            Book(title = "Title2", subtitle = "Subtitle2", isbn13 = "ISBN2", price = "$20", image = "Image2", url = "Url2"),
            Book(title = "Title1", subtitle = "Subtitle1", isbn13 = "ISBN1", price = "$10", image = "Image1", url = "Url1"),
            Book(title = "Title2", subtitle = "Subtitle2", isbn13 = "ISBN2", price = "$20", image = "Image2", url = "Url2")
        )
        val bookListUiModel = BookListUiModel(books = bookList.map { it.toUiModel() })
        val flow: Flow<BookListUiModel> = flowOf(bookListUiModel)

        `when`(getNewBooksUseCase.execute()).thenReturn(flow)

        // When
        bookViewModel.getNewBooks()

        // Then
        val state = bookViewModel.booksState.first()
        assertEquals(bookListUiModel, state)
        verify(getNewBooksUseCase, times(1)).execute()
    }

    @Test
    fun `searchBooks should update booksState with search results`() = runTest {
        // Given
        val query = "Title"
        val bookList = listOf(
            Book(title = "Title1", subtitle = "Subtitle1", isbn13 = "ISBN1", price = "$10", image = "Image1", url = "Url1"),
            Book(title = "Title2", subtitle = "Subtitle2", isbn13 = "ISBN2", price = "$20", image = "Image2", url = "Url2")
        )
        val bookListUiModel = BookListUiModel(books = bookList.map { it.toUiModel() })
        val flow: Flow<BookListUiModel> = flowOf(bookListUiModel)

        `when`(searchBooksUseCase.execute(query, BookViewModel.FIRST_PAGE)).thenReturn(flow)

        // When
        bookViewModel.searchBooks(query)

        // Then
        val state = bookViewModel.booksState.first()
        assertEquals(bookListUiModel, state)
//        verify(searchBooksUseCase).execute(query, BookViewModel.FIRST_PAGE)
    }

    @Test
    fun `searchBooks should handle load more scenario`() = runTest {
        // Given
        val query = "Title"
        val initialBookList = listOf(
            Book(title = "Title1", subtitle = "Subtitle1", isbn13 = "ISBN1", price = "$10", image = "Image1", url = "Url1")
        )
        val moreBooks = listOf(
            Book(title = "Title2", subtitle = "Subtitle2", isbn13 = "ISBN2", price = "$20", image = "Image2", url = "Url2")
        )
        val initialUiModel = BookListUiModel(books = initialBookList.map { it.toUiModel() })
        val moreUiModel = BookListUiModel(books = moreBooks.map { it.toUiModel() })

        val initialFlow: Flow<BookListUiModel> = flowOf(initialUiModel)
        val moreFlow: Flow<BookListUiModel> = flowOf(moreUiModel)

        `when`(searchBooksUseCase.execute(query, BookViewModel.FIRST_PAGE)).thenReturn(initialFlow)
        `when`(searchBooksUseCase.execute(query, 2)).thenReturn(moreFlow)

        // When
        bookViewModel.searchBooks(query)
        bookViewModel.searchBooks(query, isLoadMore = true)

        // Then
        val state = bookViewModel.booksState.first()
        assertTrue(state.books.containsAll(initialUiModel.books))
        assertTrue(state.books.containsAll(moreUiModel.books))
        verify(searchBooksUseCase).execute(query, BookViewModel.FIRST_PAGE)
        verify(searchBooksUseCase).execute(query, 2)
    }
}
