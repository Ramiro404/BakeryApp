package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramir.bakeryapp.ui.viewmodel.DessertViewModel
import java.math.BigDecimal

@Preview(showBackground = true)
@Composable
fun EditDessertFormScreen(
    dessertViewModel: DessertViewModel = viewModel(),
    dessertId:String =""){

    val dessertState by dessertViewModel.dessert.collectAsStateWithLifecycle(initialValue = null)
    dessertViewModel.getDessertById(dessertId.toInt())

    val nameState = remember { mutableStateOf(dessertState?.name ?: "") }
    val  descriptionState = remember { mutableStateOf(dessertState?.description ?: "") }
    val  unitAvailableState = remember { mutableIntStateOf(dessertState?.unitAvailable ?: 0) }
    val  priceState = remember { mutableStateOf(dessertState?.price ?: BigDecimal.ZERO) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = nameState.value,
                onValueChange = { nameState.value = it },
                label = { Text(text = "Nombre del postre") }
            )

            OutlinedTextField(
                value = descriptionState.value,
                onValueChange = { descriptionState.value = it },
                label = { Text(text = "Descripcion del postre") }
            )

            OutlinedTextField(
                value = unitAvailableState.intValue.toString(),
                onValueChange = { value: String ->
                    if (value.isDigitsOnly()) unitAvailableState.intValue = value.toInt()
                },
                label = { Text(text = "Unidades disponibles") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )

            OutlinedTextField(
                value = priceState.value.toString(),
                onValueChange = { if (it.isDigitsOnly()) priceState.value = it.toBigDecimal() },
                label = { Text(text = "Precio unitario del postre") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    dessertViewModel.saveNewDessert(
                        nameState.value,
                        descriptionState.value,
                        unitAvailableState.value,
                        priceState.value
                    )
                }
            ) {
                Text(text = "Guardar esta informacion")
            }
        }

    }}