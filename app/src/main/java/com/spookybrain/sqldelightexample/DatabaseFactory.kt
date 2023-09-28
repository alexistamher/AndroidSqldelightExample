package com.spookybrain.sqldelightexample

import android.annotation.SuppressLint
import android.content.Context
import com.spookybrain.database.NoteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver

class DatabaseFactory private constructor() {
    lateinit var db: NoteDatabase

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: DatabaseFactory? = null

        @SuppressLint("StaticFieldLeak")
        val instance = INSTANCE ?: synchronized(this) {
            INSTANCE ?: DatabaseFactory().apply {
                INSTANCE = this
            }
        }
    }

    fun setContext(context: Context) {
        val driver = AndroidSqliteDriver(
            NoteDatabase.Schema, context, "notes.db"
        )
        db = NoteDatabase.invoke(driver)
    }
}