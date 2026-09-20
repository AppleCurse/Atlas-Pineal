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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.ui.theme.AshRed
import com.example.ui.theme.BrassGold
import com.example.ui.viewmodel.AtlasTab
import com.example.ui.viewmodel.AtlasViewModel

@Composable
fun KulProtocolScreen(
    viewModel: AtlasViewModel,
    modifier: Modifier = Modifier
) {
    val purgedList by viewModel.purgedHabits.collectAsState()
    val status by viewModel.status.collectAsState()

    var targetText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Arama Motoru") }
    var poisonLevel by remember { mutableFloatStateOf(85f) }
    var lastPurgedNotice by remember { mutableStateOf<String?>(null) }

    val categories = listOf("Arama Motoru", "Data Broker", "Web Arşivi", "Forum Sızıntısı", "EXIF / Görsel")

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
                modifier = Modifier.testTag("kul_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri",
                    tint = AshRed
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(
                    text = "KÜL // KARA DELİK PROTOKOLÜ",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "“İzini değil, varlığını siliyoruz. Artık hiç doğmadın.”",
                    color = AshRed,
                    fontSize = 10.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // THREE PHASES HERO BADGE
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F0707)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF450A0A)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PhaseBadge(step = "FAZ 01", title = "HARİTA", sub = "OSINT Tarama")
                Text("→", color = Color(0xFF7F1D1D), fontSize = 12.sp)
                PhaseBadge(step = "FAZ 02", title = "SİLME", sub = "Yasal Kaldırma")
                Text("→", color = Color(0xFF7F1D1D), fontSize = 12.sp)
                PhaseBadge(step = "FAZ 03", title = "ZEHİRLEME", sub = "Kişi Bulunamadı")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // PURGE BOX (Target Input & Poisoning)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("burn_card"),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0C0707)),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, Color(0xFF7F1D1D)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "İMHA EDİLECEK DİJİTAL İZ / URL / VERİ KAYDI",
                    color = AshRed,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = targetText,
                    onValueChange = { targetText = it },
                    placeholder = {
                        Text(
                            "Örn: Google aramasında çıkan eski haber linki, Spokeo kişi kaydı...",
                            color = Color(0xFF64748B),
                            fontSize = 11.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("burn_input_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = AshRed,
                        unfocusedBorderColor = Color(0xFF450A0A),
                        cursorColor = AshRed
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // CATEGORY SELECTION
                Text(
                    text = "VERİ KAYNAĞI TÜRÜ:",
                    color = Color(0xFF94A3B8),
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    categories.take(3).forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isSelected) Color(0xFF450A0A) else Color(0xFF180A0A))
                                .border(1.dp, if (isSelected) AshRed else Color(0xFF2C1010), RoundedCornerShape(4.dp))
                                .clickable { selectedCategory = cat }
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) Color.White else Color(0xFF94A3B8),
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    categories.drop(3).forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isSelected) Color(0xFF450A0A) else Color(0xFF180A0A))
                                .border(1.dp, if (isSelected) AshRed else Color(0xFF2C1010), RoundedCornerShape(4.dp))
                                .clickable { selectedCategory = cat }
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) Color.White else Color(0xFF94A3B8),
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // POISONING INTENSITY SLIDER
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DATA POISONING (ZEHİRLEME ŞİDDETİ):",
                        color = Color(0xFF94A3B8),
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "%${poisonLevel.toInt()}",
                        color = AshRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Slider(
                    value = poisonLevel,
                    onValueChange = { poisonLevel = it },
                    valueRange = 20f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = AshRed,
                        activeTrackColor = AshRed,
                        inactiveTrackColor = Color(0xFF270D0D)
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                // INCINERATE & POISON BUTTON
                Button(
                    onClick = {
                        if (targetText.isNotBlank()) {
                            viewModel.burnHabit(
                                title = targetText,
                                category = selectedCategory,
                                friction = poisonLevel.toInt()
                            )
                            lastPurgedNotice = "“$targetText” olay örgüsünden koparıldı. Artık hiç doğmadın."
                            targetText = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .testTag("burn_submit_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F1D1D)),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = "Purge",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "KARA DELİĞE GÖNDER VE ZEHİRLE",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                AnimatedVisibility(visible = lastPurgedNotice != null) {
                    lastPurgedNotice?.let { msg ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF1C0A0A))
                                .border(1.dp, AshRed, RoundedCornerShape(4.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "⚠️ $msg",
                                color = Color(0xFFFCA5A5),
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // PURGED LIST HEADER
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "İMHA EDİLEN VE SİLİNEN VERİLER (${purgedList.size})",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = "KİŞİ BULUNAMADI",
                color = AshRed,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // PURGED RECORDS LIST
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(purgedList) { item ->
                PurgedItemCard(
                    title = item.title,
                    category = item.category,
                    friction = item.frictionBurned,
                    onDelete = { viewModel.deletePurgedHabit(item.id) }
                )
            }
        }
    }
}

@Composable
private fun PhaseBadge(step: String, title: String, sub: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = step, color = AshRed, fontSize = 8.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
        Text(text = title, color = Color.White, fontSize = 9.5.sp, fontWeight = FontWeight.ExtraBold, fontFamily = FontFamily.Monospace)
        Text(text = sub, color = Color(0xFF94A3B8), fontSize = 7.5.sp)
    }
}

@Composable
private fun PurgedItemCard(
    title: String,
    category: String,
    friction: Int,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("purged_item_card"),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF090B0E)),
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
                            .background(Color(0xFF450A0A))
                            .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = category.uppercase(),
                            color = Color(0xFFF87171),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "İMHA EDİLDİ",
                        color = AshRed,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = title,
                    color = Color(0xFFE2E8F0),
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Zehirleme Şiddeti: %$friction | Kayıt arama motorlarından ve ön bellekten koparıldı",
                    color = Color(0xFF64748B),
                    fontSize = 9.sp,
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
