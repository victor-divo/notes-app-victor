package com.example.noteappvictor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.noteappvictor.model.NoteDao
import com.example.noteappvictor.model.NoteModel

// Kelas MyDatabase menyediakan akses/koneksi ke database yang kita buat.
// Untuk membuat database, kita mendefinisikan sebuah kelas abstrak yang mengextend RoomDatabase
// dengan memberikan anotasi @Database, daftar entities yang akan dimasukkan ke dalam database, dan masing-masing DAO.
@Database(
    entities = [NoteModel::class],
    version = 3, // Versi database
    exportSchema = false // exportSchema digunakan untuk mengekspor skema database, misalnya ke file JSON
)
abstract class MyDatabase : RoomDatabase() {
    // Mendaftarkan interface NoteDao
    abstract fun noteDao(): NoteDao

    // companion object untuk mengembalikan instance database
    companion object {
        // INSTANCE Singleton untuk memastikan aplikasi hanya mengakses satu instance database
        @Volatile
        private var INSTANCE: MyDatabase? = null

        // Fungsi getDatabase untuk mengembalikan instance dari MyDatabase
        fun getDatabase(context: Context): MyDatabase {
            // Cek jika INSTANCE sudah ada
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                // Mengembalikan INSTANCE yang ada
                return tempInstance
            }

            // Jika INSTANCE masih null, buat instance baru dari database
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "noteData_database"
                )
                    .fallbackToDestructiveMigration() // Menghapus data jika ada perubahan versi database yang memerlukan migrasi
                    .build()
                INSTANCE = instance
                // Mengembalikan instance database yang baru dibuat
                return instance
            }
        }
    }
}
