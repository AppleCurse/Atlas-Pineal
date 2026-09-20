package com.example.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purged_habits")
data class PurgedHabit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val category: String,
    val frictionBurned: Int,
    val burnedDate: Long = System.currentTimeMillis(),
    val status: String = "KÜL OLDU"
)
