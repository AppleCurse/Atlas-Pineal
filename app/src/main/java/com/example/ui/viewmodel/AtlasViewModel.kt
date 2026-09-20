package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AtlasDatabase
import com.example.data.local.model.AuditDossier
import com.example.data.local.model.PearlDiscipline
import com.example.data.local.model.PurgedHabit
import com.example.data.remote.GeminiThinkingService
import com.example.data.remote.ThinkingResult
import com.example.data.repository.AtlasRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AtlasTab {
    KONSOL,
    MANIFESTO,
    KUL,
    SEDEF,
    IZ,
    KAHIN
}

data class ConsoleStatus(
    val awarenessLevel: Float = 78f, // Sezgi Seviyesi %78
    val coreIntegrity: Float = 96f, // Sistem Bütünlüğü %96
    val latencyMs: Int = 12, // 12ms Gecikme
    val osintActive: Boolean = true, // OSINT Durumu
    val zeroLogProtection: Boolean = true, // Sıfır Log Koruması
    val focusSeconds: Long = 1420L, // Operasyonel Zaman Sayacı
    val isTimerActive: Boolean = true, // Canlı Tarama
    val kulIntensity: Float = 0.65f, // KÜL Şiddeti
    val sedefTuning: Float = 0.82f, // SEDEF Ayarı
    val izFocus: Float = 0.74f, // İZ Odağı
    val totalFrictionBurnt: Int = 184, // KÜL: İmha Edilen Veri Kırıntısı
    val activeDisciplinesDoneCount: Int = 12, // SEDEF: Aktif İtibar Katmanı
    val completedDossiersCount: Int = 8 // İZ: Çözümlenen İstihbarat Dosyası
)

class AtlasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AtlasRepository

    init {
        val database = AtlasDatabase.getDatabase(application)
        repository = AtlasRepository(database.atlasDao())
        seedInitialDataIfEmpty()
    }

    private val _currentTab = MutableStateFlow(AtlasTab.KONSOL)
    val currentTab: StateFlow<AtlasTab> = _currentTab.asStateFlow()

    private val _status = MutableStateFlow(ConsoleStatus())
    val status: StateFlow<ConsoleStatus> = _status.asStateFlow()

    // Room DB streams
    val purgedHabits: StateFlow<List<PurgedHabit>> = repository.allPurgedHabits
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val disciplines: StateFlow<List<PearlDiscipline>> = repository.allDisciplines
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val auditDossiers: StateFlow<List<AuditDossier>> = repository.allAuditDossiers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // High Thinking AI State
    private val _isThinking = MutableStateFlow(false)
    val isThinking: StateFlow<Boolean> = _isThinking.asStateFlow()

    private val _thinkingResult = MutableStateFlow<ThinkingResult?>(null)
    val thinkingResult: StateFlow<ThinkingResult?> = _thinkingResult.asStateFlow()

    private var timerJob: Job? = null

    init {
        startConsoleMonitoring()
    }

    private fun startConsoleMonitoring() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                if (_status.value.isTimerActive) {
                    val newSecs = _status.value.focusSeconds + 1
                    _status.value = _status.value.copy(focusSeconds = newSecs)
                }
            }
        }
    }

    fun selectTab(tab: AtlasTab) {
        _currentTab.value = tab
    }

    fun toggleFocusTimer() {
        val currentlyActive = _status.value.isTimerActive
        _status.value = _status.value.copy(isTimerActive = !currentlyActive)
    }

    fun toggleZeroLogProtection() {
        val current = _status.value.zeroLogProtection
        _status.value = _status.value.copy(zeroLogProtection = !current)
    }

    fun setKulIntensity(value: Float) {
        _status.value = _status.value.copy(kulIntensity = value)
    }

    fun setSedefTuning(value: Float) {
        _status.value = _status.value.copy(sedefTuning = value)
    }

    fun setIzFocus(value: Float) {
        _status.value = _status.value.copy(izFocus = value)
    }

    fun burnHabit(title: String, category: String, friction: Int) {
        viewModelScope.launch {
            repository.purgeHabit(
                PurgedHabit(
                    title = title,
                    category = category,
                    frictionBurned = friction,
                    status = "İMHA EDİLDİ"
                )
            )
            val currentTotal = _status.value.totalFrictionBurnt + 1
            val updatedAwareness = (_status.value.awarenessLevel + 2f).coerceAtMost(100f)
            _status.value = _status.value.copy(
                totalFrictionBurnt = currentTotal,
                awarenessLevel = updatedAwareness
            )
        }
    }

    fun deletePurgedHabit(id: Int) {
        viewModelScope.launch {
            repository.deletePurgedHabit(id)
        }
    }

    fun toggleDiscipline(discipline: PearlDiscipline) {
        viewModelScope.launch {
            val isNowCompleted = !discipline.isCompletedToday
            val newStreak = if (isNowCompleted) discipline.currentStreak + 1 else (discipline.currentStreak - 1).coerceAtLeast(0)
            val newLayers = (newStreak / 3) + 1

            val updated = discipline.copy(
                isCompletedToday = isNowCompleted,
                currentStreak = newStreak,
                nacreLayers = newLayers
            )
            repository.updateDiscipline(updated)

            val newCount = if (isNowCompleted) _status.value.activeDisciplinesDoneCount + 1 else (_status.value.activeDisciplinesDoneCount - 1).coerceAtLeast(0)
            val updatedAwareness = (_status.value.awarenessLevel + if (isNowCompleted) 3f else -3f).coerceIn(40f, 100f)
            _status.value = _status.value.copy(
                activeDisciplinesDoneCount = newCount,
                awarenessLevel = updatedAwareness
            )
        }
    }

    fun addDiscipline(title: String, category: String, targetDays: Int) {
        viewModelScope.launch {
            repository.addDiscipline(
                PearlDiscipline(
                    title = title,
                    category = category,
                    targetDays = targetDays,
                    nacreLayers = 1
                )
            )
        }
    }

    fun deleteDiscipline(id: Int) {
        viewModelScope.launch {
            repository.deleteDiscipline(id)
        }
    }

    fun logAudit(title: String, classification: String, detail: String, level: String) {
        viewModelScope.launch {
            repository.logAudit(
                AuditDossier(
                    title = title,
                    classification = classification,
                    auditDetail = detail,
                    statusLevel = level
                )
            )
            val updatedDossiersCount = _status.value.completedDossiersCount + 1
            _status.value = _status.value.copy(completedDossiersCount = updatedDossiersCount)
        }
    }

    fun deleteAudit(id: Int) {
        viewModelScope.launch {
            repository.deleteAudit(id)
        }
    }

    fun askEpifizHighThinking(prompt: String) {
        if (_isThinking.value) return
        _isThinking.value = true
        _thinkingResult.value = null

        viewModelScope.launch {
            val pace = "Sezgi: %${_status.value.awarenessLevel.toInt()} | Bütünlük: %${_status.value.coreIntegrity.toInt()}"
            val wp = _status.value.awarenessLevel.toInt()
            val burned = _status.value.totalFrictionBurnt
            val activeDisciplinesStr = disciplines.value.joinToString(", ") { "${it.title} (${it.category})" }

            val result = GeminiThinkingService.analyzeHighThinking(
                userPrompt = prompt,
                currentPace = pace,
                willpowerScore = wp,
                burnedFriction = burned,
                activeDisciplines = activeDisciplinesStr
            )

            _thinkingResult.value = result
            _isThinking.value = false

            // Automatically log an intelligence dossier of this inquiry
            logAudit(
                title = "Epifiz AI Vaka Analizi // $prompt".take(40),
                classification = "SEZGİ",
                detail = "İnceleme: $prompt\nKarar: ${result.solution.take(160)}...",
                level = "OPTIMAL"
            )
        }
    }

    private fun seedInitialDataIfEmpty() {
        viewModelScope.launch {
            // Seed KÜL records (Digital footprints, broker leaks, unwanted search tags)
            val initialBurnt = listOf(
                PurgedHabit(title = "Acxiom & Spokeo Veri Broker Kayıtları", category = "Data Broker", frictionBurned = 95, status = "İMHA EDİLDİ"),
                PurgedHabit(title = "Eski Forum Sızıntısı & E-posta Leak (2018)", category = "Forum Leak", frictionBurned = 80, status = "ZEHİRLENDİ"),
                PurgedHabit(title = "Wayback Machine Web Arşiv Ön Belleği", category = "Web Arşiv", frictionBurned = 60, status = "SİLİNDİ"),
                PurgedHabit(title = "Google Arama 4. Sayfadaki Asılsız Haber Etiketi", category = "Arama Motoru", frictionBurned = 85, status = "KOPARILDI")
            )
            initialBurnt.forEach { repository.purgeHabit(it) }

            // Seed SEDEF records (Reputation assets, executive profiles, SEO networks)
            val initialDisciplines = listOf(
                PearlDiscipline(title = "LinkedIn Yönetici İtibar Ağı & Stratejik Profil", category = "Kimlik İnşası", targetDays = 30, currentStreak = 12, isCompletedToday = true),
                PearlDiscipline(title = "Prestijli Sektörel Röportaj & Organik Basın Yayını", category = "Organik Basın", targetDays = 60, currentStreak = 8, isCompletedToday = true),
                PearlDiscipline(title = "Google SEO 1. Sayfa Pozitif İçerik İstilası", category = "SEO Ağı", targetDays = 90, currentStreak = 24, isCompletedToday = true),
                PearlDiscipline(title = "Doğrulanmış Biyografi & Kurumsal Referans Sistemi", category = "Otorite", targetDays = 45, currentStreak = 15, isCompletedToday = false)
            )
            initialDisciplines.forEach { repository.addDiscipline(it) }

            // Seed İZ records (Intelligence dossiers, due diligence, background checks)
            val initialAudits = listOf(
                AuditDossier(title = "Hedef Firma X // Ortaklık Öncesi Risk Haritası", classification = "ANALİZ", auditDetail = "Eski batık şirketler ve gizli bağlantılar tespit edildi. Şifreli dosya teslim edildi.", statusLevel = "DIKKAT"),
                AuditDossier(title = "Yönetici Adayı K.S. // Dijital Geçmiş & İK Raporu", classification = "GÖZLEM", auditDetail = "Açık kaynaklar ve kamu kayıtları tarandı. Beyanlar doğrulandı, temiz sicil mühürlendi.", statusLevel = "OPTIMAL"),
                AuditDossier(title = "Şahsi Dijital Ayak İzi & Sızıntı Denetimi", classification = "SEZGİ", auditDetail = "EXIF verileri ve unutulmuş 3 hesap izole edildi. KÜL protokolüne sevk edildi.", statusLevel = "OPTIMAL")
            )
            initialAudits.forEach { repository.logAudit(it) }
        }
    }
}
