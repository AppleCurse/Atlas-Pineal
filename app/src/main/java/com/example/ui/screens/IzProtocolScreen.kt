package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.BrassGold
import com.example.ui.theme.PhosphorGreen
import com.example.ui.viewmodel.AtlasTab
import com.example.ui.viewmodel.AtlasViewModel

@Composable
fun IzProtocolScreen(
    viewModel: AtlasViewModel,
    modifier: Modifier = Modifier
) {
    val auditList by viewModel.auditDossiers.collectAsState()
    val status by viewModel.status.collectAsState()

    var targetName by remember { mutableStateOf("") }
    var auditDetail by remember { mutableStateOf("") }
    var selectedClassification by remember { mutableStateOf("ANALİZ") }
    var selectedLevel by remember { mutableStateOf("OPTIMAL") }

    val classifications = listOf("GÖZLEM", "ANALİZ", "SEZGİ", "ÇÖZÜM")
    val levels = listOf("OPTIMAL", "DIKKAT", "KRITIK")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF040608))
            .padding(16.dp)
    ) {
        // HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.selectTab(AtlasTab.KONSOL) },
                modifier = Modifier.testTag("iz_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri",
                    tint = PhosphorGreen
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(
                    text = "İZ // AĞ PROTOKOLÜ (GÖRME & OSINT)",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "“Bize bir isim ver, sana dosyasını verelim.”",
                    color = PhosphorGreen,
                    fontSize = 10.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // THREE PHASES HERO BADGE
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF07120D)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF134E39)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IzPhaseBadge(step = "FAZ 01", title = "AĞ KURMA", sub = "Açık Kaynak Tarama")
                Text("→", color = PhosphorGreen, fontSize = 12.sp)
                IzPhaseBadge(step = "FAZ 02", title = "BAĞLANTI", sub = "İlişki & Geçmiş")
                Text("→", color = PhosphorGreen, fontSize = 12.sp)
                IzPhaseBadge(step = "FAZ 03", title = "RAPOR", sub = "Şifreli Teslimat")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // DOSSIER INPUT BOX (New OSINT Investigation)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("audit_input_card"),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF060E0A)),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, Color(0xFF134E39)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Radar,
                        contentDescription = "Radar",
                        tint = PhosphorGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "YENİ AÇIK KAYNAK İSTİHBARAT DOSYASI (OSINT)",
                        color = PhosphorGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = targetName,
                    onValueChange = { targetName = it },
                    placeholder = {
                        Text(
                            "Hedef Şahıs / Firma / Due Diligence Konusu...",
                            color = Color(0xFF64748B),
                            fontSize = 11.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("audit_target_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = PhosphorGreen,
                        unfocusedBorderColor = Color(0xFF134E39),
                        cursorColor = PhosphorGreen
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = auditDetail,
                    onValueChange = { auditDetail = it },
                    placeholder = {
                        Text(
                            "Tespitler: Geçmiş şirket ortaklıkları, kamu kayıtları, sızıntı haritası...",
                            color = Color(0xFF64748B),
                            fontSize = 11.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                        .testTag("audit_detail_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = PhosphorGreen,
                        unfocusedBorderColor = Color(0xFF134E39),
                        cursorColor = PhosphorGreen
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // CLASSIFICATION PILLS
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    classifications.forEach { c ->
                        val isSelected = selectedClassification == c
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isSelected) Color(0xFF134E39) else Color(0xFF07120D))
                                .border(1.dp, if (isSelected) PhosphorGreen else Color(0xFF1E293B), RoundedCornerShape(4.dp))
                            .clickable { selectedClassification = c }
                            .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = c,
                                color = if (isSelected) Color.White else Color(0xFF94A3B8),
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // SUBMIT DOSSIER BUTTON
                Button(
                    onClick = {
                        if (targetName.isNotBlank()) {
                            viewModel.logAudit(
                                title = targetName,
                                classification = selectedClassification,
                                detail = auditDetail.ifBlank { "Açık kaynak bağlantıları ve ilişki haritası çıkarıldı." },
                                level = selectedLevel
                            )
                            targetName = ""
                            auditDetail = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF064E3B)),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .testTag("save_audit_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Save",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "DOSYAYI ŞİFRELE VE KAYDET",
                        color = Color.White,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // AUDIT LIST HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ŞİFRELİ İSTİHBARAT DOSYALARI (${auditList.size})",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "YALNIZCA TALEP EDENE",
                color = PhosphorGreen,
                fontSize = 8.5.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // DOSSIERS LIST
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(auditList) { item ->
                AuditDossierCard(
                    title = item.title,
                    classification = item.classification,
                    detail = item.auditDetail,
                    level = item.statusLevel,
                    onDelete = { viewModel.deleteAudit(item.id) }
                )
            }
        }
    }
}

@Composable
private fun IzPhaseBadge(step: String, title: String, sub: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = step, color = PhosphorGreen, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        Text(text = title, color = Color.White, fontSize = 9.5.sp, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Monospace)
        Text(text = sub, color = Color(0xFF94A3B8), fontSize = 7.5.sp)
    }
}

@Composable
private fun AuditDossierCard(
    title: String,
    classification: String,
    detail: String,
    level: String,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("audit_dossier_card"),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF090D14)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0xFF064E3B))
                            .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = classification,
                            color = PhosphorGreen,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "DURUM: $level",
                        color = if (level == "KRITIK") Color(0xFFEF4444) else if (level == "DIKKAT") Color(0xFFF59E0B) else PhosphorGreen,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = detail,
                    color = Color(0xFF94A3B8),
                    fontSize = 10.sp,
                    lineHeight = 14.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Sil",
                    tint = Color(0xFF64748B),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
