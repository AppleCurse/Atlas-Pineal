package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BrassKnob
import com.example.ui.components.NixieDisplay
import com.example.ui.components.PinealEyeView
import com.example.ui.components.SteampunkGauge
import com.example.ui.components.VacuumTube
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.AshRed
import com.example.ui.theme.BrassGold
import com.example.ui.theme.PhosphorGreen
import com.example.ui.viewmodel.AtlasTab
import com.example.ui.viewmodel.AtlasViewModel

@Composable
fun ConsoleScreen(
    viewModel: AtlasViewModel,
    modifier: Modifier = Modifier
) {
    val status by viewModel.status.collectAsState()
    val scrollState = rememberScrollState()

    val mins = status.focusSeconds / 60
    val secs = status.focusSeconds % 60
    val timeFormatted = String.format("%02d:%02d", mins, secs)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF040608))
            .verticalScroll(scrollState)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TOP BRASS PLATE HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2C2216),
                            Color(0xFF1B140D),
                            Color(0xFF0F0B07)
                        )
                    )
                )
                .border(1.5.dp, Color(0xFFC9B896), RoundedCornerShape(10.dp))
                .padding(vertical = 10.dp, horizontal = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "ATLAS EPİFİZ",
                        color = AntiqueGold,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )
                    Text(
                        text = "Sivil Dijital İstihbarat ve Analiz Teşkilatı",
                        color = Color(0xFF94A3B8),
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // EPİFİZ MOTTO BADGE
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFF070B10))
                        .border(1.dp, AntiqueGold.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "ÜÇÜNCÜ GÖZ BİLİR",
                        color = AntiqueGold,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // STATUS LED INDICATOR STRIP
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xFF090D14))
                .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(6.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusLedItem(label = "GÖZLEM", isActive = true, color = PhosphorGreen)
            StatusLedItem(label = "ANALİZ", isActive = true, color = PhosphorGreen)
            StatusLedItem(label = "SEZGİ", isActive = true, color = AntiqueGold)
            StatusLedItem(
                label = if (status.zeroLogProtection) "SIFIR-LOG: AKTİF" else "LOG KORUMASIZ",
                isActive = status.zeroLogProtection,
                color = if (status.zeroLogProtection) PhosphorGreen else AshRed
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // MAIN STEAMPUNK CONSOLE CLUSTER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF161C24),
                            Color(0xFF0D1117),
                            Color(0xFF07090C)
                        )
                    )
                )
                .border(2.dp, Color(0xFF38291F), RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Brass riveted subheader
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "● ÇEKİRDEK İSTİHBARAT KONSOLU ●",
                        color = BrassGold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "GECİKME: ${status.latencyMs}ms | AES-256",
                        color = PhosphorGreen,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // INSTRUMENTS + PINEAL EYE
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // LEFT INSTRUMENTS: Awareness & Core Integrity Gauges
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SteampunkGauge(
                            title = "SEZGİ",
                            value = status.awarenessLevel,
                            unit = "%",
                            size = 76.dp,
                            accentColor = PhosphorGreen
                        )

                        SteampunkGauge(
                            title = "BÜTÜNLÜK",
                            value = status.coreIntegrity,
                            unit = "%",
                            size = 76.dp,
                            accentColor = AntiqueGold
                        )
                    }

                    // CENTERPIECE: The Living Pineal Eye of Atlas Epifiz
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        // Security Switch Toggle
                        Button(
                            onClick = { viewModel.toggleZeroLogProtection() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (status.zeroLogProtection) Color(0xFF14532D) else Color(0xFF7F1D1D)
                            ),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .height(22.dp)
                                .testTag("zero_log_toggle_button"),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                        ) {
                            Icon(
                                imageVector = if (status.zeroLogProtection) Icons.Default.Lock else Icons.Default.Security,
                                contentDescription = "Security",
                                tint = Color.White,
                                modifier = Modifier.size(10.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = if (status.zeroLogProtection) "SIFIR LOG" else "LOG AÇIK",
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        PinealEyeView(
                            eyeSize = 136.dp,
                            intensity = if (status.isTimerActive) 0.85f else 0.5f,
                            isThinking = status.isTimerActive,
                            modifier = Modifier.testTag("console_pineal_eye")
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "EPİFİZ ÇEKİRDEĞİ",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            text = "HİÇ KAPANMAYAN GÖZ",
                            color = AntiqueGold,
                            fontSize = 8.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // RIGHT INSTRUMENTS: Twin Vacuum Tubes + Nixie Indicator
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            VacuumTube(label = "OSINT", isActive = true, width = 28.dp, height = 64.dp)
                            VacuumTube(label = "AKTİF", isActive = true, width = 28.dp, height = 64.dp)
                        }

                        NixieDisplay(
                            text = "OSNT",
                            isGreen = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // LOWER INSTRUMENT ROW: Time Flow Chronometer + Knobs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF090C11))
                        .border(1.dp, Color(0xFF2C2219), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Chronometer (Canlı Tarama Sayacı)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "CANLI TARAMA",
                            color = BrassGold,
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        NixieDisplay(
                            text = timeFormatted,
                            isGreen = status.isTimerActive
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(
                            onClick = { viewModel.toggleFocusTimer() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (status.isTimerActive) Color(0xFF7F1D1D) else Color(0xFF14532D)
                            ),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .height(24.dp)
                                .testTag("toggle_timer_button"),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                        ) {
                            Icon(
                                imageVector = if (status.isTimerActive) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Timer Toggle",
                                tint = Color.White,
                                modifier = Modifier.size(11.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (status.isTimerActive) "DURDUR" else "TARA",
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    // Rotary Dials: KÜL, SEDEF, İZ
                    BrassKnob(
                        label = "KÜL",
                        subLabel = "ŞİDDET",
                        value = status.kulIntensity,
                        size = 54.dp,
                        onValueChange = { viewModel.setKulIntensity(it) }
                    )

                    BrassKnob(
                        label = "SEDEF",
                        subLabel = "AYAR",
                        value = status.sedefTuning,
                        size = 54.dp,
                        onValueChange = { viewModel.setSedefTuning(it) }
                    )

                    BrassKnob(
                        label = "İZ",
                        subLabel = "ODAK",
                        value = status.izFocus,
                        size = 54.dp,
                        onValueChange = { viewModel.setIzFocus(it) }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // SERIAL BADGE
                Text(
                    text = "TEŞKİLAT MASASI // ATLAS-EPİFİZ-00713 | ANKARA",
                    color = Color(0xFF64748B),
                    fontSize = 8.sp,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // MANIFESTO BANNER: "DİJİTAL DÜNYA BİR KAYIT SİSTEMİDİR"
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("manifesto_cta_banner")
                .clickable { viewModel.selectTab(AtlasTab.MANIFESTO) },
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, AntiqueGold),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Shield",
                            tint = AntiqueGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "AMACIMIZ & BU SİSTEM KİM İÇİN?",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Text(
                        text = "“Dijital dünya hafıza değil, kayıt sistemidir. Peki bu kayıtları kim yönetiyor? Siz yöneteceksiniz.”",
                        color = Color(0xFF94A3B8),
                        fontSize = 10.5.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0xFF2A1F14))
                        .border(1.dp, AntiqueGold, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "DOSYA →",
                        color = AntiqueGold,
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // PROTOCOL HUBS: KÜL, SEDEF, İZ
        Text(
            text = "ÜÇ BÜYÜK PROTOKOL",
            color = BrassGold,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 2.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ProtocolCard(
                title = "KÜL",
                sub = "YOK ETME",
                quote = "Artık hiç doğmadın",
                icon = Icons.Default.DeleteSweep,
                accent = AshRed,
                modifier = Modifier.weight(1f),
                onClick = { viewModel.selectTab(AtlasTab.KUL) }
            )

            ProtocolCard(
                title = "SEDEF",
                sub = "VAR ETME",
                quote = "Kumdan inciye",
                icon = Icons.Default.AutoAwesome,
                accent = BrassGold,
                modifier = Modifier.weight(1f),
                onClick = { viewModel.selectTab(AtlasTab.SEDEF) }
            )

            ProtocolCard(
                title = "İZ",
                sub = "GÖRME / OSINT",
                quote = "Bize bir isim ver",
                icon = Icons.Default.Visibility,
                accent = PhosphorGreen,
                modifier = Modifier.weight(1f),
                onClick = { viewModel.selectTab(AtlasTab.IZ) }
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // EPİFİZ KÂHİNİ (HIGH THINKING AI) PROMO
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("oracle_promo_card")
                .clickable { viewModel.selectTab(AtlasTab.KAHIN) },
            colors = CardDefaults.cardColors(containerColor = Color(0xFF090E17)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B82F6)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E3A8A)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Thinking AI",
                        tint = Color(0xFF93C5FD),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "EPİFİZ YAPAY ZEKÂ ÇEKİRDEĞİ",
                            color = Color.White,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color(0xFF1D4ED8))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "HIGH THINKING",
                                color = Color.White,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Text(
                        text = "Vaka ve kriz analizi. Derin düşünme ile Teşkilat Operasyon Planı oluşturun.",
                        color = Color(0xFF94A3B8),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // LIVE SYSTEM TELEMETRY
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF080C12))
                .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(8.dp))
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TelemetryMetric(label = "İMHA EDİLEN VERİ", value = "${status.totalFrictionBurnt}", color = AshRed)
            TelemetryMetric(label = "İTİBAR KATMANI", value = "${status.activeDisciplinesDoneCount}", color = BrassGold)
            TelemetryMetric(label = "İSTİHBARAT DOSYASI", value = "${status.completedDossiersCount}", color = PhosphorGreen)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun StatusLedItem(label: String, isActive: Boolean, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .clip(CircleShape)
                .background(if (isActive) color else Color(0xFF475569))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            color = if (isActive) Color(0xFFE2E8F0) else Color(0xFF64748B),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
private fun ProtocolCard(
    title: String,
    sub: String,
    quote: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accent: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .testTag("protocol_${title.lowercase()}_card")
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF090D14)),
        border = androidx.compose.foundation.BorderStroke(1.dp, accent.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = accent,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = sub,
                color = accent,
                fontSize = 8.5.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "“$quote”",
                color = Color(0xFF94A3B8),
                fontSize = 8.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun TelemetryMetric(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = color,
            fontSize = 15.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = label,
            color = Color(0xFF94A3B8),
            fontSize = 8.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}
