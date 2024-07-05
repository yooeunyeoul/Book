package com.example.bookapp.presentation.ui.bookListScreen.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import com.example.bookapp.presentation.viewmodel.BookViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun setupPagination(viewModel: BookViewModel, listState: LazyListState, itemCount: Int) {
    LaunchedEffect(listState, itemCount) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { it.lastOrNull()?.index ?: 0 }
            .distinctUntilChanged()
            .filter { it >= itemCount - 5 }
            .collect { index ->
                if (viewModel.shouldLoadMore()) {
                    Log.e("Pagination", "Loading more items")
                    viewModel.searchBooks(viewModel.lastQuery, isLoadMore = true)
                }
            }
    }
}

@Composable
fun setupPagination(viewModel: BookViewModel, gridState: LazyGridState, itemCount: Int) {
    LaunchedEffect(gridState, itemCount) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }
            .map { it.lastOrNull()?.index ?: 0 }
            .distinctUntilChanged()
            .filter { it >= itemCount - 5 }
            .collect { index ->
                if (viewModel.shouldLoadMore()) {
                    Log.e("Pagination", "Loading more items")
                    viewModel.searchBooks(viewModel.lastQuery, isLoadMore = true)
                }
            }
    }
}
