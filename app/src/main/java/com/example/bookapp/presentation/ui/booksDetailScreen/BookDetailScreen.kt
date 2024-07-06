package com.example.bookapp.presentation.ui.booksDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.bookapp.presentation.model.BookDetailUiModel
import com.example.bookapp.presentation.viewmodel.BookDetailViewModel

@Composable
fun BookDetailScreen(isbn13: String, viewModel: BookDetailViewModel = hiltViewModel()) {
    val bookDetail by viewModel.bookDetailState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize {
            viewModel.getBookDetail(isbn13)
        }
    }

    if (bookDetail.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        bookDetail.let { book ->
            BookDetailContent(book = book)
        }
    }
}

@Composable
fun BookDetailContent(book: BookDetailUiModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = book.image,
            contentDescription = book.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.title,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = book.subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Authors: ${book.authors}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Publisher: ${book.publisher}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "ISBN-10: ${book.isbn10}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "ISBN-13: ${book.isbn13}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Pages: ${book.pages}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Year: ${book.year}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Rating: ${book.rating}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.desc,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.price,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        book.pdf?.let { pdfs ->
            pdfs.forEach { (chapter, _) ->
                TextButton(onClick = {  }) {
                    Text(text = chapter)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailContentPreview() {
    val bookDetail = BookDetailUiModel(
        title = "Sample Book",
        subtitle = "Subtitle",
        authors = "Author",
        publisher = "Publisher",
        isbn10 = "1234567890",
        isbn13 = "1234567890123",
        pages = "200",
        year = "2021",
        rating = "4.5",
        desc = "This is a sample book description.",
        price = "$20.00",
        image = "https://itbook.store/img/books/9781617294136.png",
        url = "https://itbook.store/books/9781617294136",
        pdf = mapOf("Chapter 1" to "https://itbook.store/files/9781617294136/chapter1.pdf")
    )
    BookDetailContent(book = bookDetail)
}
