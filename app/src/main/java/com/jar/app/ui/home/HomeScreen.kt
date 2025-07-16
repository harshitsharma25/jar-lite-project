package com.jar.app.ui.home

import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Hexagon
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Animation
import androidx.compose.material.icons.outlined.FileCopy
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.jar.app.R
import com.jar.app.model.YoutubeVideoResponse.YoutubeVideoItems
import com.jar.app.navigation.JarScreens
import com.jar.app.ui.theme.Dark_Purple
import com.jar.app.ui.theme.Purple80
import com.jar.app.widgets.ShimmerVideoCard


@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            HomeScreenTopBar()
        },
        bottomBar = {
            HomeScreenBottomBar()
        },
        floatingActionButton = {
            HomeScreenFAB()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            MySavingsData()

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "User Success Stories",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 12.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold // Correct place
            )

            Spacer(modifier = Modifier.height(28.dp))

            UserSuccessStories(navController = navController)

            RecommendedForYou()
        }
    }
}

@Composable
fun MySavingsData() {
    val viewModel : HomeScreenViewModel = hiltViewModel()
    val userName by viewModel.userName

    // State for gold amount (you can replace this with your actual data source)
    var goldAmount by remember { mutableStateOf("0.0000g") }

    // Get screen height for 40% calculation
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val cardHeight = screenHeight * 0.4f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Dark_Purple // Using your Dark_Purple for subtle contrast
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side - User info and button
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                // User greeting and gold amount
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Hi $userName",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White, // Using your White color
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Gold Purchased",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f) // Subtle white for secondary text
                    )

                    Text(
                        text = goldAmount,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFFFFD700), // Gold color for the amount
                        fontWeight = FontWeight.Bold
                    )
                }

                // Start Today button
                AnimatedShineButton(
                    onClick = {}
                )
            }

            // Right side - Gold inspiring banner
            Card(
                modifier = Modifier
                    .size(150.dp, 190.dp)
                    .padding(start = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Purple80.copy(alpha = 0.3f) // Subtle purple background
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Gold icon
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color(0xFFFFD700), // Gold color for the icon background
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🥇",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Build Your",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "Gold Future",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFFFD700), // Gold color for emphasis
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun UserSuccessStories(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
){
    val videoItems = viewModel.videos.collectAsLazyPagingItems()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(videoItems.itemCount) { index ->
            val video = videoItems[index]
            if (video != null && video.id.videoId != null) {
                VideoThumbnail(video = video) {
                    val encodedTitle = Uri.encode(video.snippet.title)
                    navController.navigate("${JarScreens.VideoPlayerScreen.name}/${video.id.videoId}/$encodedTitle")

                }
            }
        }

            if(videoItems.loadState.append is LoadState.Loading){
                items(3) { ShimmerVideoCard()}
            }

            if(videoItems.loadState.append is LoadState.Error){
                item {
                    val error = (videoItems.loadState.append as LoadState.Error).error
                    Text(
                        text = "Failed to load more: ${error.message}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
    }
    
}

@Composable
fun VideoThumbnail(video: YoutubeVideoItems, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(240.dp)
            .height(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(0.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(Color.White.copy(alpha = 0.15f))
    ) {
        Column {
            AsyncImage(
                model = video.snippet.thumbnails.medium?.url,
                contentDescription = video.snippet.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Text(
                text = video.snippet.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(6.dp),
            )
        }
    }
}


@Composable
fun RecommendedForYou(){

}


@Composable
fun AnimatedShineButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Start Today"
) {
    val Purple80 = Color(0x6F2C002C)

    // Animation state for the shine effect
    val infiniteTransition = rememberInfiniteTransition(label = "shine")

    val shimmerTranslateAnim by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000, // 2 seconds for one cycle
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    // Create gradient brush for the shine effect
    val shimmerBrush = Brush.linearGradient(
        colors = listOf(
            Color.Transparent,
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.6f),
            Color.White.copy(alpha = 0.3f),
            Color.Transparent
        ),
        start = Offset(
            x = shimmerTranslateAnim * 500f - 200f,
            y = shimmerTranslateAnim * 200f - 100f
        ),
        end = Offset(
            x = shimmerTranslateAnim * 500f + 200f,
            y = shimmerTranslateAnim * 200f + 100f
        )
    )

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.background(
            brush = shimmerBrush,
            shape = RoundedCornerShape(12.dp)
        )
    )
    {
        Button(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth(0.8f)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple80
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
    
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.bodyLarge
            )
            
           }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(){
    var goldPrice  = 234.35  //todo: fetch from the api

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Card
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
                    border = BorderStroke(1.dp, Color.Gray),
                    elevation = CardDefaults.cardElevation(0.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = stringResource(id = R.string.profile),
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }


                // Spacer that takes available horizontal space
                Spacer(modifier = Modifier.weight(1f))

                // Right side row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp), // Reduced spacing
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 2.dp)
                ) {
                    // Gold Price Card
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(Color.Transparent),
                        border = BorderStroke(2.dp, Color.Gray.copy(alpha = 0.4f)),
                        elevation = CardDefaults.cardElevation(0.dp),
                        modifier = Modifier
                            .width(150.dp)
                            .height(40.dp) // Optional: define height if you want consistent vertical centering
                            .align(Alignment.CenterVertically)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.gold_price, goldPrice),
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textDecoration = TextDecoration.None
                                )
                            )
                        }
                    }


                    // Icons Card
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
                        border = BorderStroke(1.dp, Color.Gray),
                        elevation = CardDefaults.cardElevation(0.dp),
                        modifier = Modifier.width(100.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 4.dp, vertical = 6.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Hexagon,
                                contentDescription = "hexagon",
                                tint = Color.White.copy(alpha = .5f),
                                modifier = Modifier
                                    .padding(end = 6.dp)
                                    .size(24.dp)
                            )

                            // Vertical divider
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(20.dp)
                                    .background(Color.Gray.copy(alpha = 0.5f))
                            )

                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "notifications",
                                tint = Color.White.copy(alpha = .5f),
                                modifier = Modifier
                                    .padding(start = 6.dp)
                                    .size(24.dp)
                            )
                        }
                    }

                }
            }
        },
                colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun HomeScreenBottomBar() {
    val barHeight = 120.dp
    val iconPadding = 34.dp
    var selectedTab by rememberSaveable { mutableStateOf(BottomBarTab.HOME) }

    Column {
        Box {
            // Curved background canvas
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barHeight)
            ) {
                val width = size.width
                val curveDepth = -45f
                val thickness = 8f
                val endHeight = 21f

                val path = Path().apply {
                    moveTo(0f, endHeight)
                    quadraticBezierTo(width * 0.5f, curveDepth, width, endHeight)
                    lineTo(width, endHeight + thickness)
                    quadraticBezierTo(
                        width * 0.5f,
                        curveDepth + thickness,
                        0f,
                        endHeight + thickness
                    )
                    close()
                }

                drawPath(
                    path = path,
                    color = Color(0xFF800080)
                )
            }

            // BottomAppBar
            BottomAppBar(
                containerColor = Color.Transparent,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barHeight),
                tonalElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = iconPadding),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomBarItem(
                        icon = if (selectedTab == BottomBarTab.HOME) Icons.Default.Home else Icons.Outlined.Home,
                        label = "Home",
                        selected = selectedTab == BottomBarTab.HOME,
                        onClick = { selectedTab = BottomBarTab.HOME }
                    )

                    BottomBarItem(
                        icon = if (selectedTab == BottomBarTab.JEWELLERY) Icons.Default.Animation else Icons.Outlined.Animation,
                        label = "Jewellery",
                        selected = selectedTab == BottomBarTab.JEWELLERY,
                        onClick = { selectedTab = BottomBarTab.JEWELLERY }
                    )

                    BottomBarItem(
                        icon = if (selectedTab == BottomBarTab.TRANSACTIONS) Icons.Default.FileCopy else Icons.Outlined.FileCopy,
                        label = "Transactions",
                        selected = selectedTab == BottomBarTab.TRANSACTIONS,
                        onClick = { selectedTab = BottomBarTab.TRANSACTIONS }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) Color.White else Color.White.copy(alpha = 0.35f),
                modifier = Modifier.size(30.dp)
            )
        }


        Text(
            text = label,
            fontSize = 12.sp,
            color = if (selected) Color.White else Color.White.copy(alpha = 0.35f)
        )
    }
}

enum class BottomBarTab {
    HOME, JEWELLERY, TRANSACTIONS
}

@Composable
fun HomeScreenFAB() {
    FloatingActionButton(
        onClick = {
            // Handle save instantly action
        },
        modifier = Modifier
            .width(200.dp)
            .height(65.dp)
            .padding(bottom = 8.dp), // <-- Adds space below FAB
        shape = RoundedCornerShape(28.dp),
        containerColor = Color.Transparent,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF4B0082),
                            Color(0xFF800080)
                        )
                    ),
                    shape = RoundedCornerShape(28.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lightning),
                    contentDescription = "Lightning",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(34.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Save Instantly",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
