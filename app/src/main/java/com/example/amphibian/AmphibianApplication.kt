package com.example.amphibian

import android.app.Application
import com.example.amphibian.data.DefaultAppContainer

class AmphibianApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
