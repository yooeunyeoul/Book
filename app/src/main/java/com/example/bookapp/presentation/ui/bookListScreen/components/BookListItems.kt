package com.example.bookapp.presentation.ui.bookListScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.bookapp.presentation.model.BookUiModel
import com.example.bookapp.presentation.util.clickableSingle

@Composable
fun ListView(
    books: List<BookUiModel>,
    listState: LazyListState,
    onBookSelected: (String) -> Unit
) {
    LazyColumn(state = listState) {
        items(books) { book ->
            BookListItem(book = book, onBookSelected = onBookSelected)
        }
    }
}

@Composable
fun GridView(
    books: List<BookUiModel>,
    gridState: LazyGridState,
    onBookSelected: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = gridState,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(books) { book ->
            BookGridItem(book = book, onBookSelected = onBookSelected)
        }
    }
}

@Composable
fun BookListItem(book: BookUiModel, onBookSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickableSingle { onBookSelected(book.isbn13) }
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        AsyncImage(
            model = book.image,
            contentDescription = book.title,
            modifier = Modifier.size(80.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(text = book.title, style = MaterialTheme.typography.titleLarge)
            Text(
                text = book.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(text = book.price, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun BookGridItem(book: BookUiModel, onBookSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickableSingle { onBookSelected(book.isbn13) }
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        AsyncImage(
            model = book.image,
            contentDescription = book.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = book.title, style = MaterialTheme.typography.titleLarge)
        Text(text = book.subtitle, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        Text(text = book.price, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun BookGridItemPreview() {
    val book = BookUiModel(
        title = "Sample Book Title",
        subtitle = "Sample Book Subtitle",
        isbn13 = "1234567890123",
        price = "$29.99",
        image = "https://itbook.store/img/books/9781617291609.png",
        url = "https://itbook.store/books/9781617291609"
    )
    BookGridItem(book = book) {}
}

@Preview(showBackground = true)
@Composable
fun BookListItemPreview() {
    val book = BookUiModel(
        title = "Sample Book Title",
        subtitle = "Sample Book Subtitle",
        isbn13 = "1234567890123",
        price = "$29.99",
        image = "https://itbook.store/img/books/9781617291609.png",
        url = "https://itbook.store/books/9781617291609"
    )
    BookListItem(book = book) {}
}
