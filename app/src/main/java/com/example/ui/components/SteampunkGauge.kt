package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SteampunkGauge(
    title: String,
    value: Float, // 0f to 100f
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    unit: String = "%",
    accentColor: Color = Color(0xFFE5A93C)
) {
    val animatedValue by animateFloatAsState(
        targetValue = value.coerceIn(0f, 100f),
        animationSpec = tween(durationMillis = 750),
        label = "gauge_needle"
    )

    Column(
        modifier = modifier.testTag("steampunk_gauge_$title"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(size)) {
                val center = Offset(size.toPx() / 2f, size.toPx() / 2f)
                val radius = size.toPx() / 2f

                // Outer brass bezel
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF4A3828),
                            Color(0xFF2C1F16),
                            Color(0xFF1E140E)
                        ),
                        center = center,
                        radius = radius
                    ),
                    radius = radius,
                    center = center
                )

                drawCircle(
                    color = Color(0xFFC9B896),
                    radius = radius * 0.95f,
                    center = center,
                    style = Stroke(width = 2.5f)
                )

                // Parchment Dial Face
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFF3EAD8),
                            Color(0xFFDFD0B8),
                            Color(0xFFBAA385)
                        ),
                        center = center,
                        radius = radius * 0.88f
                    ),
                    radius = radius * 0.88f,
                    center = center
                )

                // Dial tick marks (from -140 deg to +140 deg = 280 deg arc)
                val startAngle = 140.0
                val totalSweep = 260.0

                for (i in 0..10) {
                    val angleDeg = startAngle + (i / 10.0) * totalSweep
                    val angleRad = angleDeg * (Math.PI / 180.0)
                    val rOuter = radius * 0.82f
                    val rInner = if (i % 5 == 0) radius * 0.65f else radius * 0.72f

                    val p1 = Offset(
                        center.x + (rOuter * cos(angleRad)).toFloat(),
                        center.y + (rOuter * sin(angleRad)).toFloat()
                    )
                    val p2 = Offset(
                        center.x + (rInner * cos(angleRad)).toFloat(),
                        center.y + (rInner * sin(angleRad)).toFloat()
                    )

                    drawLine(
                        color = if (i > 7) Color(0xFF991B1B) else Color(0xFF26190F),
                        start = p1,
                        end = p2,
                        strokeWidth = if (i % 5 == 0) 2f else 1.2f,
                        cap = StrokeCap.Round
                    )
                }

                // Needle calculation (-140 to +120 deg)
                val needleFraction = animatedValue / 100f
                val needleAngleDeg = startAngle + (needleFraction * totalSweep)
                val needleAngleRad = needleAngleDeg * (Math.PI / 180.0)
                val needleLength = radius * 0.76f

                val needleTip = Offset(
                    center.x + (needleLength * cos(needleAngleRad)).toFloat(),
                    center.y + (needleLength * sin(needleAngleRad)).toFloat()
                )

                val baseAngle1 = needleAngleRad + Math.PI / 2
                val baseAngle2 = needleAngleRad - Math.PI / 2
                val baseWidth = 4f
                val needleBase1 = Offset(
                    center.x + (baseWidth * cos(baseAngle1)).toFloat(),
                    center.y + (baseWidth * sin(baseAngle1)).toFloat()
                )
                val needleBase2 = Offset(
                    center.x + (baseWidth * cos(baseAngle2)).toFloat(),
                    center.y + (baseWidth * sin(baseAngle2)).toFloat()
                )

                val needlePath = Path().apply {
                    moveTo(needleBase1.x, needleBase1.y)
                    lineTo(needleTip.x, needleTip.y)
                    lineTo(needleBase2.x, needleBase2.y)
                    close()
                }

                // Draw needle (red indicator with bronze hub)
                drawPath(
                    path = needlePath,
                    color = Color(0xFFB91C1C)
                )

                // Central Brass Pivot Cap
                drawCircle(
                    color = Color(0xFF1E140E),
                    radius = radius * 0.16f,
                    center = center
                )
                drawCircle(
                    color = Color(0xFFE5A93C),
                    radius = radius * 0.12f,
                    center = center
                )
                drawCircle(
                    color = Color(0xFFF3E5AB),
                    radius = radius * 0.05f,
                    center = Offset(center.x - 2f, center.y - 2f)
                )
            }
        }

        Text(
            text = title,
            color = Color(0xFFC9B896),
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(top = 2.dp)
        )
        Text(
            text = "${animatedValue.toInt()}$unit",
            color = Color(0xFFF8FAFC),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )
    }
}
