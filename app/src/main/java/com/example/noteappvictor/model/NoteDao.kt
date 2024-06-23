package com.example.noteappvictor.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Dao (Data Access Object) adalah kelas yang berisi fungsi-fungsi untuk mengakses data pada database.
@Dao
interface   NoteDao {
    // Fungsi untuk menyisipkan (insert) data ke dalam tabel
    @Insert
    fun insertData(noteData: NoteModel)

    // Fungsi untuk mengambil semua data dari tabel dan mengembalikannya dalam bentuk LiveData
    @Query("SELECT * FROM NoteModel")
    fun getAll(): LiveData<List<NoteModel>>

    // Fungsi untuk melakukan update terhadap data yang sudah ada dalam tabel
    @Update
    fun update(noteData: NoteModel)

    // Fungsi untuk menghapus data dari tabel
    @Delete
    fun delete(noteData: NoteModel)
}
