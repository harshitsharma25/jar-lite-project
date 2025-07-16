package com.jar.app.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.jar.app.R
import com.jar.app.navigation.JarScreens


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
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(text = "Welcome to the Home Screen", color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.clickable {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(JarScreens.SigninScreen.name) })
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
fun HomeScreenFAB(){
    FloatingActionButton(
        onClick = {
            // Handle save instantly action
        },
        modifier = Modifier
            .width(200.dp)
            .height(56.dp),
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
                            Color(0xFF4B0082), // Purple
                            Color(0xFF800080)  // Light Purple
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
                    painter = painterResource(id = R.drawable.ic_lightning), // Replace with your lightning icon
                    contentDescription = "Lightning",
                    tint = Color(0xFFFFD700), // Golden color
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