package com.example.bookshelfapp.network

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface BookApiService {

    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = 20
    ): BookResponse

    @GET
    suspend fun getBooksWithCompleteUrl(
        @Url url: String // Use the full URL as a parameter
    ): BookResponse
}

@Serializable
data class BookResponse(
    val items: List<VolumeItem>
)

@Serializable
data class VolumeItem(
    val id: String,
    val volumeInfo: VolumeInfo
)

@Serializable
data class VolumeInfo(
    val title: String,
    val authors: List<String>? = null,
    val imageLinks: ImageLinks? = null
)

@Serializable
data class ImageLinks(
    val thumbnail: String? = null
)


@Serializable
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>? = null,
    val thumbnail: String? = null
)
