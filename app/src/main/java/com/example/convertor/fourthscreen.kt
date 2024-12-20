import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.convertor.R
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FourthScreen(navHostController: NavHostController) {
    var selectedUnit by remember { mutableStateOf("Weight") }

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
                    label = { Text("Weight") },
                    selected = selectedUnit == "Weight",
                    onClick = {
                        selectedUnit = "Weight"
                        navHostController.navigate("fourthscreen")
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
                        selectedTextColor = Color.Black,
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
            var inputUnit by remember { mutableStateOf("Kilograms") }
            var outputUnit by remember { mutableStateOf("Kilograms") }
            var iExpanded by remember { mutableStateOf(false) }
            var oExpanded by remember { mutableStateOf(false) }

            fun convertUnits() {
                val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
                val conversionFactor = getConversionFactor(inputUnit)
                val oConversionFactor = getConversionFactor(outputUnit)
                val result = (inputValueDouble * conversionFactor * 100 / oConversionFactor).roundToInt() / 100.0
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

                    Text("Weight Converter", style = MaterialTheme.typography.headlineLarge)

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
                                convertUnits()
                            },
                            expanded = iExpanded,
                            onExpandedChange = { iExpanded = it }
                        )


                        UnitDropdown(
                            selectedUnit = outputUnit,
                            onUnitSelected = { selectedUnit ->
                                outputUnit = selectedUnit
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
            listOf(
                "Kilograms", "Grams", "Milligrams",
                "Pounds", "Tons"
            ).forEach { unit ->
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


fun getConversionFactor(unit: String): Double {
    return when (unit) {
        "Kilograms" -> 1.0
        "Grams" -> 1000.0
        "Milligrams" -> 1_000_000.0
        "Pounds" -> 2.20462
        "Tons" -> 0.001
        else -> 1.0
    }
}