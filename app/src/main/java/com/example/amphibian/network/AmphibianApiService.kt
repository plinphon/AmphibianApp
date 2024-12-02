package com.example.amphibian.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface AmphibianApiService {

    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}

@Serializable
data class Amphibian(
    val name: String,
    val type: String,
    val description: String,
    @SerializedName("img_src") val imgSrc: String
)
