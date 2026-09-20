package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.model.AuditDossier
import com.example.data.local.model.PearlDiscipline
import com.example.data.local.model.PurgedHabit
import com.example.data.local.model.RaceSession
import kotlinx.coroutines.flow.Flow

@Dao
interface AtlasDao {

    // Race sessions
    @Query("SELECT * FROM race_sessions ORDER BY timestamp DESC")
    fun getAllRaceSessions(): Flow<List<RaceSession>>

    @Query("SELECT * FROM race_sessions ORDER BY timestamp DESC LIMIT 1")
    fun getLatestRaceSession(): Flow<RaceSession?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaceSession(session: RaceSession): Long

    @Update
    suspend fun updateRaceSession(session: RaceSession)

    // Purged Habits (KÜL)
    @Query("SELECT * FROM purged_habits ORDER BY burnedDate DESC")
    fun getAllPurgedHabits(): Flow<List<PurgedHabit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurgedHabit(habit: PurgedHabit): Long

    @Query("DELETE FROM purged_habits WHERE id = :id")
    suspend fun deletePurgedHabit(id: Int)

    // Pearl Disciplines (SEDEF)
    @Query("SELECT * FROM pearl_disciplines ORDER BY id ASC")
    fun getAllDisciplines(): Flow<List<PearlDiscipline>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiscipline(discipline: PearlDiscipline): Long

    @Update
    suspend fun updateDiscipline(discipline: PearlDiscipline)

    @Query("DELETE FROM pearl_disciplines WHERE id = :id")
    suspend fun deleteDiscipline(id: Int)

    // Audit Dossiers (İZ)
    @Query("SELECT * FROM audit_dossiers ORDER BY timestamp DESC")
    fun getAllAuditDossiers(): Flow<List<AuditDossier>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditDossier(dossier: AuditDossier): Long

    @Query("DELETE FROM audit_dossiers WHERE id = :id")
    suspend fun deleteAuditDossier(id: Int)
}
