package com.canopas.composeanimations.animations

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.canopas.composeanimations.ui.theme.ThemeColor
import kotlin.math.cos
import kotlin.math.sin

@Preview
@Composable
fun PreviewRotateDotAnimation() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ThemeColor
    ) {
        RotateDotAnimation()
    }
}

@Composable
fun RotateDotAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier) {

            drawCircle(
                Color.White.copy(.6f), center = center,
                radius = 150f,
                style = Stroke(width = 10f)
            )

            val x = (center.x + cos(Math.toRadians(rotation.toDouble())) * 120f).toFloat()
            val y = (center.y + sin(Math.toRadians(rotation.toDouble())) * 120f).toFloat()

            drawCircle(
                Color.White, center = Offset(x, y),
                radius = 20f
            )
        }
    }
}