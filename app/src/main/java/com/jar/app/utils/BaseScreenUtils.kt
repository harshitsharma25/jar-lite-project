package com.jar.app.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object BaseScreenUtils {

    fun showErrorSnackBar(snackbarHostState: SnackbarHostState, message: String, scope: CoroutineScope) {
        scope.launch {
            // Dismiss any existing snackbar first
            snackbarHostState.currentSnackbarData?.dismiss()
            println("-->debug Snackbar: $message")
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long, // Changed to Long for better visibility
                withDismissAction = true
            )
        }
    }

    @Composable
    fun ShowProgressBar() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
