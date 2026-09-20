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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.ui.components.PinealEyeView
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.BrassGold
import com.example.ui.theme.PhosphorGreen
import com.example.ui.viewmodel.AtlasTab
import com.example.ui.viewmodel.AtlasViewModel

@Composable
fun EpifizOracleScreen(
    viewModel: AtlasViewModel,
    modifier: Modifier = Modifier
) {
    val isThinking by viewModel.isThinking.collectAsState()
    val result by viewModel.thinkingResult.collectAsState()
    val status by viewModel.status.collectAsState()

    var userQuery by remember { mutableStateOf("") }
    var showThoughts by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    val presets = listOf(
        "Geçmişteki bir sızıntıyı KÜL protokolüyle silip olay örgüsünden nasıl koparırım?",
        "Google'da hiç görünmüyorum; SEDEF protokolüyle organik itibar katmanları nasıl örülür?",
        "Yeni bir ortaklık/yatırım öncesi hedef kişi hakkında İZ protokolüyle hangi açık kaynaklar taranmalı?",
        "Kriz anı: Hakkımdaki karalama haberi için KÜL mü yoksa SEDEF mi uygulanmalı?"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF040608))
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TOP BAR
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.selectTab(AtlasTab.KONSOL) },
                modifier = Modifier.testTag("oracle_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Geri",
                    tint = Color(0xFF60A5FA)
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Text(
                    text = "EPİFİZ KÂHİNİ // MERKEZİ ANALİZ ÇEKİRDEĞİ",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Gemini 3.1 Pro Preview • High Thinking Level",
                    color = Color(0xFF60A5FA),
                    fontSize = 9.5.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // CENTRAL ANIMATED PINEAL EYE (Thinking Mode)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF081226),
                            Color(0xFF040A17),
                            Color(0xFF020408)
                        )
                    )
                )
                .border(1.5.dp, Color(0xFF2563EB), RoundedCornerShape(12.dp))
                .padding(14.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                PinealEyeView(
                    eyeSize = 120.dp,
                    intensity = if (isThinking) 0.95f else 0.55f,
                    isThinking = isThinking
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (isThinking)
                        "⚡ EPİFİZ ÇEKİRDEĞİ DERİN ANALİZ VE DÜŞÜNME MODUNDA..."
                    else
                        "TEŞKİLAT SEZGİSİ AKTİF: KÜL • SEDEF • İZ",
                    color = if (isThinking) PhosphorGreen else Color(0xFF93C5FD),
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // INPUT BOX
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0B1120)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E3A8A)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "VAKA, KRİZ VEYA HEDEF ANALİZ TALEBİ",
                    color = Color(0xFF93C5FD),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = userQuery,
                    onValueChange = { userQuery = it },
                    placeholder = {
                        Text(
                            "Vakanızı, ifşa durumunu, hedef araştırmasını veya itibar hedefinizi girin...",
                            color = Color(0xFF64748B),
                            fontSize = 10.5.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("oracle_input_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF3B82F6),
                        unfocusedBorderColor = Color(0xFF1E293B),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(6.dp),
                    minLines = 2
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (userQuery.isNotBlank()) {
                            viewModel.askEpifizHighThinking(userQuery.trim())
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .testTag("oracle_submit_button"),
                    enabled = !isThinking && userQuery.isNotBlank()
                ) {
                    if (isThinking) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "YÜKSEK DÜŞÜNME SÜRECİ ÇALIŞIYOR...",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = "Analiz",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "TEŞKİLAT SEZGİSİNE DANIŞ (HIGH THINKING)",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // PRESET INQUIRIES
        Text(
            text = "ÖRNEK VAKA VE STRATEJİ SORULARI",
            color = Color(0xFF94A3B8),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
        )

        presets.forEach { preset ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF090E17))
                    .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(6.dp))
                    .clickable {
                        userQuery = preset
                        viewModel.askEpifizHighThinking(preset)
                    }
                    .padding(horizontal = 10.dp, vertical = 7.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Preset",
                        tint = Color(0xFF38BDF8),
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = preset,
                        color = Color(0xFFCBD5E1),
                        fontSize = 10.sp,
                        lineHeight = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // THINKING PROCESS & SOLUTION OUTPUT
        AnimatedVisibility(visible = result != null) {
            result?.let { res ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    // THOUGHTS ACCORDION
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showThoughts = !showThoughts },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF070B12)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1D4ED8)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = "Thinking Process",
                                        tint = PhosphorGreen,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "TEŞKİLATIN DÜŞÜNCE SÜRECİ (THINKING LOGS)",
                                        color = PhosphorGreen,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }

                                Text(
                                    text = if (showThoughts) "[GİZLE]" else "[GÖSTER]",
                                    color = Color(0xFF60A5FA),
                                    fontSize = 9.sp,
                                    fontFamily = FontFamily.Monospace
                                )
                            }

                            AnimatedVisibility(visible = showThoughts) {
                                Column(modifier = Modifier.padding(top = 8.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color(0xFF030508))
                                            .padding(10.dp)
                                    ) {
                                        Text(
                                            text = res.thoughts,
                                            color = Color(0xFF94A3B8),
                                            fontSize = 9.5.sp,
                                            lineHeight = 14.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // OPERATIONAL REPORT / SOLUTION
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF05111B)),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, AntiqueGold),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Çözüm",
                                    tint = AntiqueGold,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "STRATEJİK OPERASYON EYLEM PLANI",
                                    color = AntiqueGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    letterSpacing = 1.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = res.solution,
                                color = Color(0xFFE2E8F0),
                                fontSize = 11.5.sp,
                                lineHeight = 17.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF0A0F1A))
                                    .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(4.dp))
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "DOSYA RAPORU İSTİHBARAT ARŞİVİNE KAYDEDİLDİ (AES-256 SIFIR-LOG)",
                                    color = PhosphorGreen,
                                    fontSize = 8.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
