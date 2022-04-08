package com.canopas.composeanimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canopas.composeanimations.animations.RotatingCircle
import com.canopas.composeanimations.ui.theme.ComposeAnimationsTheme
import com.canopas.composeanimations.ui.theme.ThemeColor
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = ThemeColor
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(50.dp)
                    ) {

//                        StepperAnimation(modifier = Modifier.align(CenterHorizontally))
//                        Spacer(modifier = Modifier.height(25.dp))
//                        HeartAnimation(modifier = Modifier.align(CenterHorizontally))
//                        Spacer(modifier = Modifier.height(75.dp))
//                        ProgressAnimation(modifier = Modifier.align(CenterHorizontally))
//                        Spacer(modifier = Modifier.height(125.dp))
//                        WavesAnimation(modifier = Modifier.align(CenterHorizontally))

                        RotatingCircle()
                    }
                }
            }
        }
    }
}

@Composable
fun StepperAnimation(modifier: Modifier) {
    var currentNumber by remember {
        mutableStateOf(0)
    }

    var frontNumber by remember {
        mutableStateOf(currentNumber)
    }
    var backNumber by remember {
        mutableStateOf(currentNumber)
    }

    var targetAngle by remember {
        mutableStateOf(0f)
    }

    val rotation = animateFloatAsState(
        targetValue = targetAngle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing,
        )
    )

    fun isFront(): Boolean {
        val value = kotlin.math.abs(rotation.value % 360)
        return value < 90 || value > 270
    }

    fun flipBack() {
        currentNumber -= 1
        if (isFront()) {
            backNumber = currentNumber
        } else {
            frontNumber = currentNumber
        }
        targetAngle -= 180f
    }

    fun flipNext() {
        currentNumber += 1
        if (isFront()) {
            backNumber = currentNumber
        } else {
            frontNumber = currentNumber
        }
        targetAngle += 180f
    }

    @Composable
    fun Step(number: Int, rotationY: Float) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .graphicsLayer {
                    this.rotationY = rotationY
                }
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    flipBack()
                })

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                Text(
                    modifier = Modifier.align(Center),
                    text = number.toString(), color = Color.Black, fontSize = 40.sp
                )
            }

            Box(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    flipNext()
                })
        }

    }

    Box(
        modifier = modifier
            .height(80.dp)
            .width(200.dp)
            .graphicsLayer {
                rotationY = rotation.value
            }
    ) {
        if (isFront()) {
            //Front
            Step(number = frontNumber, rotationY = 0f)
        } else {
            //Back
            Step(number = backNumber, rotationY = rotation.value)
        }
    }

}

@Composable
fun HeartAnimation(modifier: Modifier) {

    val infiniteTransition = rememberInfiniteTransition()
    val dy by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val travelDistance = with(LocalDensity.current) { 30.dp.toPx() }

    Column(
        modifier = modifier
            .width(200.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            "",
            tint = Color.White,
            modifier = Modifier
                .size(100.dp)
                .align(CenterHorizontally)
                .graphicsLayer {
                    translationY = dy * travelDistance
                },
        )

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .width(40.dp)
                .height(10.dp)
                .align(CenterHorizontally)
                .graphicsLayer {
                    scaleX = 0.5f + dy / 2
                    alpha = 0.3f + dy / 2
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White, shape = CircleShape)
            )
        }
    }

}

@Composable
fun ProgressAnimation(modifier: Modifier) {

    val dots = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
    )

    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(4000, easing = FastOutLinearInEasing),
        repeatMode = RepeatMode.Restart,
    )

    dots.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f, animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 2000
                        0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
                        1.0f at 200 with LinearOutSlowInEasing // for 15-75 ms
                        0.0f at 400 with LinearOutSlowInEasing // for 0-15 ms
                        0.0f at 2000
                    },
                    repeatMode = RepeatMode.Restart,
                )
            )
        }
    }

    val dys = dots.map { it.value }

    val travelDistance = with(LocalDensity.current) { 15.dp.toPx() }

    Row(modifier) {
        dys.forEachIndexed { index, dy ->
            Box(
                Modifier
                    .size(25.dp)
                    .graphicsLayer {
                        translationY = -dy * travelDistance
                    },
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.White, shape = CircleShape)
                )
            }

            if (index != dys.size - 1) {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }

}

@Composable
fun WavesAnimation(modifier: Modifier) {

    val waves = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
    )

    val animationSpec = infiniteRepeatable<Float>(
        animation = tween(4000, easing = FastOutLinearInEasing),
        repeatMode = RepeatMode.Restart,
    )

    waves.forEachIndexed { index, animatable ->
        LaunchedEffect(animatable) {
            delay(index * 1000L)
            animatable.animateTo(
                targetValue = 1f, animationSpec = animationSpec
            )
        }
    }

    val dys = waves.map { it.value }

    Box(modifier) {
        // Waves
        dys.forEach { dy ->
            Box(
                Modifier
                    .size(50.dp)
                    .align(Center)
                    .graphicsLayer {
                        scaleX = dy * 4 + 1
                        scaleY = dy * 4 + 1
                        alpha = 1 - dy
                    },
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.White, shape = CircleShape)
                )
            }
        }

        // Mic icon
        Box(
            Modifier
                .size(50.dp)
                .align(Center)
                .background(color = Color.White, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_mic_24),
                "",
                tint = Color.Black,
                modifier = Modifier
                    .size(32.dp)
                    .align(Center)
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAnimationsTheme {
        StepperAnimation(Modifier)
    }
}