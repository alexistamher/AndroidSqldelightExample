package com.spookybrain.sqldelightexample

import android.app.Application
import com.spookybrain.database.NoteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Binds
    abstract fun bindsNoteRepository(repository: NoteRepositoryImpl): NoteRepository

    companion object {
        @Singleton
        @Provides
        fun providesDatabase(application: Application): NoteDatabase {
            val driver = AndroidSqliteDriver(
                NoteDatabase.Schema, application, "notes.db"
            )
            return NoteDatabase.invoke(driver)
        }
    }
}