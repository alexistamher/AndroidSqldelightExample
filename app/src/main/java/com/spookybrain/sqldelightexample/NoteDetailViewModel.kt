package com.spookybrain.sqldelightexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class NoteDetailViewModel(
    private val repository: NoteRepository = NoteRepository()
) : ViewModel() {
    private val dispatcher = CoroutineScope(Dispatchers.IO)
    private var noteId: Long? = null
    var title: String = ""
        private set
    var content: String = ""
        private set

    private val _validForm: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val validForm: LiveData<Boolean>
        get() = _validForm

    fun getNoteById(id: Long) {
        dispatcher.launch {
            repository.getNoteById(id)?.let { note ->
                noteId = note.id
                title = note.title
                content = note.content
            }
        }
    }

    fun insertNote() {
        val note = Note(noteId, title, content, Date().time)
        dispatcher.launch {
            repository.insertNote(note)
        }
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