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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Diamond
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.model.PearlDiscipline
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.BrassGold
import com.example.ui.theme.PhosphorGreen
import com.example.ui.viewmodel.AtlasTab
import com.example.ui.viewmodel.AtlasViewModel

@Composable
fun SedefProtocolScreen(
    viewModel: AtlasViewModel,
    modifier: Modifier = Modifier
) {
    val disciplines by viewModel.disciplines.collectAsState()
    val status by viewModel.status.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    var newCategory by remember { mutableStateOf("Kimlik İnşası") }

    val categories = listOf("Kimlik İnşası", "Organik Basın", "SEO Ağı", "Otorite", "Kurumsal")

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
                modifier = Modifier.testTag("sedef_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri",
                    tint = BrassGold
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(
                    text = "SEDEF // DÖNÜŞÜM PROTOKOLÜ",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "“Kumdan inciye. Yalan değil, dönüşüm.”",
                    color = AntiqueGold,
                    fontSize = 10.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // THREE PHASES BANNER
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF14100B)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF38291F)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SedefPhaseBadge(step = "FAZ 01", title = "HAMMADDE", sub = "Mevcut Ayak İzi")
                Text("→", color = AntiqueGold, fontSize = 12.sp)
                SedefPhaseBadge(step = "FAZ 02", title = "KATMANLAR", sub = "Organik İnşa")
                Text("→", color = AntiqueGold, fontSize = 12.sp)
                SedefPhaseBadge(step = "FAZ 03", title = "PARLATMA", sub = "İnci Görünümü")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // HERO BOX: ISTIRIDYE METAFORU VE INCİ KATMANLARI
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF241B11),
                            Color(0xFF15100A),
                            Color(0xFF090704)
                        )
                    )
                )
                .border(1.5.dp, Color(0xFFC9B896), RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFFF7ED),
                                    Color(0xFFFDE68A),
                                    Color(0xFFB45309)
                                )
                            )
                        )
                        .border(1.5.dp, AntiqueGold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Diamond,
                        contentDescription = "Pearl",
                        tint = Color(0xFF451A03),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "İSTİRİDYE METAFORU",
                        color = AntiqueGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "İstiridye rahatsız olduğu için inci üretir. SEDEF o kötü haberi, o boş Google sonucunu hammadde alır; etrafını katman katman gerçek ve saygın hikayeyle sarar.",
                        color = Color(0xFFE2E8F0),
                        fontSize = 10.sp,
                        lineHeight = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // SECTION: REPUTATION ASSETS (ITIBAR KATMANLARI)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DÖNÜŞÜM KATMANLARI (${disciplines.size})",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Button(
                onClick = { showAddDialog = !showAddDialog },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E2116)),
                shape = RoundedCornerShape(6.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AntiqueGold),
                modifier = Modifier
                    .height(28.dp)
                    .testTag("add_sedef_button"),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Ekle",
                    tint = AntiqueGold,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "KATMAN İNŞA ET",
                    color = AntiqueGold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        // ADD LAYER DIALOG INLINE
        if (showAddDialog) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF130E09)),
                border = androidx.compose.foundation.BorderStroke(1.dp, AntiqueGold),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "YENİ İTİBAR & DÖNÜŞÜM KATMANI",
                        color = AntiqueGold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        placeholder = { Text("Örn: Google 1. Sayfa SEO Ağı veya Basın Röportajı...", color = Color(0xFF64748B), fontSize = 11.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("new_sedef_title_field"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = AntiqueGold,
                            unfocusedBorderColor = Color(0xFF38291F),
                            cursorColor = AntiqueGold
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSelected = newCategory == cat
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isSelected) Color(0xFF38291F) else Color(0xFF0F0B07))
                                    .border(1.dp, if (isSelected) AntiqueGold else Color(0xFF1E140C), RoundedCornerShape(4.dp))
                                    .clickable { newCategory = cat }
                                    .padding(vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cat,
                                    color = if (isSelected) AntiqueGold else Color(0xFF94A3B8),
                                    fontSize = 7.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (newTitle.isNotBlank()) {
                                viewModel.addDiscipline(
                                    title = newTitle,
                                    category = newCategory,
                                    targetDays = 60
                                )
                                newTitle = ""
                                showAddDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E2116)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AntiqueGold),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("KATMANI MÜHÜRLE", color = AntiqueGold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // LIST OF DISCIPLINES / REPUTATION LAYERS
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(disciplines) { item ->
                SedefLayerCard(
                    item = item,
                    onToggle = { viewModel.toggleDiscipline(item) },
                    onDelete = { viewModel.deleteDiscipline(item.id) }
                )
            }
        }
    }
}

@Composable
private fun SedefPhaseBadge(step: String, title: String, sub: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = step, color = AntiqueGold, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        Text(text = title, color = Color.White, fontSize = 9.5.sp, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Monospace)
        Text(text = sub, color = Color(0xFF94A3B8), fontSize = 7.5.sp)
    }
}

@Composable
private fun SedefLayerCard(
    item: PearlDiscipline,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("sedef_layer_card"),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF090D14)),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (item.isCompletedToday) AntiqueGold else Color(0xFF1E293B)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // CHECK / ACTIVE BOX
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (item.isCompletedToday) AntiqueGold else Color(0xFF1E293B))
                    .clickable { onToggle() },
                contentAlignment = Alignment.Center
            ) {
                if (item.isCompletedToday) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Active",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = item.category.uppercase(),
                        color = AntiqueGold,
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "• ${item.nacreLayers} Katman İnci İnşası",
                        color = Color(0xFF94A3B8),
                        fontSize = 8.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = item.title,
                    color = Color.White,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = if (item.isCompletedToday) "Aktif Arama Sonucu Koruması: Güçlü" else "Katman Genişletme Bekleniyor",
                    color = if (item.isCompletedToday) PhosphorGreen else Color(0xFF64748B),
                    fontSize = 8.5.sp,
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
