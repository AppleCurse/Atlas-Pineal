package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BrassKnob(
    label: String,
    subLabel: String,
    value: Float, // 0f to 1f
    modifier: Modifier = Modifier,
    size: Dp = 68.dp,
    onValueChange: (Float) -> Unit = {}
) {
    var internalRotation by remember { mutableFloatStateOf(value) }
    val animatedRotation by animateFloatAsState(targetValue = value, label = "knob_rot")

    Column(
        modifier = modifier.testTag("brass_knob_$label"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .aspectRatio(1f)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val delta = -dragAmount.y * 0.005f + dragAmount.x * 0.005f
                        internalRotation = (internalRotation + delta).coerceIn(0f, 1f)
                        onValueChange(internalRotation)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(size)) {
                val center = Offset(size.toPx() / 2f, size.toPx() / 2f)
                val radius = size.toPx() / 2f

                // Outer base plate with notched ring
                drawCircle(
                    color = Color(0xFF1E140E),
                    radius = radius,
                    center = center
                )
                drawCircle(
                    color = Color(0xFF4A3828),
                    radius = radius * 0.94f,
                    center = center,
                    style = Stroke(width = 1.5f)
                )

                // Rotary knob face (Fluted Brass)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFE5A93C),
                            Color(0xFFC9B896),
                            Color(0xFF8C6D3B),
                            Color(0xFF38291F)
                        ),
                        center = center,
                        radius = radius * 0.8f
                    ),
                    radius = radius * 0.8f,
                    center = center
                )

                // Fluted notches around knob
                val angleDeg = 135.0 + (animatedRotation * 270.0)
                for (k in 0 until 8) {
                    val flutedAngle = (k * 45.0) * (Math.PI / 180.0)
                    val fx = center.x + (radius * 0.65f * cos(flutedAngle)).toFloat()
                    val fy = center.y + (radius * 0.65f * sin(flutedAngle)).toFloat()
                    drawCircle(
                        color = Color(0xFF2C1F16),
                        radius = 2.5f,
                        center = Offset(fx, fy)
                    )
                }

                // Pointer notch
                val angleRad = angleDeg * (Math.PI / 180.0)
                val notchStart = Offset(
                    center.x + (radius * 0.25f * cos(angleRad)).toFloat(),
                    center.y + (radius * 0.25f * sin(angleRad)).toFloat()
                )
                val notchEnd = Offset(
                    center.x + (radius * 0.78f * cos(angleRad)).toFloat(),
                    center.y + (radius * 0.78f * sin(angleRad)).toFloat()
                )
                drawLine(
                    color = Color(0xFF07090C),
                    start = notchStart,
                    end = notchEnd,
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = Color(0xFFFFFBEB),
                    start = notchStart,
                    end = notchEnd,
                    strokeWidth = 1.5f,
                    cap = StrokeCap.Round
                )

                // Central screw head
                drawCircle(
                    color = Color(0xFF2C1F16),
                    radius = radius * 0.22f,
                    center = center
                )
                drawCircle(
                    color = Color(0xFFE5A93C),
                    radius = radius * 0.16f,
                    center = center
                )
            }
        }

        Text(
            text = label,
            color = Color(0xFFF3E5AB),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier.padding(top = 2.dp)
        )
        Text(
            text = subLabel,
            color = Color(0xFF94A3B8),
            fontSize = 8.sp,
            fontFamily = FontFamily.Monospace
        )
    }
}
