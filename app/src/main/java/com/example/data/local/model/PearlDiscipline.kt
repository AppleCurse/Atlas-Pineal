package com.example.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pearl_disciplines")
data class PearlDiscipline(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String = "",
    val category: String,
    val currentStreak: Int = 0,
    val targetDays: Int = 30,
    val nacreLayers: Int = 1,
    val isCompletedToday: Boolean = false,
    val lastCompletedDate: String = ""
)
