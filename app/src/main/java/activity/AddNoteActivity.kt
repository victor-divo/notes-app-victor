package com.example.noteappvictor.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.noteappvictor.R
import com.example.noteappvictor.database.MyDatabase
import com.example.noteappvictor.databinding.ActivityAddNoteBinding
import com.example.noteappvictor.model.NoteModel
import java.util.Date
import java.util.concurrent.Executors

// Kelas AddNoteActivity mewarisi AppCompatActivity untuk membuat sebuah activity yang memungkinkan pengguna menambahkan catatan.
class AddNoteActivity : AppCompatActivity() {
    // Deklarasi variabel untuk binding dan database.
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var database: MyDatabase

    // Variabel untuk menyimpan title dan deskripsi catatan.
    var title = ""
    var description = ""

    // Fungsi onCreate dipanggil saat activity dibuat.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengatur layout menggunakan DataBindingUtil.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_note)
        binding.activity = this
        // Menginisialisasi database.
        database = MyDatabase.getDatabase(this)
    }

    // Fungsi untuk validasi input username.
    private fun validateUsername(): Boolean {
        // Cek jika username kosong.
        return if (binding.addTitleNote.text.toString().trim().isEmpty()) {
            binding.addTitleNoteTextInputLayout.error = getString(R.string.title_empty_error)
            binding.addTitleNote.requestFocus()
            false
        } else {
            binding.addTitleNoteTextInputLayout.isErrorEnabled = false
            true
        }
    }

    // Fungsi untuk validasi input deskripsi.
    private fun validateDescription(): Boolean {
        // Cek jika deskripsi kosong.
        return if (binding.addNoteDescription.text.toString().trim().isEmpty()) {
            binding.addNoteDescriptionTextInputLayout.error = getString(R.string.description_empty_error)
            binding.addNoteDescription.requestFocus()
            false
        } else {
            binding.addNoteDescriptionTextInputLayout.isErrorEnabled = false
            true
        }
    }

    // Fungsi untuk menyimpan data catatan ke database.
    fun saveData(view: View?) {
        // Mengambil nilai input dari pengguna.
        title = binding.addTitleNote.text.toString().trim()
        description = binding.addNoteDescription.text.toString().trim()

        // Melakukan validasi title dan deskripsi secara terpisah.
        val isTitleValid = validateUsername()
        val isDescriptionValid = validateDescription()

        // Jika kedua validasi lolos, simpan data ke database.
        if (isTitleValid && isDescriptionValid) {
//            Log.d("tes data", "$title $description")
            if (title.isNotEmpty() && description.isNotEmpty()) {
                val noteData = NoteModel(title, description)
                // Menyimpan data ke database di thread terpisah.
                Executors.newSingleThreadExecutor().execute {
                    database.noteDao().insertData(noteData)
                }
                // Tampilkan pesan toast bahwa data berhasil disimpan
                Toast.makeText(this, R.string.data_successfully_save, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}
