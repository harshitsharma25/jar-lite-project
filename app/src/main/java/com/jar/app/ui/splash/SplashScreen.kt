package com.jar.app.ui.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.jar.app.navigation.JarScreens
import com.jar.app.ui.theme.Dark_Purple
import com.jar.app.ui.theme.LigthGray
import com.jar.app.ui.theme.Purple80

@Composable
fun SplashScreen(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }

    // Animation values
    val alphaAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    val scaleAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    val offsetAnimation = animateFloatAsState(
        targetValue = if (startAnimation) 0f else 100f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    // Start animation when composable is first composed
    LaunchedEffect(Unit) {
        startAnimation = true
        delay(3000) // Show splash for 3 seconds

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null){
            navController.navigate(JarScreens.HomeScreen.name){
                popUpTo(JarScreens.SplashScreen.name) { inclusive = true }
            }
        }
        else{
            navController.navigate(JarScreens.SigninScreen.name) {
                popUpTo(JarScreens.SplashScreen.name) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0A0A), // Very dark at top
                        Color(0xFF1A0A1A), // Dark with subtle purple tint
                        Color(0xFF000000)  // Pure black at bottom
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Subtle background elements
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Large subtle circle
            drawCircle(
                color = Purple80.copy(alpha = 0.05f),
                radius = size.width * 0.7f,
                center = Offset(centerX, centerY)
            )

            // Medium purple accent circle
            drawCircle(
                color = Purple80.copy(alpha = 0.08f),
                radius = size.width * 0.4f,
                center = Offset(centerX, centerY)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .alpha(alphaAnimation.value)
                .scale(scaleAnimation.value)
                .offset(y = offsetAnimation.value.dp)
        ) {
            // App Icon with purple accent
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF1A1A1A), // Dark background
                                Color(0xFF2A1A2A)  // Slightly purple tinted dark
                            )
                        )
                    )
                    .border(
                        width = 1.5.dp,
                        color = Purple80.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Simple Jar Icon
                Canvas(
                    modifier = Modifier.size(50.dp)
                ) {
                    val strokeWidth = 3.dp.toPx()
                    val jarPath = Path().apply {
                        // Draw jar shape
                        moveTo(size.width * 0.25f, size.height * 0.35f)
                        lineTo(size.width * 0.75f, size.height * 0.35f)
                        lineTo(size.width * 0.7f, size.height * 0.85f)
                        quadraticBezierTo(
                            size.width * 0.5f, size.height * 1.0f,
                            size.width * 0.3f, size.height * 0.85f
                        )
                        close()
                    }

                    drawPath(
                        path = jarPath,
                        color = Color.White,
                        style = Stroke(width = strokeWidth)
                    )

                    // Jar lid with purple accent
                    drawRoundRect(
                        color = Purple80.copy(alpha = 0.8f),
                        topLeft = Offset(size.width * 0.2f, size.height * 0.15f),
                        size = Size(size.width * 0.6f, size.height * 0.25f),
                        cornerRadius = CornerRadius(6.dp.toPx()),
                        style = Fill
                    )

                    // Dollar sign
                    drawPath(
                        path = Path().apply {
                            moveTo(size.width * 0.5f, size.height * 0.45f)
                            lineTo(size.width * 0.5f, size.height * 0.75f)
                            moveTo(size.width * 0.42f, size.height * 0.52f)
                            quadraticBezierTo(
                                size.width * 0.5f, size.height * 0.48f,
                                size.width * 0.58f, size.height * 0.52f
                            )
                            quadraticBezierTo(
                                size.width * 0.5f, size.height * 0.56f,
                                size.width * 0.42f, size.height * 0.6f
                            )
                            quadraticBezierTo(
                                size.width * 0.5f, size.height * 0.64f,
                                size.width * 0.58f, size.height * 0.68f
                            )
                        },
                        color = LigthGray,
                        style = Stroke(width = strokeWidth)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // App Name with high contrast
            Text(
                text = "JarLite",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            // App Tagline with purple accent
            Text(
                text = "Smart Finance, Simple Savings",
                style = MaterialTheme.typography.bodyLarge,
                color = LigthGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Loading indicator with purple accent
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1A1A1A))
                    .border(
                        width = 1.dp,
                        color = Purple80.copy(alpha = 0.3f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(25.dp),
                    color = Purple80.copy(alpha = 0.8f),
                    strokeWidth = 2.5.dp
                )
            }
        }

        // Bottom version info
        Text(
            text = "Version 1.0.0",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF666666),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .alpha(alphaAnimation.value)
        )
    }
}