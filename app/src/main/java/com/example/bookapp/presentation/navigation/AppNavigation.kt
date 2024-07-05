package com.example.bookapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookapp.presentation.ui.bookListScreen.BookListScreen
import com.example.bookapp.presentation.ui.booksDetailScreen.BookDetailScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "bookList") {
        composable("bookList") {
            BookListScreen(
                onBookSelected = { isbn13 ->
                    navController.navigate("bookDetail/$isbn13")
                }
            )
        }
        composable("bookDetail/{isbn13}") { backStackEntry ->
            val isbn13 = backStackEntry.arguments?.getString("isbn13") ?: ""
            BookDetailScreen(isbn13 = isbn13)
        }
    }
}
