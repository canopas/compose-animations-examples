package com.canopas.composeanimations.animations

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.canopas.composeanimations.ui.theme.ThemeColor

@Preview
@Composable
fun PreviewRotatingSquare() {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = ThemeColor
    ) {
        RotatingSquare()
    }
}

@Composable
fun RotatingSquare() {

    var xRotation by remember {
        mutableStateOf(0f)
    }
    var yRotation by remember {
        mutableStateOf(0f)
    }

    val animationSpec = tween<Float>(1000, easing = LinearEasing)

    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            animate(
                0f,
                180f,
                animationSpec = animationSpec,
                block = { value, _ -> xRotation = value }
            )
            animate(
                0f,
                180f,
                animationSpec = animationSpec,
                block = { value, _ -> yRotation = value }
            )
        }
    })

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer {
                    rotationX = xRotation
                    rotationY = yRotation
                }
                .background(Color.White)
        )
    }

}

