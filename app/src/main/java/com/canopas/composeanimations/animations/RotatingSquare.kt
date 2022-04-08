package com.canopas.composeanimations.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun RotatingSquare() {

    val infiniteTransition = rememberInfiniteTransition()

    var allowedToRotateX by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(1000)
            allowedToRotateX = !allowedToRotateX
        }
    })

    val xRotation by infiniteTransition.animateFloat(
        initialValue = 180F,
        targetValue = 0F,

        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        )
    )

    val yRotation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 180F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        )
    )

    Box(
        modifier = Modifier
            .size(60.dp)
            .graphicsLayer {
                if (allowedToRotateX) rotationX = xRotation
                else
                    rotationY = yRotation
            }
            .background(Color.White)
    )

}

