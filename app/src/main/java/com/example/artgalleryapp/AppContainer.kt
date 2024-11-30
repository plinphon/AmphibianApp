package com.example.bookshelfapp

import com.example.bookshelfapp.data.BookRepository

interface AppContainer {
    val bookRepository: BookRepository
}
