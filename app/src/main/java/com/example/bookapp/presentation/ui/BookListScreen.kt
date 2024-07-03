@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.bookapp.presentation.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bookapp.presentation.viewmodel.BookViewModel

@Composable
fun BookListScreen(viewModel: BookViewModel = hiltViewModel()) {
    val books by viewModel.books.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getBooks()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Book List") })
        }
    ) {
        LazyColumn {
            items(books) { book ->
                Text(text = book.title)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookListScreenPreview() {
    BookListScreen()
}