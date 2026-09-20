package com.example.data.repository

import com.example.data.local.dao.AtlasDao
import com.example.data.local.model.AuditDossier
import com.example.data.local.model.PearlDiscipline
import com.example.data.local.model.PurgedHabit
import com.example.data.local.model.RaceSession
import kotlinx.coroutines.flow.Flow

class AtlasRepository(private val atlasDao: AtlasDao) {

    val allRaceSessions: Flow<List<RaceSession>> = atlasDao.getAllRaceSessions()
    val latestRaceSession: Flow<RaceSession?> = atlasDao.getLatestRaceSession()
    val allPurgedHabits: Flow<List<PurgedHabit>> = atlasDao.getAllPurgedHabits()
    val allDisciplines: Flow<List<PearlDiscipline>> = atlasDao.getAllDisciplines()
    val allAuditDossiers: Flow<List<AuditDossier>> = atlasDao.getAllAuditDossiers()

    suspend fun saveRaceSession(session: RaceSession): Long = atlasDao.insertRaceSession(session)
    suspend fun updateRaceSession(session: RaceSession) = atlasDao.updateRaceSession(session)

    suspend fun purgeHabit(habit: PurgedHabit): Long = atlasDao.insertPurgedHabit(habit)
    suspend fun deletePurgedHabit(id: Int) = atlasDao.deletePurgedHabit(id)

    suspend fun addDiscipline(discipline: PearlDiscipline): Long = atlasDao.insertDiscipline(discipline)
    suspend fun updateDiscipline(discipline: PearlDiscipline) = atlasDao.updateDiscipline(discipline)
    suspend fun deleteDiscipline(id: Int) = atlasDao.deleteDiscipline(id)

    suspend fun logAudit(dossier: AuditDossier): Long = atlasDao.insertAuditDossier(dossier)
    suspend fun deleteAudit(id: Int) = atlasDao.deleteAuditDossier(id)
}
