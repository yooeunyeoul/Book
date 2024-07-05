package com.example.bookapp.presentation.ui.bookListScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bookapp.domain.model.Book
import com.example.bookapp.presentation.ui.bookListScreen.components.CustomToggleButton
import com.example.bookapp.presentation.ui.bookListScreen.components.GridView
import com.example.bookapp.presentation.ui.bookListScreen.components.ListView
import com.example.bookapp.presentation.ui.bookListScreen.components.SearchArea
import com.example.bookapp.presentation.ui.bookListScreen.components.ViewType
import com.example.bookapp.presentation.ui.bookListScreen.components.setupPagination
import com.example.bookapp.presentation.viewmodel.BookViewModel

@Composable
fun BookListScreen(viewModel: BookViewModel = hiltViewModel()) {
    val books by viewModel.books.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    var viewType by remember { mutableStateOf(ViewType.LIST) }
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.getNewBooks()
    }

    setupPagination(viewModel, listState, books.size)
    setupPagination(viewModel, gridState, books.size)

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(16.dp)) {
            SearchArea(
                modifier = Modifier.weight(1f),
                onSearch = { query ->
                    viewModel.searchBooks(query)
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomToggleButton(viewType, onSelected = { viewType = it })
        }
        ContentArea(books, viewType, listState, gridState, isLoading)
    }
}

@Composable
fun ContentArea(
    books: List<Book>,
    viewType: ViewType,
    listState: LazyListState,
    gridState: LazyGridState,
    isLoading: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (viewType) {
            ViewType.LIST -> ListView(books, listState)
            ViewType.GRID -> GridView(books, gridState)
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center).size(54.dp)
            )
        }
    }
}
