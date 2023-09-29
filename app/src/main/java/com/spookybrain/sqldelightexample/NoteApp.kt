package com.spookybrain.sqldelightexample

import android.app.Application

class NoteApp: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }
}