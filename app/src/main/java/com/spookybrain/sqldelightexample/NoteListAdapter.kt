package com.spookybrain.sqldelightexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NotesListAdapter(
    private val onDeletePressed: (id: Long) -> Unit,
    private val onNotePressed: (id: Long) -> Unit
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {
    private var list: Array<Note> = emptyArray()
    private var lastChanged: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.note_item, parent, false
            )
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cvNote: CardView = view.findViewById(R.id.cv_note)
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val tvContent: TextView = view.findViewById(R.id.tv_content)
        private val tvCreated: TextView = view.findViewById(R.id.tv_created)
        private val ibDelete: ImageButton = view.findViewById(R.id.ib_delete)

        fun bind(note: Note) {
            with(note) {
                tvTitle.text = title
                tvContent.text = content
                tvCreated.text = created.toString()
            }
            note.id ?: return
            ibDelete.setOnClickListener {
                setCurrentIndex(note.id)
                onDeletePressed(note.id)
            }
            cvNote.setOnClickListener {
                setCurrentIndex(note.id)
                onNotePressed(note.id)
            }
        }
    }

    fun setNotes(notes: Array<Note>) {
        val lastSize = list.size
        val action = when {
            list.size < notes.size -> ListAdapterAction.ADDED
            list.size == notes.size -> ListAdapterAction.MODIFIED
            else -> ListAdapterAction.REMOVED
        }
        list = notes
        when (action) {
            ListAdapterAction.ADDED -> notifyItemInserted(lastSize)
            ListAdapterAction.MODIFIED -> notifyItemChanged(lastChanged)
            else -> notifyItemRemoved(lastChanged)
        }
    }

    private fun setCurrentIndex(id: Long) {
        lastChanged = list.indexOfFirst { it.id == id }
    }

    private enum class ListAdapterAction {
        ADDED,
        REMOVED,
        MODIFIED;
    }
}
