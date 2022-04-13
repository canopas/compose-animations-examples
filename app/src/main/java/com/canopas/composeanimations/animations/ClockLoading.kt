package com.canopas.composeanimations.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.canopas.composeanimations.ui.theme.ThemeColor

@Preview
@Composable
fun PreviewClockLoading() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ThemeColor
    ) {
        ClockLoading()
    }
}

@Composable
fun ClockLoading() {

    val infiniteTransition = rememberInfiniteTransition()

    val minHeightWidth: Dp = 80.dp

    val progressRotation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 1F,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing)
        )
    )

    val hourRotation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 1F,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing)
        )
    )

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .size(150.dp)
                .border(2.dp, color = Color.Black, shape = CircleShape)
                .background(Color.White, shape = CircleShape)
        ) {
            val middle = Offset(size.minDimension / 2, size.minDimension / 2)
            withTransform(
                {
                    rotate(360f * progressRotation, middle)
                }, {
                    drawLine(
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round,
                        color = ThemeColor,
                        start = middle,
                        end = Offset(
                            size.minDimension / 2,
                            (minHeightWidth / 2 - 20.dp).toPx()
                        )
                    )
                }
            )
            withTransform(
                {
                    rotate(360f * hourRotation, middle)
                }, {
                    drawLine(
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round,
                        color = Color.Black,
                        start = middle,
                        end = Offset(
                            size.minDimension / 2,
                            (minHeightWidth / 2 - 12.dp).toPx()
                        )
                    )
                }
            )
        }
    }
}