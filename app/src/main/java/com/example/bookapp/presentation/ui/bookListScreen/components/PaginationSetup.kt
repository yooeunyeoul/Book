package com.example.bookapp.presentation.ui.bookListScreen.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@Composable
fun SetupPagination(
    listState: LazyListState,
    itemCount: Int,
    shouldLoadMore: () -> Boolean,
    loadMoreItems: () -> Unit
) {
    LaunchedEffect(listState, itemCount) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .map { it.lastOrNull()?.index ?: 0 }
            .distinctUntilChanged()
            .filter { it >= itemCount - 5 }
            .collect {
                if (shouldLoadMore()) {
                    Log.e("Pagination", "Loading more items")
                    loadMoreItems()
                }
            }
    }
}

@Composable
fun SetupPagination(
    gridState: LazyGridState,
    itemCount: Int,
    shouldLoadMore: () -> Boolean,
    loadMoreItems: () -> Unit
) {
    LaunchedEffect(gridState, itemCount) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }
            .map { it.lastOrNull()?.index ?: 0 }
            .distinctUntilChanged()
            .filter { it >= itemCount - 5 }
            .collect {
                if (shouldLoadMore()) {
                    Log.e("Pagination", "Loading more items")
                    loadMoreItems()
                }
            }
    }
}
