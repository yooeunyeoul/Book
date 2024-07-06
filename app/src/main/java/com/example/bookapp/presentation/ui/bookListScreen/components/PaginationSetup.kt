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
fun SetupPagination(viewModel: BookViewModel, listState: LazyListState,itemCount:Int ,lastQuery: String) {
    LaunchedEffect(listState, itemCount) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { it.lastOrNull()?.index ?: 0 }
            .distinctUntilChanged()
            .filter { it >= itemCount - 5 }
            .collect {
                if (viewModel.shouldLoadMore()) {
                    Log.e("Pagination", "Loading more items")
                    viewModel.searchBooks(lastQuery, isLoadMore = true)
                }
            }
    }
}

@Composable
fun SetupPagination(viewModel: BookViewModel, gridState: LazyGridState, itemCount: Int,lastQuery:String) {
    LaunchedEffect(gridState, itemCount) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }
            .map { it.lastOrNull()?.index ?: 0 }
            .distinctUntilChanged()
            .filter { it >= itemCount - 5 }
            .collect {
                if (viewModel.shouldLoadMore()) {
                    Log.e("Pagination", "Loading more items")
                    viewModel.searchBooks(lastQuery, isLoadMore = true)
                }
            }
    }
}
