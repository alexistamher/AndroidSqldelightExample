package com.spookybrain.sqldelightexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: NotesListAdapter
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvNotes: RecyclerView = findViewById(R.id.rv_notes)
        val btnAdd: FloatingActionButton = findViewById(R.id.btn_add)

        adapter = NotesListAdapter(::onDeletePressed, ::launchNoteDetailActivity)
        rvNotes.adapter = adapter

        btnAdd.setOnClickListener {
            launchNoteDetailActivity()
        }

        listenNotes()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getNotes()
    }

    private fun listenNotes() {
        viewModel.state.observeForever { notes ->
            runOnUiThread {
                adapter.setNotes(notes.toTypedArray())
            }
        }
    }

    private fun onDeletePressed(id: Long) {
        viewModel.deleteNote(id)
    }

    private fun launchNoteDetailActivity(id: Long? = null) {
        Intent(this, NoteDetailActivity::class.java).run {
            putExtra(NoteDetailActivity.EXTRA_NOTE_ID, id)
            startActivity(this)
        }
    }
}