package com.canopas.composeanimations.animations

import androidx.compose.animation.animateColor
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
import com.canopas.composeanimations.utils.animateValues


@Preview
@Composable
fun PreviewCircleOffsetAnimation() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ThemeColor
    ) {
        CircleOffsetAnimation()
    }
}

@Composable
fun CircleOffsetAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    val easing = LinearOutSlowInEasing

    val color by infiniteTransition.animateColor(
        initialValue = Color(0xff2E5984),
        targetValue = Color(0xFF91BAD6),
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = easing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color2 by infiniteTransition.animateColor(
        initialValue = Color(0xFF91BAD6),
        targetValue = Color(0xff2E5984),
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = easing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val offsetX by animateValues(
        values = listOf(0f, 100f, -100f, 0f),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = easing),
            repeatMode = RepeatMode.Restart
        )
    )

    val scale by animateValues(
        values = listOf(1f, 10f, 10f, 10f, 1f),
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = easing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(100.dp)

        ) {
            drawCircle(
                color = Color.White,
            )
            drawCircle(
                color = color2,
                radius = 80f + scale * 4f,
                center = Offset(-offsetX + this.center.x, this.center.y)
            )
            drawCircle(
                color = color,
                radius = 80f + scale * 4f,
                center = Offset(offsetX + this.center.x, this.center.y)
            )
        }
    }
}