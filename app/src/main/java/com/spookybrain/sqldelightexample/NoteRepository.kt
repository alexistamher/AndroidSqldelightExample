package com.spookybrain.sqldelightexample

interface NoteRepository {
    fun getNotes(): List<Note>

    fun getNoteById(id: Long): Note?

    fun insertNote(note: Note)

    fun deleteNote(id: Long)
}