package com.example.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "race_sessions")
data class RaceSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val todayScore: Int,
    val yesterdayScore: Int,
    val willpowerGauge: Float, // 0 to 100
    val focusMinutes: Int,
    val disciplinesDone: Int,
    val temptationsResisted: Int,
    val victoryDeclared: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
