package com.example.bookshelfapp.data

import android.util.Log
import com.example.bookshelfapp.network.BookApiService
import com.example.bookshelfapp.network.Book

interface BookRepository {
    suspend fun getBooks(query: String): List<Book>
}

class NetworkBooksRepository(
    private val bookApiService: BookApiService
) : BookRepository {

    override suspend fun getBooks(query: String): List<Book> {
        val response = bookApiService.getBooks(query)
        return response.items.map { volumeItem ->
            val book = Book(
                id = volumeItem.id,
                title = volumeItem.volumeInfo.title,
                authors = volumeItem.volumeInfo.authors,
                thumbnail = volumeItem.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")
            )
            book
        }
    }
}
