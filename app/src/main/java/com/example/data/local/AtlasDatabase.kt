package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.AtlasDao
import com.example.data.local.model.AuditDossier
import com.example.data.local.model.PearlDiscipline
import com.example.data.local.model.PurgedHabit
import com.example.data.local.model.RaceSession

@Database(
    entities = [
        RaceSession::class,
        PurgedHabit::class,
        PearlDiscipline::class,
        AuditDossier::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AtlasDatabase : RoomDatabase() {
    abstract fun atlasDao(): AtlasDao

    companion object {
        @Volatile
        private var INSTANCE: AtlasDatabase? = null

        fun getDatabase(context: Context): AtlasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AtlasDatabase::class.java,
                    "atlas_pineal_db"
                ).fallbackToDestructiveMigration(dropAllTables = true).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
