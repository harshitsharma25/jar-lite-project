package com.jar.app.ui.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jar.app.R
import com.jar.app.model.User
import com.jar.app.navigation.JarScreens
import com.jar.app.ui.theme.Purple80
import com.jar.app.utils.BaseScreenUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onPrimary
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowSignUpForm(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSignUpForm(navController: NavHostController) {
    val emailState = remember { mutableStateOf("") }
    val nameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val triggerSignUp = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Move SignUpUser outside the Box to ensure proper composition
    if (triggerSignUp.value) {
        SignUpUser(
            name = nameState.value,
            email = emailState.value,
            password = passwordState.value,
            navController = navController,
            triggerSignUp = triggerSignUp.value,
            snackbarHostState = snackbarHostState,
            onFinished = { triggerSignUp.value = false }
        )
    }

    // Use Scaffold for proper snackbar positioning
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.onPrimary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Toolbar
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple80 // Replace with your primary_app_color
                ),
                modifier = Modifier.height(70.dp)
            )

            // Content in scrollable column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // SignIn Image
                Image(
                    painter = painterResource(id = R.drawable.signup_image),
                    contentDescription = "Sign In Image",
                    modifier = Modifier
                        .size(230.dp)
                        .padding(top = 30.dp)
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
                        // Name Input
                        OutlinedTextField(
                            value = nameState.value,
                            onValueChange = { nameState.value = it },
                            label = { Text("Name") },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )

                        // Email Input
                        OutlinedTextField(
                            value = emailState.value,
                            onValueChange = { emailState.value = it },
                            label = { Text("Email") },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
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
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            ),
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

                        // Sign Up Button
                        Button(
                            onClick = {
                                triggerSignUp.value = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            shape = RoundedCornerShape(30.dp)
                        ) {
                            Text("Sign Up")
                        }

                        // Sign In Prompt
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Already have an account?")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Sign In",
                                color = Purple80,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {
                                    navController.navigate(JarScreens.SigninScreen.name)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SignUpUser(
    name: String,
    email: String,
    password: String,
    navController: NavHostController,
    triggerSignUp: Boolean,
    snackbarHostState: SnackbarHostState,
    onFinished: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }

    if (loading) {
        BaseScreenUtils.ShowProgressBar()
    }

    LaunchedEffect(triggerSignUp) {
        if (triggerSignUp) {
            println("debug--> Starting signUp")
            if (validForm(name, email, password, snackbarHostState, coroutineScope)) {
                println("debug--> form validation passed of signUp")
                loading = true

                try {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            loading = false // Hide progress bar

                            if (task.isSuccessful) {
                                val firebaseUser = task.result?.user
                                val uid = firebaseUser?.uid

                                if (uid != null) {
                                    AddUserToFirestore(
                                        uid = uid,
                                        name = name,
                                        email = email,
                                        onSuccess = {
                                            // Navigate to home screen after successful Firestore operation
                                            navController.navigate(JarScreens.HomeScreen.name) {
                                                // Clear the back stack so user can't go back to sign up
                                                popUpTo(JarScreens.SignupScreen.name) { inclusive = true }
                                            }
                                            onFinished()
                                        },
                                        onFailure = { exception ->
                                            coroutineScope.launch {
                                                snackbarHostState.showSnackbar("Error saving user data: ${exception.message}")
                                            }
                                            onFinished()
                                        }
                                    )
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Error: Unable to get user ID")
                                    }
                                    onFinished()
                                }
                            } else {
                                // Show error message
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Sign up failed: ${task.exception?.message}")
                                }
                                onFinished()
                            }
                        }
                        .addOnFailureListener { exception ->
                            loading = false // Hide progress bar
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Error: ${exception.message}")
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

private fun AddUserToFirestore(
    uid: String,
    name: String,
    email: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val user = User(
        id = uid,
        name = name,
        email = email,
        image = "",         // You can update this later if user uploads a photo
        mobile = "" ,         // Default or take input from EditText
        age = ""
    )

    FirebaseFirestore.getInstance()
        .collection("users")
        .document(uid)
        .set(user)
        .addOnSuccessListener {
            Log.d("Firestore", "User document added successfully.")
            onSuccess()
        }
        .addOnFailureListener { e ->
            Log.e("Firestore", "Failed to add user document.", e)
            onFailure(e)
        }
}

private fun validForm(
    name: String,
    email: String,
    password: String,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
): Boolean {
    return when {
        name.isEmpty() -> {
            BaseScreenUtils.showErrorSnackBar(snackbarHostState, "Please Enter Your Name", scope)
//            showToast("Please Enter Your Name")
//            Toast.makeText(LocalContext.current,"Please Enter Your Name",Toast.LENGTH_SHORT).show()
            false
        }
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

@Composable
fun showToast(message:String){
    Toast.makeText(LocalContext.current,message,Toast.LENGTH_SHORT).show()
}