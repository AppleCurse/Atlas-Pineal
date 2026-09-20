package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PinealEyeView(
    modifier: Modifier = Modifier,
    eyeSize: Dp = 190.dp,
    intensity: Float = 0.5f,
    isThinking: Boolean = false,
    onEyeClick: () -> Unit = {}
) {
    // Infinite animation for subtle organic eye dilation and breathing
    val infiniteTransition = rememberInfiniteTransition(label = "eye_breath")
    val pulseDilation by infiniteTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isThinking) 1200 else 2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dilation"
    )

    val auraGlow by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isThinking) 800 else 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    var gazeOffsetX by remember { mutableFloatStateOf(0f) }
    var gazeOffsetY by remember { mutableFloatStateOf(0f) }
    var isBlinking by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .size(eyeSize)
            .aspectRatio(1f)
            .testTag("pineal_eye_view")
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        // Calculate relative gaze offset
                        val centerX = eyeSize.toPx() / 2f
                        val centerY = eyeSize.toPx() / 2f
                        gazeOffsetX = ((offset.x - centerX) / centerX).coerceIn(-0.35f, 0.35f)
                        gazeOffsetY = ((offset.y - centerY) / centerY).coerceIn(-0.25f, 0.25f)
                        tryAwaitRelease()
                        // Return gaze gently to center
                        gazeOffsetX = 0f
                        gazeOffsetY = 0f
                    },
                    onTap = {
                        isBlinking = true
                        onEyeClick()
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2f

            // 1. Heavy Outer Brass Bezel with Rivet Accents
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2C1F16),
                        Color(0xFF4A3828),
                        Color(0xFF1E140E)
                    ),
                    center = center,
                    radius = radius
                ),
                radius = radius,
                center = center
            )

            // Outer Brass Ring
            drawCircle(
                color = Color(0xFFC9B896),
                radius = radius * 0.96f,
                center = center,
                style = Stroke(width = 3.5f)
            )

            drawCircle(
                color = Color(0xFFE5A93C),
                radius = radius * 0.91f,
                center = center,
                style = Stroke(width = 1.5f)
            )

            // Rivets / Screws around bezel rim (12 clock positions)
            for (i in 0 until 12) {
                val angle = (i * 30.0) * (Math.PI / 180.0)
                val rivetX = center.x + (radius * 0.935f * cos(angle)).toFloat()
                val rivetY = center.y + (radius * 0.935f * sin(angle)).toFloat()
                drawCircle(
                    color = Color(0xFF1A120B),
                    radius = 3.5f,
                    center = Offset(rivetX, rivetY)
                )
                drawCircle(
                    color = Color(0xFFE5A93C),
                    radius = 2f,
                    center = Offset(rivetX, rivetY)
                )
            }

            // 2. Mother of Pearl (Sedef) Inlaid Ring
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFE8E0D2),
                        Color(0xFFC7BBA8),
                        Color(0xFFA09380),
                        Color(0xFF382A1D)
                    ),
                    center = center,
                    radius = radius * 0.88f
                ),
                radius = radius * 0.88f,
                center = center
            )

            // Inner Brass Bezel
            drawCircle(
                color = Color(0xFFC9B896),
                radius = radius * 0.74f,
                center = center,
                style = Stroke(width = 2.5f)
            )

            // 3. Eye Cavity Shadow (Dark Ebony)
            drawCircle(
                color = Color(0xFF07090C),
                radius = radius * 0.72f,
                center = center
            )

            // 4. Almond-shaped Sclera (Göz Akı / Sedef Tonu)
            val eyeWidth = radius * 1.35f
            val eyeHeight = if (isBlinking) 2f else radius * 0.78f

            val eyePath = Path().apply {
                moveTo(center.x - eyeWidth / 2f, center.y)
                quadraticTo(
                    center.x, center.y - eyeHeight,
                    center.x + eyeWidth / 2f, center.y
                )
                quadraticTo(
                    center.x, center.y + eyeHeight,
                    center.x - eyeWidth / 2f, center.y
                )
                close()
            }

            // Draw Sclera
            drawPath(
                path = eyePath,
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFFAF6F0),
                        Color(0xFFE3DACB),
                        Color(0xFF9E8F7A),
                        Color(0xFF423425)
                    ),
                    center = center,
                    radius = eyeWidth / 2f
                )
            )

            // Sclera Outline
            drawPath(
                path = eyePath,
                color = Color(0xFF2B1D12),
                style = Stroke(width = 2.5f)
            )

            // 5. Iris (Golden, Amber, and Emerald Phosphor Glow)
            val gazeShiftX = gazeOffsetX * radius * 0.3f
            val gazeShiftY = gazeOffsetY * radius * 0.2f
            val irisCenter = Offset(center.x + gazeShiftX, center.y + gazeShiftY)
            val baseIrisRadius = radius * 0.38f * (1f + (intensity - 0.5f) * 0.2f)

            // Iris Aura glow
            val glowColor = if (isThinking) Color(0xFF22C55E) else Color(0xFFE5A93C)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        glowColor.copy(alpha = 0.6f * auraGlow),
                        glowColor.copy(alpha = 0.2f * auraGlow),
                        Color.Transparent
                    ),
                    center = irisCenter,
                    radius = baseIrisRadius * 1.5f
                ),
                radius = baseIrisRadius * 1.5f,
                center = irisCenter
            )

            // Iris Texture & Radial Strands
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFF3E5AB),
                        Color(0xFFE5A93C),
                        Color(0xFFB45309),
                        Color(0xFF15803D),
                        Color(0xFF0F172A)
                    ),
                    center = irisCenter,
                    radius = baseIrisRadius
                ),
                radius = baseIrisRadius,
                center = irisCenter
            )

            // Iris striations / Radiating rays
            for (j in 0 until 24) {
                val rayAngle = (j * 15.0) * (Math.PI / 180.0)
                val rStart = baseIrisRadius * 0.45f
                val rEnd = baseIrisRadius * 0.95f
                val p1 = Offset(
                    irisCenter.x + (rStart * cos(rayAngle)).toFloat(),
                    irisCenter.y + (rStart * sin(rayAngle)).toFloat()
                )
                val p2 = Offset(
                    irisCenter.x + (rEnd * cos(rayAngle)).toFloat(),
                    irisCenter.y + (rEnd * sin(rayAngle)).toFloat()
                )
                drawLine(
                    color = Color(0xFFFDE68A).copy(alpha = 0.4f),
                    start = p1,
                    end = p2,
                    strokeWidth = 1.2f,
                    cap = StrokeCap.Round
                )
            }

            // Iris border
            drawCircle(
                color = Color(0xFF26190F),
                radius = baseIrisRadius,
                center = irisCenter,
                style = Stroke(width = 2f)
            )

            // 6. Pupil (Deep Black with breathing dilation)
            val pupilRadius = baseIrisRadius * 0.42f * pulseDilation
            drawCircle(
                color = Color(0xFF030406),
                radius = pupilRadius,
                center = irisCenter
            )

            // 7. Epifiz Core Sparkle (High Consciousness reflection)
            val sparkleCenter = Offset(
                irisCenter.x - pupilRadius * 0.35f,
                irisCenter.y - pupilRadius * 0.35f
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.95f),
                radius = pupilRadius * 0.35f,
                center = sparkleCenter
            )
            drawCircle(
                color = Color(0xFF86EFAC).copy(alpha = 0.8f),
                radius = pupilRadius * 0.18f,
                center = Offset(sparkleCenter.x + 4f, sparkleCenter.y + 4f)
            )
        }
    }
}
