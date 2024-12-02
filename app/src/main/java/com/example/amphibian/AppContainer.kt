package com.example.amphibian

import com.example.amphibian.data.AmphibianRepository

interface AppContainer {
    val amphibianRepository: AmphibianRepository
}
