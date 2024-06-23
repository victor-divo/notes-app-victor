package com.example.noteappvictor.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.noteappvictor.databinding.ActivityMainBinding
import com.example.noteappvictor.R
import com.example.noteappvictor.adapter.NoteAdapter
import com.example.noteappvictor.database.MyDatabase

class MainActivity : AppCompatActivity() {
    // Variabel binding dan database akan diinisialisasi nanti di dalam onCreate.
    // Variabel ini hanya dapat diakses di dalam kelas MainActivity saja.
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: MyDatabase

    // Metode yang dipanggil saat aktivitas dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inisialisasi binding dengan menggunakan DataBindingUtil
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        // Inisialisasi database
        database = MyDatabase.getDatabase(this)

        // Menampilkan data yang disimpan dalam bentuk list di adapter
        database.noteDao().getAll().observe(this) {
            // Mengatur adapter dengan data yang diperoleh dari database
            binding.adapter = NoteAdapter(it) {
                // Mengirim data yang akan diedit ke DetailActivity menggunakan intent
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("id", it.id)
                    putExtra("title", it.title)
                    putExtra("description", it.description)
                }
                startActivity(intent)
            }
        }
    }

    // Fungsi untuk mengaktifkan atau menonaktifkan mode gelap ketika switch diklik
    fun switchDarkMode(isChecked: Boolean) {
        if (binding.switchDarkMode.isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    // Fungsi untuk menambah data ketika tombol tambah diklik
    fun addData(view: View) {
        // Melakukan perpindahan ke halaman AddNoteActivity
        val intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }
}
