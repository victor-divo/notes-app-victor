package com.example.noteappvictor.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class NoteModel(
    var title: String,
    var description: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
