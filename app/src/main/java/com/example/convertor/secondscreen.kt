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
import androidx.navigation.NavHostController
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SecondScreen(navHostController: NavHostController) {
    var selectedUnit by remember { mutableStateOf("Unit") }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
            ) {
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
                    label = { Text("Length") },
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
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var inputValue by remember { mutableStateOf("") }
            var outputValue by remember { mutableStateOf("") }
            var inputUnit by remember { mutableStateOf("Meters") }
            var outputUnit by remember { mutableStateOf("Meters") }
            var iExpanded by remember { mutableStateOf(false) }
            var oExpanded by remember { mutableStateOf(false) }
            var conversionFactor = remember { mutableStateOf(1.00) }
            var oConversionFactor = remember { mutableStateOf(1.00) }

            fun convertUnits() {
                val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
                val result = (inputValueDouble * conversionFactor.value * 100 / oConversionFactor.value).roundToInt() / 100.0
                outputValue = result.toString()
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {



                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Length Converter", style = MaterialTheme.typography.headlineLarge)

                    Spacer(modifier = Modifier.height(16.dp))


                    OutlinedTextField(
                        value = inputValue,
                        onValueChange = {
                            inputValue = it
                            convertUnits()
                        },
                        label = { Text("Enter Value") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        UnitDropdown(
                            selectedUnit = inputUnit,
                            onUnitSelected = { selectedUnit ->
                                inputUnit = selectedUnit
                                conversionFactor.value = when (selectedUnit) {
                                    "Meters" -> 1.0
                                    "Centimeters" -> 0.01
                                    "Feet" -> 0.3048
                                    "Millimeters" -> 0.001
                                    "Inches" -> 0.0254
                                    "Yards" -> 0.9144
                                    else -> 1.0
                                }
                                convertUnits()
                            },
                            expanded = iExpanded,
                            onExpandedChange = { iExpanded = it }
                        )


                        UnitDropdown(
                            selectedUnit = outputUnit,
                            onUnitSelected = { selectedUnit ->
                                outputUnit = selectedUnit
                                oConversionFactor.value = when (selectedUnit) {
                                    "Meters" -> 1.0
                                    "Centimeters" -> 0.01
                                    "Feet" -> 0.3048
                                    "Millimeters" -> 0.001
                                    "Inches" -> 0.0254
                                    "Yards" -> 0.9144
                                    else -> 1.0
                                }
                                convertUnits()
                            },
                            expanded = oExpanded,
                            onExpandedChange = { oExpanded = it }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    Text("Result = $outputValue $outputUnit", style = MaterialTheme.typography.headlineSmall)
                }
            }
        }
    }
}

@Composable
fun UnitDropdown(
    selectedUnit: String,
    onUnitSelected: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    Box {
        Button(onClick = { onExpandedChange(true) }) {
            Text(text = selectedUnit)
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            listOf("Meters", "Centimeters", "Feet", "Millimeters", "Inches", "Yards").forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = {
                        onUnitSelected(unit)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}
