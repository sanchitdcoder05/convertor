package com.example.convertor

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ThirdScreen(navHostController: NavHostController) {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputCurrency by remember { mutableStateOf("INR") }
    var outputCurrency by remember { mutableStateOf("INR") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    var conversionFactor = remember { mutableStateOf(1.00) }


    fun convertCurrency() {
        if (inputCurrency == "INR") {
            when (outputCurrency) {
                "USD" -> conversionFactor.value = 0.0119
                "Pounds" -> conversionFactor.value = 0.0092
                "Euros" -> conversionFactor.value = 0.011
                else -> conversionFactor.value = 1.0
            }
        }
        if (inputCurrency == "USD") {
            when (outputCurrency) {
                "INR" -> conversionFactor.value = 84.1003
                "Pounds" -> conversionFactor.value = 0.7714
                "Euros" -> conversionFactor.value = 0.9261
                else -> conversionFactor.value = 1.0
            }
        }
        if (inputCurrency == "Pounds") {
            when (outputCurrency) {
                "INR" -> conversionFactor.value = 109.0211
                "USD" -> conversionFactor.value = 1.2963
                "Euros" -> conversionFactor.value = 1.2006
                else -> conversionFactor.value = 1.0
            }
        }
        if (inputCurrency == "Euros") {
            when (outputCurrency) {
                "INR" -> conversionFactor.value = 90.8064
                "USD" -> conversionFactor.value = 1.0797
                "Pounds" -> conversionFactor.value = 0.8329
                else -> conversionFactor.value = 1.0
            }
        }

        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        outputValue = (inputValueDouble * conversionFactor.value).toString()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center), // Centering the content
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Currency Converter", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Button(onClick = { iExpanded = true }) {
                        Text(text = inputCurrency)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                    }
                    DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("INR") },
                            onClick = {
                                iExpanded = false
                                inputCurrency = "INR"
                                convertCurrency()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("USD") },
                            onClick = {
                                iExpanded = false
                                inputCurrency = "USD"
                                convertCurrency()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Pounds") },
                            onClick = {
                                iExpanded = false
                                inputCurrency = "Pounds"
                                convertCurrency()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Euros") },
                            onClick = {
                                iExpanded = false
                                inputCurrency = "Euros"
                                convertCurrency()
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedTextField(
                    value = inputValue,
                    onValueChange = {
                        inputValue = it
                        convertCurrency()
                    },
                    label = { Text("Enter Amount") }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            Text("To")
            Spacer(modifier = Modifier.height(30.dp))


            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Button(onClick = { oExpanded = true }) {
                        Text(text = outputCurrency)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                    }
                    DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                        DropdownMenuItem(
                            text = { Text("INR") },
                            onClick = {
                                oExpanded = false
                                outputCurrency = "INR"
                                convertCurrency()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("USD") },
                            onClick = {
                                oExpanded = false
                                outputCurrency = "USD"
                                convertCurrency()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Pounds") },
                            onClick = {
                                oExpanded = false
                                outputCurrency = "Pounds"
                                convertCurrency()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Euros") },
                            onClick = {
                                oExpanded = false
                                outputCurrency = "Euros"
                                convertCurrency()
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = outputValue,
                    onValueChange = {
                        inputValue = it
                        convertCurrency()
                                    },
                    label = { Text("Enter Amount") },
                )
            }
        }



        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text("Last Updated on $current IST")
            Spacer(modifier = Modifier.height(12.dp))
        }


        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.Red)
        ) {
            var selectedUnit by remember { mutableStateOf("Currency") }
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.unit),
                        contentDescription = "Unit"
                    )
                },
                label = { Text("Unit") },
                selected = selectedUnit == "Unit",
                onClick = {
                    selectedUnit = "Unit"
                    navHostController.navigate("secondscreen")
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color(0xFF87CEEB),
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color(0xFF87CEEB)
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.unit),
                        contentDescription = "Weight"
                    )
                },
                label = { Text("Weight") },
                selected = selectedUnit == "Weight",
                onClick = {
                    selectedUnit = "Weight"
                    navHostController.navigate("fourthscreen")
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color(0xFF87CEEB)
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.unit),
                        contentDescription = "Currency"
                    )
                },
                label = { Text("Currency") },
                selected = selectedUnit == "Currency",
                onClick = {
                    selectedUnit = "Currency"
                    navHostController.navigate("thirdscreen")
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color(0xFF87CEEB),
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color(0xFF87CEEB)
                )
            )
        }
    }
}
