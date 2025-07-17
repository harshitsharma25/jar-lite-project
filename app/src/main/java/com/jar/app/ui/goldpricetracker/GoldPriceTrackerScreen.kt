package com.jar.app.ui.goldpricetracker

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jar.app.R
import com.jar.app.ui.theme.Purple80
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf

@Composable
fun GoldPriceTrackerScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val boxHeight = screenHeight * 0.75f
    var goldPrice by remember {
        mutableStateOf(0.0)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(
                    bottom = WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding() + 12.dp
                )
        ) {
            Row(modifier = Modifier.padding(vertical = 45.dp)) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "back", tint = Color.White)
                }
                Text(
                    text = stringResource(R.string.gold_price_text),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Card(
                modifier = Modifier
                    .height(boxHeight)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.039f)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShowGoldPrice(goldPrice)
                    Spacer(modifier = Modifier.height(30.dp))
                    ShowChart(boxHeight = boxHeight * 0.7f, goldPrice = {price -> goldPrice = price})
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            BuyGoldTextAndField()
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .padding(horizontal = 18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple80),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Proceed", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun ShowGoldPrice(price : Double) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        Text("Live Gold Price", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
        Text(
            text = stringResource(R.string.gold_price_per_gram, price),
            color = Color(0xFFFFD700),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun ShowChart(viewModel: GoldPriceViewModel = hiltViewModel(),goldPrice : (Double) -> Unit,boxHeight: Dp) {
    val goldPriceState = viewModel.goldPrice.value

    when (goldPriceState) {
        is GoldPriceUiState.Loading -> {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        is GoldPriceUiState.Empty -> {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp), contentAlignment = Alignment.Center) {
                Text("No data available", color = Color.White.copy(alpha = 0.7f))
            }
        }

        is GoldPriceUiState.GoldPriceData -> {
            val todaysPrice = goldPriceState.price.price
            val goldPricePerGram = goldPriceState.price.price_gram_24k
            goldPrice(goldPricePerGram)

            val low = goldPriceState.price.low_price
            val high = goldPriceState.price.high_price

            val points = listOf(
                "Low" to low,
                "Now" to todaysPrice,
                "High" to high
            )

            val entries = points.mapIndexed { index, (_, value) ->
                entryOf(index.toFloat(), value.toFloat())
            }

            val model = entryModelOf(entries)
            val xLabels = points.map { it.first }

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(
                    text = "Gold Price Trend",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Chart(
                    chart = lineChart(),
                    model = model,
                    startAxis = startAxis(
                        title = "₹ Price",
                        titleComponent = textComponent(
                            color = Color.White, // Use Compose Color
                            textSize = 13.sp
                        ),
                        label = textComponent(
                            color = Color.White, // Use Compose Color
                            textSize = 13.sp
                        )
                    ),
                    bottomAxis = bottomAxis(
                        title = "Today Price",
                        titleComponent = textComponent(
                            color = Color.White, // Use Compose Color
                            textSize = 13.sp
                        ),
                        label = textComponent(
                            color = Color.White, // Use Compose Color
                            textSize = 13.sp
                        ),
                        valueFormatter = { value, _ -> // Corrected to 2 parameters
                            xLabels.getOrElse(value.toInt()) { value.toString() }
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(boxHeight)
                )
            }
        }

        null -> Unit
    }
}

@Composable
fun BuyGoldTextAndField() {
    var amount by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)) {
        Text("Buy Gold", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Don't miss out! Buy Gold Instantly", color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { if (it.all { c -> c.isDigit() }) amount = it },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            visualTransformation = VisualTransformation { text ->
                val prefix = "₹ "
                val transformed = prefix + text.text
                val offsetMapping = object : OffsetMapping {
                    override fun originalToTransformed(offset: Int) = offset + prefix.length
                    override fun transformedToOriginal(offset: Int) = (offset - prefix.length).coerceAtLeast(0)
                }
                TransformedText(AnnotatedString(transformed), offsetMapping)
            },
            textStyle = TextStyle(color = Color.White, textAlign = TextAlign.Center, fontSize = 16.sp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Color(0xFFFFD700),
                focusedIndicatorColor = Color.White.copy(alpha = 0.6f),
                unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = {
                Text("Enter amount", color = Color.White.copy(alpha = 0.5f), textAlign = TextAlign.Center)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
