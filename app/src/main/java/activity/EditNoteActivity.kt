package com.example.noteappvictor.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.noteappvictor.R
import com.example.noteappvictor.database.MyDatabase
import com.example.noteappvictor.databinding.ActivityEditNoteBinding
import com.example.noteappvictor.model.NoteModel
import java.util.Date
import java.util.concurrent.Executors

// Kelas EditNoteActivity mewarisi AppCompatActivity untuk mengedit catatan yang ada.
class EditNoteActivity : AppCompatActivity() {
    // Deklarasi variabel untuk binding tampilan dan database
    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var database: MyDatabase

    // Variabel untuk menyimpan ID, title, dan deskripsi catatan
    var idNote = 0
    var title = ""
    var description = ""

    // Metode yang dipanggil saat aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menginisialisasi binding dengan menggunakan DataBindingUtil
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_note)

        // Mendapatkan data dari intent
        idNote = intent.getIntExtra("id", 0)
        title = intent.getStringExtra("title") ?: ""
        description = intent.getStringExtra("description") ?: ""

        // Mengaitkan aktivitas ini dengan binding
        binding.activity = this

        // Mendapatkan instance database
        database = MyDatabase.getDatabase(this)
    }

    // Fungsi validasi input title
    private fun validateUsername(): Boolean {
        // Validasi untuk title
        return if (binding.editTitleNote.text.toString().trim().isEmpty()) {
            binding.editTitleNoteTextInputLayout.error = getString(R.string.title_empty_error)
            binding.editTitleNote.requestFocus()
            false
        } else {
            binding.editTitleNoteTextInputLayout.isErrorEnabled = false
            true
        }
    }

    // Fungsi validasi input deskripsi
    private fun validateDescription(): Boolean {
        // Validasi untuk deskripsi
        return if (binding.editNoteDescription.text.toString().trim().isEmpty()) {
            binding.editNoteDescriptionTextInputLayout.error = getString(R.string.description_empty_error)
            binding.editNoteDescription.requestFocus()
            false
        } else {
            binding.editNoteDescriptionTextInputLayout.isErrorEnabled = false
            true
        }
    }

    // Fungsi untuk mengedit data ketika tombol Edit diklik
    fun editData(view: View?) {
        // Mengambil nilai input dari pengguna
        title = binding.editTitleNote.text.toString().trim()
        description = binding.editNoteDescription.text.toString().trim()

        // Melakukan validasi username dan deskripsi secara terpisah
        val isUsernameValid = validateUsername()
        val isDescriptionValid = validateDescription()

        // Kondisi ketika inputan tidak kosong dan validasi berhasil
        if (isUsernameValid && isDescriptionValid) {
            // Deklarasi variabel noteData
            val noteData = NoteModel(title, description).apply {
                id = idNote
            }
            // Database tidak bisa diakses langsung di main thread utama, maka menggunakan thread terpisah
            Executors.newSingleThreadExecutor().execute {
                // Proses menyimpan data note yang telah diupdate ke database
                database.noteDao().update(noteData)
                // Ketika data berhasil terupdate, pindah ke halaman MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            // Tampilkan pesan toast bahwa data berhasil diupdate
            Toast.makeText(this, getString(R.string.data_successfully_updated), Toast.LENGTH_SHORT).show()
        }
    }
}
