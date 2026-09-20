package com.example.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_dossiers")
data class AuditDossier(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val classification: String, // GÖZLEM, ANALİZ, SEZGİ, ÇÖZÜM
    val auditDetail: String,
    val statusLevel: String, // OPTIMAL, DIKKAT, KRITIK
    val timestamp: Long = System.currentTimeMillis()
)
