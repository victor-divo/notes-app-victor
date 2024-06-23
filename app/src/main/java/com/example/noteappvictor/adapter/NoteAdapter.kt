package com.example.noteappvictor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappvictor.R
import com.example.noteappvictor.databinding.ItemNoteBinding
import com.example.noteappvictor.model.NoteModel

// Membuat Class NoteAdapter
class NoteAdapter(private val items: List<NoteModel>, private val onItemClick: (noteData: NoteModel) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // Fungsi ini dipanggil saat ViewHolder dibuat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        // Mengembalikan NoteViewHolder dengan menggunakan DataBindingUtil untuk menginflate layout item_note
        return NoteViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_note, parent, false))
    }

    // Fungsi ini dipanggil untuk menghubungkan data dengan ViewHolder pada posisi tertentu
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        // Memanggil fungsi bind untuk menghubungkan data dengan view
        holder.bind(items[position])
        // Membuat fungsi onClick untuk item dalam RecyclerView
        holder.itemView.setOnClickListener {
            // Menjalankan fungsi onItemClick dengan data item yang sesuai
            onItemClick(items[position])
        }
    }

    // Fungsi ini untuk menentukan jumlah item dalam list yang akan ditampilkan
    override fun getItemCount(): Int {
        // Mengembalikan jumlah item dalam list
        return items.size
    }

    // Class NoteViewHolder yang menghubungkan item_note layout dengan data NoteModel
    class NoteViewHolder(var itemNoteBinding: ItemNoteBinding) : RecyclerView.ViewHolder(itemNoteBinding.root) {
        // Fungsi bind untuk menghubungkan data NoteModel dengan item_note layout
        fun bind(noteData: NoteModel?) {
            // Menghubungkan objek noteData dengan itemNoteBinding
            itemNoteBinding.noteData = noteData
            // Menjalankan semua binding yang tertunda segera
            itemNoteBinding.executePendingBindings()
        }
    }
}
