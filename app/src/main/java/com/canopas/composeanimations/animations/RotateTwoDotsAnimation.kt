package com.canopas.composeanimations.animations

import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import com.canopas.composeanimations.ui.theme.ThemeColor
import kotlin.math.cos
import kotlin.math.sin


@Preview
@Composable
fun PreviewRotateTwoDotsAnimation() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ThemeColor
    ) {
        RotateTwoDotsAnimation()
    }
}

@Composable
fun RotateTwoDotsAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    val animValue by infiniteTransition.animateFloat(
        initialValue = .2f,
        targetValue = .8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animValue2 by infiniteTransition.animateFloat(
        initialValue = .2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier) {
            withTransform({
                scale(animValue)
            }, {
                drawCircle(
                    Color.White, center = center,
                    alpha = animValue,
                    radius = 50f
                )

            })

            val x = (center.x + cos(Math.toRadians(rotation.toDouble())) * 10f).toFloat()
            val y = (center.y + sin(Math.toRadians(rotation.toDouble())) * 140f).toFloat()

            withTransform({
                scale(animValue2)
            }, {
                drawCircle(
                    Color.White, alpha = animValue2, center = Offset(x, y),
                    radius = 50f
                )
            })
        }
    }
}