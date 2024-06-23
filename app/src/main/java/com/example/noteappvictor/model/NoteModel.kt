package com.example.noteappvictor.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// Entity merupakan representasi dari sebuah tabel dalam database. Room akan menciptakan
// sebuah tabel untuk setiap kelas yang memiliki anotasi @Entity. Property dalam sebuah kelas
// akan mewakili kolom-kolom dalam tabel.
@Entity
// Data class NoteModel merepresentasikan sebuah entitas dalam database.
data class NoteModel(
    var title: String,
    var description: String,
    //TODO 54 Menambahkan primary key yang di-generate secara otomatis.
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
