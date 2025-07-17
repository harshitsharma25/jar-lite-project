package com.jar.app.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController


@Composable
fun ProfileScreen(navController: NavHostController){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val boxHeight = screenHeight * 0.7f

    val viewModel : ProfileViewModel = hiltViewModel()
    val user = viewModel.user.value
    val userName = user?.name
    val userAge = user?.age
    val userMobile = user?.mobile
    val userEmail = user?.email
    val userProfileImage = user?.image

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {


        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(vertical = 45.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Color.White
            )
        }

        Box(
            modifier = Modifier
                .height(boxHeight)
                .fillMaxWidth()
                .padding(top = 110.dp)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (user != null) {

                    ProfileTextField(
                        value = userName ?: "",
                        label = "Name",
                        focusable = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        onValueChanged = {value -> viewModel.updateField("name",value)}
                    )
                    ProfileTextField(
                        value = userEmail,
                        label = "Email",
                        focusable = false,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    )
                    ProfileTextField(
                        value = userMobile ?: "",
                        label = "Mobile",
                        focusable = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        onValueChanged = {value -> viewModel.updateField("mobile",value)}
                    )
                    ProfileTextField(
                        value = userAge ?: "",
                        label = "Age",
                        focusable = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        onValueChanged = {value -> viewModel.updateField("age",value)}
                    )
                } else {
                    CircularProgressIndicator() // or loading state
                }
            }
        }

    }
}


@Composable
fun ProfileTextField(
    value: String?,
    label: String,
    focusable: Boolean,
    keyboardOptions: KeyboardOptions,
    onValueChanged : (String) -> Unit = {},
){

    var textFieldValue by remember {
        mutableStateOf(value)
    }
    TextField(
        value = textFieldValue ?: "",
        onValueChange = {
            textFieldValue = it
            if(focusable){
                onValueChanged(it)
            }
        },
        singleLine = true,
        label = {Text(text = label)},
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor = Color.Green,
            disabledContainerColor = Color.Transparent,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            focusedIndicatorColor = Color.White.copy(alpha = 0.6f),    // ✅ underline when focused
            unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f),  // ✅ underline when not focused
            disabledIndicatorColor = Color.White.copy(alpha = 0.3f)

        ),
        keyboardOptions = keyboardOptions,
        enabled = focusable,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp)
    )
}
