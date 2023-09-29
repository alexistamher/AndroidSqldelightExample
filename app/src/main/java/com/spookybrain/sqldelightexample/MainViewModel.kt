package com.spookybrain.sqldelightexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: NoteRepository
) {
    private val _state: MutableLiveData<List<Note>> = MutableLiveData(emptyList())
    val state: LiveData<List<Note>>
        get() = _state

    fun getNotes() {
        val result = repository.getNotes()
        _state.postValue(result)
    }

    fun deleteNote(id: Long) {
        repository.deleteNote(id)
        getNotes()
    }
}