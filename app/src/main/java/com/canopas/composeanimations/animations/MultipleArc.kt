package com.canopas.composeanimations.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.canopas.composeanimations.ui.theme.ThemeColor


@Preview
@Composable
fun PreviewMultipleArcAnimation() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ThemeColor
    ) {
        MultipleArcAnimation()
    }
}

@Composable
fun MultipleArcAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    val arcAngle1 by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val arcAngle2 by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val arcs = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
    )
    val tweens = listOf(
        infiniteRepeatable<Float>(
            animation = tween(800, easing = FastOutSlowInEasing)
        ),
        infiniteRepeatable<Float>(
            animation = tween(980, easing = FastOutSlowInEasing)
        ),
        infiniteRepeatable<Float>(
            animation = tween(1460, easing = FastOutSlowInEasing)
        ),
        infiniteRepeatable<Float>(
            animation = tween(1140, easing = FastOutSlowInEasing)
        ),
        infiniteRepeatable<Float>(
            animation = tween(2140, easing = FastOutSlowInEasing)
        )
    )

    arcs.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            animatable.animateTo(
                targetValue = 360f, animationSpec = tweens[index]
            )

        }
    }
    val angles = arcs.map { it.value }
    val sizes = arrayOf(100f, 160f, 220f, 280f, 340f)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
        ) {

            angles.forEachIndexed { index, angle ->
                val width = sizes[index]
                val topLeftArc1 = Offset((size.width - width) / 2f, (size.height - width) / 2f)

                drawArc(
                    color = Color.White,
                    size = Size(width, width),
                    startAngle = angle,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = topLeftArc1,
                    style = Stroke(width = 20f, cap = StrokeCap.Round),
                )
            }
        }

    }
}