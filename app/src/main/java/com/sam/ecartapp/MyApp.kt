package com.sam.ecartapp

import android.app.Application

class Application:Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferenceManager.init(this)
    }
}