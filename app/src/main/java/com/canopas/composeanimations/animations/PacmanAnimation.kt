package com.canopas.composeanimations.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.canopas.composeanimations.ui.theme.ThemeColor

@Preview
@Composable
fun PreviewPacmanAnimation() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ThemeColor
    ) {
        PacmanAnimation()
    }
}

@Composable
fun PacmanAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    val mouthAnimation by infiniteTransition.animateFloat(
        initialValue = 360F,
        targetValue = 280F,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val antiMouthAnimation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 40F,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(80.dp)

        ) {
            drawArc(
                color = Color.White,
                startAngle = antiMouthAnimation,
                sweepAngle = mouthAnimation,
                useCenter = true,
            )

            drawCircle(
                color = Color.Black,
                radius = 15f,
                center = Offset(x = this.center.x + 15f, y = this.center.y - 85f)
            )
        }

    }
}