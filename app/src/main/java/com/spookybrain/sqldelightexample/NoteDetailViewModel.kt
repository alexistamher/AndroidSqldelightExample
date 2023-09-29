package com.spookybrain.sqldelightexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.Date
import javax.inject.Inject

class NoteDetailViewModel @Inject constructor(
    private val repository: NoteRepository
) {
    private var noteId: Long? = null
    var title: String = ""
        private set
    var content: String = ""
        private set

    private val _validForm: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val validForm: LiveData<Boolean>
        get() = _validForm

    fun getNoteById(id: Long) {
        repository.getNoteById(id)?.let { note ->
            noteId = note.id
            title = note.title
            content = note.content
        }
    }

    fun insertNote() {
        val note = Note(noteId, title, content, Date().time)
        repository.insertNote(note)
    }

    fun setTitle(current: String) {
        title = current
        validateForm()
    }

    fun setContent(current: String) {
        content = current
        validateForm()
    }

    private fun validateForm() {
        _validForm.value = title.isNotEmpty() && content.isNotEmpty()
    }
}