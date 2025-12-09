package com.ramir.bakeryapp.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.DessertViewModel
import com.ramir.bakeryapp.utils.Resource
import java.math.BigDecimal

@Preview(showBackground = true)
@Composable
fun EditDessertFormScreen(
    dessertViewModel: DessertViewModel = hiltViewModel(),
    dessertId: String = ""
) {

    val dessertState by dessertViewModel.dessertUiState.collectAsStateWithLifecycle(initialValue = null)
    LaunchedEffect(Unit) {
        dessertViewModel.getDessertById(dessertId.toInt())
    }

    Scaffold(
        topBar = { BakeryTopAppBar("Inventario") }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            when (val resource = dessertState?.dessertResource) {
                is Resource.Error -> DialogError({}, "Algo salio mal") //show modal error
                Resource.Loading -> LoadingProgress() // show spinner
                is Resource.Success<Dessert> -> EditDessertFormContent(
                    resource.data,
                    {
                        dessertViewModel.saveNewDessert(
                            it.name,
                            it.description,
                            it.unitAvailable,
                            it.price
                        )
                    }) //show ui
                else -> { DialogError({}, "No se encontro el postre")} //show modal error
            }
        }
    }
}

@Composable
private fun EditDessertFormContent(
    dessertState: Dessert,
    onSubmit: (dessert: Dessert) -> Unit
) {
    val nameState = remember { mutableStateOf(dessertState.name) }
    val descriptionState = remember { mutableStateOf(dessertState.description) }
    val unitAvailableState = remember { mutableIntStateOf(dessertState.unitAvailable) }
    val priceState = remember { mutableStateOf(dessertState.price) }


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
                val updatedDessert = Dessert(
                    dessertState.id,
                    nameState.value,
                    descriptionState.value,
                    unitAvailableState.value,
                    priceState.value
                )
                onSubmit(updatedDessert)
            }
        ) {
            Text(text = "Guardar esta informacion")
        }
    }

}
