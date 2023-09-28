package com.spookybrain.sqldelightexample

import com.spookybrain.database.entity.NoteEntity

fun NoteEntity.asModel() = Note(id, title, content, created)