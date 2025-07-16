package com.jar.app.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerVideoCard() {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Box(
        modifier = Modifier
            .width(180.dp)
            .height(140.dp)
            .shimmer(shimmerInstance)
            .background(Color.Gray.copy(alpha = 0.4f), shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
    )
}
