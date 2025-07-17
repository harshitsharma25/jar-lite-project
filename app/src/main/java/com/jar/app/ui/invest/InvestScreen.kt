package com.jar.app.ui.invest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jar.app.R
import com.jar.app.ui.theme.Dark_Purple

@Composable
fun InvestScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val boxHeight = screenHeight * 0.25f
    var amountToInvest by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 45.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(boxHeight)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(vertical = 12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(50.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_lightning),
                                contentDescription = "lightning",
                                modifier = Modifier.size(70.dp)
                            )
                            Column {
                                Text(
                                    text = "INSTANT",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color(0xFFFFD700),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "SAVE",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color(0xFFFFD700),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 18.dp)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(160.dp)
                                .padding(end = 12.dp)
                                .clip(RoundedCornerShape(15.dp))
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.instant_save_gold),
                                contentDescription = "save gold",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Save in Gold",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 15.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            InvestAmount { amount ->
                amountToInvest = amount.toString()
            }

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "Available Offers",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 15.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OfferCard()

            Spacer(modifier = Modifier.weight(1f)) // pushes PayNow to bottom
        }

        // Bottom PayNow Button
        PayNow(
            enteredAmount = amountToInvest,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Composable
fun InvestAmount(amount : (Int) -> Unit) {
    var investAmount by remember {
        mutableStateOf(50)
    }
    val focusReqester = remember {
        FocusRequester()
    }

    LaunchedEffect(Unit){
        focusReqester.requestFocus()
    }

    OutlinedTextField(
        value = investAmount.toString(),
        onValueChange = {
            investAmount = it.toIntOrNull() ?: 0
            amount(investAmount)
        },
        label = { Text("Enter Amount") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Dark_Purple,
            focusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            //unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            focusedLabelColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .focusRequester(focusReqester)
        ,
    )
}

@Composable
fun OfferCard(){
    Card(
        modifier = Modifier.padding(horizontal = 18.dp)
    ){
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = "MYFIRSTGOLD",
                    color = Color.White,
                    fontSize = 15.sp,

                    )
                Text(
                    text = "APPLY",
                    color = Color.Green,
                    fontSize = 15.sp,
                )
            }
            Text(
                text = "Get extra gold upto ₹20 on minimum purchase of ₹10",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.5f),
            )
        }
    }
}

@Composable
fun PayNow(enteredAmount: String, modifier: Modifier = Modifier){
    val amountToShow = if (enteredAmount.isBlank()) "50" else enteredAmount

    Box(modifier = modifier
        .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 12.dp,)) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = "₹$amountToShow", fontSize = 30.sp, color = Color.White)
                Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = "drowpDown")
            }

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = Dark_Purple),
                modifier = Modifier.height(60.dp).width(160.dp).clip(RoundedCornerShape(30.dp))
            ) {
                Text(
                    text = "Pay Now",
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}
