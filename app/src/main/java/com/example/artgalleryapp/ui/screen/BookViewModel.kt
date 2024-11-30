package com.example.bookshelfapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelfapp.BookShelfApplication
import com.example.bookshelfapp.data.BookRepository
import com.example.bookshelfapp.network.Book
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface BookUiState {
    data class Success(val books: List<Book>) : BookUiState
    data class Error(val message: String) : BookUiState
    object Loading : BookUiState
}

// ViewModel for managing book data and state
class BookViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    var bookUiState: BookUiState by mutableStateOf(BookUiState.Loading)
        private set

    private var retryQuery: String? = null

    init {
        getBooks("default query")
    }

    fun getBooks(query: String) {
        retryQuery = query
        viewModelScope.launch {
            bookUiState = BookUiState.Loading
            bookUiState = try {
                val books = bookRepository.getBooks(query)
                if (books.isEmpty()) {
                    BookUiState.Error("No books found for the query.")
                } else {
                    BookUiState.Success(books)
                }
            } catch (e: IOException) {
                BookUiState.Error("Network error. Please try again.")
            } catch (e: Exception) {
                BookUiState.Error("An unexpected error occurred.")
            }
        }
    }

    fun retry() {
        retryQuery?.let { getBooks(it) }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BookShelfApplication
                val bookRepository = application.container.bookRepository
                BookViewModel(bookRepository = bookRepository)
            }
        }
    }
}
