package com.example.noteappvictor.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.noteappvictor.R
import com.example.noteappvictor.database.MyDatabase
import com.example.noteappvictor.databinding.ActivityDetailBinding
import com.example.noteappvictor.model.NoteModel
import java.util.Date
import java.util.concurrent.Executors

class DetailActivity : AppCompatActivity() {
    // Deklarasi variabel untuk binding tampilan dan database
    private lateinit var binding: ActivityDetailBinding
    private lateinit var database: MyDatabase
    private var title = ""
    private var description = ""
    var idNote = 0

    // Metode yang dipanggil saat aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menginisialisasi binding dengan menggunakan layout inflater
        binding = ActivityDetailBinding.inflate(layoutInflater)
        // Menetapkan konten tampilan ke root dari binding
        setContentView(binding.root)

        // Mendapatkan data dari intent
        idNote = intent.getIntExtra("id", 0)
        title = intent.getStringExtra("title") ?: ""
        description = intent.getStringExtra("description") ?: ""

        // Menampilkan data pada TextView di layout
        binding.detailTitleNoteTextView.text = title
        binding.detailNoteDescriptionTextView.text = description
        // Mendapatkan instance database
        database = MyDatabase.getDatabase(this)

        // Jika data kosong, tampilkan pesan "No data found"
        if (title.isEmpty() && description.isEmpty()) {
            binding.detailTitleNoteTextView.text = getString(R.string.no_data_found)
            binding.detailNoteDescriptionTextView.visibility = View.GONE
        }
    }

    // Fungsi untuk mengedit data ketika tombol Edit diklik
    fun editData(view: View?) {
        val intent = Intent(this, EditNoteActivity::class.java).apply {
            putExtra("id", idNote)
            putExtra("title", title)
            putExtra("description", description)
        }
        startActivity(intent)
    }

    // Fungsi untuk menghapus data ketika tombol Delete diklik
    fun deleteData(view: View) {
        val noteData = NoteModel(title, description).apply {
            id = idNote
        }
        // database tidak bisa diakses langsung di main thread utama
        Executors.newSingleThreadExecutor().execute {
            // Menghapus data Note dari database
            database.noteDao().delete(noteData)
            // Setelah data berhasil dihapus, pindah ke halaman MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        // Tampilkan pesan toast bahwa data berhasil dihapus
        Toast.makeText(this, getString(R.string.data_successfully_deleted), Toast.LENGTH_SHORT).show()
    }
}
