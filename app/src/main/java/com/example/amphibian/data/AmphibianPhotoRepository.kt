package com.example.amphibian.data

import com.example.amphibian.network.Amphibian
import com.example.amphibian.network.AmphibianApiService

interface AmphibianRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

class NetworkAmphibianRepository(
    private val amphibianApiService: AmphibianApiService
) : AmphibianRepository {

    override suspend fun getAmphibians(): List<Amphibian> {
        val response = amphibianApiService.getAmphibians()
        return response.map { amphibianItem ->
            Amphibian(
                name = amphibianItem.name,
                type = amphibianItem.type,
                description = amphibianItem.description,
                imgSrc = amphibianItem.imgSrc.replace("http://", "https://")
            )
        }
    }
}
