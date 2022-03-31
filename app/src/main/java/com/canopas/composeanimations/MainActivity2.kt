package com.canopas.composeanimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.canopas.composeanimations.ui.theme.ComposeAnimationsTheme
import com.canopas.composeanimations.ui.theme.ThemeColor

class MainActivity2: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
         ComposeAnimationsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = ThemeColor
                ) {
                    CoolAnimations()
                }
            }
        }
    }
}

@Composable
fun CoolAnimations() {
    val infiniteTransition = rememberInfiniteTransition()
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.verticalScroll(scrollState)
    ) {

        TwinCircleAnimation(infiniteTransition)
        CircleOffsetAnimation(infiniteTransition)
        PacmanAnimation(infiniteTransition)
        ArcRotationAnimation(infiniteTransition)
    }
}

@Composable
fun TwinCircleAnimation(infiniteTransition: InfiniteTransition) {
    val twinCircleAnimation by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Row(
        modifier = Modifier
            .size(120.dp)
            .padding(12.dp)
            .clip(CircleShape)
            .background(Color.Red),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(15.dp)
                .scale(twinCircleAnimation)
                .clip(CircleShape)
                .background(Color.White)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Box(
            modifier = Modifier
                .size(15.dp)
                .scale(twinCircleAnimation)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}

@Composable
fun CircleOffsetAnimation(infiniteTransition: InfiniteTransition) {
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

@Composable
fun animateValues(
    values: List<Float>,
    animationSpec: AnimationSpec<Float> = spring(),
): State<Float> {

    // 1. Create the groups zipping with next entry
    val groups by rememberUpdatedState(newValue = values.zipWithNext())
    // 2. Start the state with the first value
    val state = remember { mutableStateOf(values.first()) }

    LaunchedEffect(key1 = groups) {
        val (_, setValue) = state
        // Start the animation from 0 to groups quantity
        animate(
            initialValue = 0f,
            targetValue = groups.size.toFloat(),
            animationSpec = animationSpec,
        ) { frame, _ ->
            // Get which group is being evaluated
            val integerPart = frame.toInt()
            val (initialValue, finalValue) = groups[frame.toInt()]
            // Get the current "position" from the group animation
            val decimalPart = frame - integerPart
            // Calculate the progress between the initial and final value
            setValue(
                initialValue + (finalValue - initialValue) * decimalPart
            )
        }
    }
    return state
}

@Composable
fun PacmanAnimation(infiniteTransition: InfiniteTransition) {
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

    Canvas(
        modifier = Modifier
            .padding(top = 16.dp)
            .size(100.dp)

    ) {
        drawArc(
            color = Color(0xFFFFd301),
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

@Composable
fun ArcRotationAnimation(infiniteTransition: InfiniteTransition) {
    val circleColor = Color(0xFFAFE1AF)
    val arcColor = Color(0XFFFFFFFF)
    val arcAngle1 by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 180F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val arcAngle2 by infiniteTransition.animateFloat(
        initialValue = 180F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val greenCircleAnimation by infiniteTransition.animateFloat(
        initialValue = 50f,
        targetValue = 80f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, delayMillis = 100, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = Modifier
            .padding(12.dp)
            .size(100.dp)
    ) {
        drawArc(
            color = arcColor,
            startAngle = arcAngle1,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = 10f, cap = StrokeCap.Round),
        )

        drawArc(
            color = arcColor,
            startAngle = arcAngle2,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = 10f, cap = StrokeCap.Round),
        )

        drawCircle(
            color = arcColor,
            radius = 120f,
        )

        drawCircle(
            color = circleColor,
            radius = greenCircleAnimation,
        )
    }
}