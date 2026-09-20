package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VacuumTube(
    label: String,
    isActive: Boolean = true,
    modifier: Modifier = Modifier,
    width: Dp = 32.dp,
    height: Dp = 76.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "tube_plasma")
    val plasmaGlow by infiniteTransition.animateFloat(
        initialValue = 0.65f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "plasma"
    )

    Column(
        modifier = modifier.testTag("vacuum_tube_$label"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(width)
                .height(height),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val w = size.width
                val h = size.height

                // Brass Base Cap
                drawRoundRect(
                    color = Color(0xFFC9B896),
                    topLeft = Offset(0f, h - 12f),
                    size = Size(w, 12f),
                    cornerRadius = CornerRadius(3f, 3f)
                )

                // Brass Top Cap
                drawRoundRect(
                    color = Color(0xFFC9B896),
                    topLeft = Offset(0f, 0f),
                    size = Size(w, 8f),
                    cornerRadius = CornerRadius(4f, 4f)
                )

                // Glass Cylinder
                val glassTop = 8f
                val glassHeight = h - 20f
                drawRoundRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x2210B981),
                            Color(0x33064E3B),
                            Color(0x2210B981)
                        )
                    ),
                    topLeft = Offset(2f, glassTop),
                    size = Size(w - 4f, glassHeight),
                    cornerRadius = CornerRadius(6f, 6f)
                )

                // Glass Border / Reflection
                drawRoundRect(
                    color = Color(0x66C9B896),
                    topLeft = Offset(2f, glassTop),
                    size = Size(w - 4f, glassHeight),
                    cornerRadius = CornerRadius(6f, 6f),
                    style = Stroke(width = 1f)
                )

                // Glowing Green Phosphor Filament / Plasma
                if (isActive) {
                    val filamentColor = Color(0xFF22C55E).copy(alpha = plasmaGlow)
                    val glowColor = Color(0xFF4ADE80).copy(alpha = 0.5f * plasmaGlow)

                    // Plasma Aura
                    drawRoundRect(
                        color = glowColor,
                        topLeft = Offset(w * 0.32f, glassTop + 4f),
                        size = Size(w * 0.36f, glassHeight - 8f),
                        cornerRadius = CornerRadius(4f, 4f)
                    )

                    // Core Filament Beam
                    drawLine(
                        color = filamentColor,
                        start = Offset(w * 0.5f, glassTop + 6f),
                        end = Offset(w * 0.5f, glassTop + glassHeight - 6f),
                        strokeWidth = 3f,
                        cap = StrokeCap.Round
                    )

                    drawLine(
                        color = Color.White.copy(alpha = 0.85f * plasmaGlow),
                        start = Offset(w * 0.5f, glassTop + 8f),
                        end = Offset(w * 0.5f, glassTop + glassHeight - 8f),
                        strokeWidth = 1.2f,
                        cap = StrokeCap.Round
                    )
                }

                // Glass highlight streak
                drawLine(
                    color = Color.White.copy(alpha = 0.35f),
                    start = Offset(5f, glassTop + 4f),
                    end = Offset(5f, glassTop + glassHeight - 6f),
                    strokeWidth = 1.2f
                )
            }
        }

        Text(
            text = label,
            color = if (isActive) Color(0xFF22C55E) else Color(0xFF64748B),
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
