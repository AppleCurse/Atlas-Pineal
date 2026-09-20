package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun NixieDisplay(
    text: String,
    modifier: Modifier = Modifier,
    isGreen: Boolean = false
) {
    val charBg = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF030508),
            Color(0xFF131A26),
            Color(0xFF030508)
        )
    )

    val textColor = if (isGreen) Color(0xFF22C55E) else Color(0xFFF59E0B)
    val textShadow = if (isGreen) Color(0xFF15803D) else Color(0xFFB45309)

    Box(
        modifier = modifier
            .testTag("nixie_display")
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF07090C))
            .border(1.5.dp, Color(0xFF4A3E2C), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            text.forEach { char ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(charBg)
                        .border(0.5.dp, Color(0xFF1E293B), RoundedCornerShape(3.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char.toString(),
                        color = textColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }
    }
}
