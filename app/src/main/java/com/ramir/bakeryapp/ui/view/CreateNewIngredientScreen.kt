package com.ramir.bakeryapp.ui.view

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.domain.model.SaveUiState
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.DialogSuccess
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.AdditionalIngredientViewModel
import com.ramir.bakeryapp.utils.SaveResource
import java.math.BigDecimal

@Composable
fun CreateNewIngredientScreen(additionalIngredientViewModel: AdditionalIngredientViewModel = hiltViewModel()) {
    val nameState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }
    val unitAvailableState = remember { mutableIntStateOf(0) }
    val priceState = remember { mutableStateOf(BigDecimal.ZERO) }

    val saveUiState by additionalIngredientViewModel.saveUiState.collectAsStateWithLifecycle(
        SaveUiState())

    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { BakeryTopAppBar("Crear Nuevo Ingrediente") }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
                        if (value.isDigitsOnly()) unitAvailableState.intValue = value.toInt() else 0
                    },
                    label = { Text(text = "Unidades disponibles") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

                )

                OutlinedTextField(
                    value = priceState.value.toString(),
                    onValueChange = {
                        if (it.isDigitsOnly() || it.equals(".")) priceState.value =
                            it.toBigDecimal() else 0
                    },
                    label = { Text(text = "Precio unitario del postre") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        additionalIngredientViewModel.postIngredient(
                            nameState.value,
                            descriptionState.value,
                            unitAvailableState.value,
                            priceState.value
                        )
                        showDialog.value = true
                    }
                ) {
                    Text(text = "Guardar este nuevo postre")
                }
            }


        }
        when (val resource = saveUiState.saveUiResource) {
            is SaveResource.Error -> DialogError(
                { showDialog.value = false },
                "Ocurrio un error",
                showDialog.value
            )

            SaveResource.Loading -> LoadingProgress()
            SaveResource.Success -> DialogSuccess(
                { showDialog.value = false },
                "Guadardo con exito!",
                showDialog.value
            )
        }

    }
}