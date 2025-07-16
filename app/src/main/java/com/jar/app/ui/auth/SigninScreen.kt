package com.jar.app.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.jar.app.R
import com.jar.app.navigation.JarScreens
import com.jar.app.ui.theme.Purple80
import com.jar.app.utils.BaseScreenUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun SignInScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onPrimary
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowSignInForm(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSignInForm(navController: NavHostController) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val triggerSignIn = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Move SigninUser outside the Box to ensure proper composition
    if (triggerSignIn.value) {
        SigninUser(
            email = emailState.value,
            password = passwordState.value,
            navController = navController,
            triggerSignIn = triggerSignIn.value,
            snackbarHostState = snackbarHostState,
            onFinished = { triggerSignIn.value = false }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Toolbar
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple80 // Replace with your primary_app_color
                ),
                modifier = Modifier.height(70.dp)
            )

            // SignIn Image
            Image(
                painter = painterResource(id = R.drawable.signin_image),
                contentDescription = "Sign In Image",
                modifier = Modifier
                    .size(230.dp)
                    .padding(top = 30.dp)
                    .align(Alignment.CenterHorizontally)
            )

            // Card View
            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 20.dp)
                ) {
                    // Email Input
                    OutlinedTextField(
                        value = emailState.value,
                        onValueChange = { emailState.value = it },
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    // Password Input
                    var passwordVisible by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = passwordState.value,
                        onValueChange = { passwordState.value = it },
                        label = { Text("Password") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = icon, contentDescription = "Toggle Password")
                            }
                        }
                    )

                    // Sign In Button

                        Button(
                            onClick = {
                                triggerSignIn.value = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(Purple80)
                        ) {
                            Text("Sign In")
                        }



                    // Sign Up Prompt
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("Don't have an account?", color = Color.Black)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Sign Up",
                            color = Purple80,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                navController.navigate(JarScreens.SignupScreen.name)
                            }
                        )
                    }
                }
            }
        }

        // Add SnackbarHost to display error messages
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun SigninUser(
    email: String,
    password: String,
    navController: NavHostController,
    triggerSignIn: Boolean,
    snackbarHostState: SnackbarHostState,
    onFinished: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }

    if (loading) {
        BaseScreenUtils.ShowProgressBar()
    }

    LaunchedEffect(email,password) {
        if (triggerSignIn) {
            println("debug--> Starting signIn")
            if (validForm(email, password, snackbarHostState, coroutineScope)) {
                println("debug--> form validation passed")
                loading = true

                try {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            loading = false
                            if (task.isSuccessful) {
                                navController.navigate(JarScreens.HomeScreen.name) {
                                    popUpTo(JarScreens.SigninScreen.name) { inclusive = true }
                                }
                            } else {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Sign-in failed: ${task.exception?.message ?: "Unknown error"}"
                                    )
                                }
                            }
                            onFinished()
                        }
                        .addOnFailureListener { exception ->
                            loading = false
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Sign-in error: ${exception.message}")
                            }
                            onFinished()
                        }
                } catch (e: Exception) {
                    loading = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Error: ${e.message}")
                    }
                    onFinished()
                }
            } else {
                println("form validation failed")
                onFinished()
            }
        }
    }
}

private fun validForm(
    email: String,
    password: String,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
): Boolean {
    return when {
        email.isEmpty() -> {
            BaseScreenUtils.showErrorSnackBar(snackbarHostState, "Please Enter Email", scope)
            false
        }
        !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
            BaseScreenUtils.showErrorSnackBar(snackbarHostState, "Please Enter Valid Email", scope)
            false
        }
        password.isEmpty() -> {
            BaseScreenUtils.showErrorSnackBar(snackbarHostState, "Please Enter Password", scope)
            false
        }
        password.length < 6 -> {
            BaseScreenUtils.showErrorSnackBar(snackbarHostState, "Password must be at least 6 characters", scope)
            false
        }
        else -> {
            true
        }
    }
}
