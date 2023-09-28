package com.spookybrain.sqldelightexample

import android.app.Application

class NoteApp: Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseFactory.instance.setContext(this)
    }
}