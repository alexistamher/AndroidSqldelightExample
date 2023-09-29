package com.spookybrain.sqldelightexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import javax.inject.Inject

class NoteDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NOTE_ID = "NOTE_ID"
    }

    @Inject
    lateinit var viewModel: NoteDetailViewModel

    private lateinit var ibGoBack: ImageButton
    private lateinit var ibSave: ImageButton
    private lateinit var tvTitle: TextInputEditText
    private lateinit var tvContent: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as NoteApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_note_detail)

        intent.extras?.getLong(EXTRA_NOTE_ID)
            .takeIf { it != 0L }?.let { id ->
                viewModel.getNoteById(id)
            }

        ibGoBack = findViewById(R.id.ib_go_back)
        ibSave = findViewById(R.id.ib_save)
        tvTitle = findViewById(R.id.tid_title)
        tvContent = findViewById(R.id.tid_content)

        setUiInteractions()
    }

    private fun setUiInteractions() {
        ibGoBack.setOnClickListener {
            finish()
        }
        ibSave.setOnClickListener {
            viewModel.insertNote()
            finish()
        }
        tvTitle.doAfterTextChanged {
            viewModel.setTitle(it.toString())
        }
        tvContent.doAfterTextChanged {
            viewModel.setContent(it.toString())
        }
        viewModel.validForm.observeForever {
            runOnUiThread {
                ibSave.isVisible = it
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tvTitle.setText(viewModel.title)
        tvContent.setText(viewModel.content)
    }
}