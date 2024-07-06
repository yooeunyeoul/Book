package com.example.bookapp.presentation.viewmodel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private var isInitialized = false

    suspend fun initialize(initializer: suspend () -> Unit) {
        if (!isInitialized) {
            isInitialized = true
            initializer()
        }
    }
}