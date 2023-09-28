package com.spookybrain.sqldelightexample

import com.spookybrain.database.NoteDatabase

class NoteRepository(
    private val db: NoteDatabase = DatabaseFactory.instance.db
) {
    fun getNotes(): List<Note> {
        return db.noteQueries.getNotes().executeAsList().map { it.asModel() }
    }

    fun getNoteById(id: Long): Note? {
        return db.noteQueries.getNoteById(id).executeAsOneOrNull()?.asModel()
    }

    fun insertNote(note: Note) {
        with(note) {
            db.noteQueries.insertNote(id, title, content, created)
        }
    }

    fun deleteNote(id: Long) {
        db.noteQueries.deleteNote(id)
    }
}