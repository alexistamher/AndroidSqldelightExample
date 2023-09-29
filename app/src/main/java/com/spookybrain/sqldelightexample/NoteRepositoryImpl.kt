package com.spookybrain.sqldelightexample

import com.spookybrain.database.NoteDatabase
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val db: NoteDatabase
) : NoteRepository {
    override fun getNotes(): List<Note> {
        return db.noteQueries.getNotes().executeAsList().map { it.asModel() }
    }

    override fun getNoteById(id: Long): Note? {
        return db.noteQueries.getNoteById(id).executeAsOneOrNull()?.asModel()
    }

    override fun insertNote(note: Note) {
        with(note) {
            db.noteQueries.insertNote(id, title, content, created)
        }
    }

    override fun deleteNote(id: Long) {
        db.noteQueries.deleteNote(id)
    }
}