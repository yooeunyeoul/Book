@file:OptIn(ExperimentalMaterialApi::class)

package com.example.bookapp.presentation.ui.bookListScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.example.bookapp.presentation.ui.bookListScreen.components.SetupPagination
import com.example.bookapp.presentation.ui.bookListScreen.components.ViewType
import com.example.bookapp.presentation.viewmodel.BookViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookListScreen(
    onBookSelected: (String) -> Unit,
    viewModel: BookViewModel = hiltViewModel()
) {
    val books by viewModel.books.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    var viewType by rememberSaveable { mutableStateOf(ViewType.LIST) }
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()

    SetupPagination(viewModel, listState, books.size)
    SetupPagination(viewModel, gridState, books.size)

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            if (query.isEmpty()) {
                viewModel.getNewBooks()
            } else {
                viewModel.searchBooks(query)
            }
        }
    )

    Column(
        Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        Row(Modifier.padding(16.dp)) {
            SearchArea(
                modifier = Modifier.weight(1f),
                query = query,
                onQueryChange = { viewModel.updateQuery(it) },
                onSearch = { query ->
                    if (query.isEmpty()) {
                        viewModel.getNewBooks()
                    } else {
                        viewModel.searchBooks(query)
                    }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomToggleButton(viewType, onSelected = { viewType = it })
        }
        Box(Modifier.fillMaxSize()) {
            ContentArea(books, viewType, listState, gridState, isLoading, onBookSelected)
            PullRefreshIndicator(isLoading, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
fun ContentArea(
    books: List<Book>,
    viewType: ViewType,
    listState: LazyListState,
    gridState: LazyGridState,
    isLoading: Boolean,
    onBookSelected: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (!isLoading && books.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "검색 결과 없음", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            when (viewType) {
                ViewType.LIST -> ListView(books, listState, onBookSelected)
                ViewType.GRID -> GridView(books, gridState, onBookSelected)
            }
        }
    }
}
