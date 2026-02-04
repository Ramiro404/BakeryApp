package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.R
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import com.ramir.bakeryapp.domain.model.AdditionalIngredientListUiState
import com.ramir.bakeryapp.domain.model.SaveUiState
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.DialogSuccess
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.AdditionalIngredientViewModel
import com.ramir.bakeryapp.utils.Resource
import com.ramir.bakeryapp.utils.SaveResource
import java.math.BigDecimal

@Composable
fun EditAdditionalIngredient(additionalIngredientViewModel: AdditionalIngredientViewModel = hiltViewModel()) {
    val additionalIngredientList by additionalIngredientViewModel.additionalIngredientListUiState.collectAsStateWithLifecycle(
        AdditionalIngredientListUiState()
    )
    val saveUiState by additionalIngredientViewModel.saveUiState.collectAsStateWithLifecycle(
        SaveUiState()
    )
    val showIngredientDialog = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val quantityAvailable = remember { mutableStateOf(value = 0) }
    val selectedIngredient = remember {
        mutableStateOf(
            value = AdditionalIngredient(
                0, "", "", 0,
                BigDecimal.ZERO
            )
        )
    }
    Scaffold(
        topBar = { BakeryTopAppBar("Editar Ingrediente") }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val resource = additionalIngredientList.additionalIngredientList) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<List<AdditionalIngredient>> -> {
                    if (resource.data.isNotEmpty()) {
                        LazyVerticalGrid(
                            modifier = Modifier.padding(8.dp),
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(resource.data) { item ->
                                IngredientItemEdit(
                                    item = item,
                                    modifier = Modifier.fillMaxWidth(),
                                    onOpenDialog = {
                                        quantityAvailable.value = it.unitAvailable
                                        selectedIngredient.value = it
                                        showIngredientDialog.value = true
                                    }
                                )
                            }
                        }


                    } else {
                        DialogError({}, "No hay Ingredientes")
                    }


                }
            }
        }
        if (showIngredientDialog.value) {
            AddRemoveIngredientDialog(
                quantity = quantityAvailable.value,
                onAdd = { quantityAvailable.value++ },
                onSubstract = { quantityAvailable.value-- },
                onDismissRequest = { showIngredientDialog.value = false },
                onSubmit = {
                    quantityAvailable.value = it
                    additionalIngredientViewModel.updateIngredient(
                        selectedIngredient.value.id,
                        selectedIngredient.value.name,
                        selectedIngredient.value.description,
                        unitAvailable = it,
                        selectedIngredient.value.price
                    )
                    showDialog.value = true
                }
            )
            when (val resource = saveUiState.saveUiResource) {
                is SaveResource.Error -> DialogError(
                    { showDialog.value = false },
                    "Ocurrio un error",
                    showDialog.value
                )

                SaveResource.Loading -> LoadingProgress()
                SaveResource.Success -> DialogSuccess({
                    showDialog.value = false
                    showIngredientDialog.value = false
                }, "Se guardo con exito!", showDialog.value)
            }

        }
    }

}

@Composable
fun IngredientItemEdit(
    item: AdditionalIngredient,
    modifier: Modifier = Modifier,
    onOpenDialog: (selectedItem: AdditionalIngredient) -> Unit,
) {


    Card(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = item.name)
            Text(text = item.description)
            Text(text = "Unidades ${item.unitAvailable}")
            Text(text = "Precio $ ${item.price}")
            Button(
                onClick = { onOpenDialog(item) }
            ) {
                Text(text = "Agregar / Restar Inventario")
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddRemoveIngredientDialog(
    quantity: Int,
    onAdd: () -> Unit,
    onSubstract: () -> Unit,
    onDismissRequest: () -> Unit,
    onSubmit: (newQuantity: Int) -> Unit
) {

    Dialog(
        onDismissRequest = { onDismissRequest() },
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),

            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    IconButton(
                        onClick = { onSubstract() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_substract_check),
                            contentDescription = "Sumar"
                        )
                    }

                    Text(text = "$quantity")

                    IconButton(
                        onClick = { onAdd() }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "Restar"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = { onSubmit(quantity) }
                ) {
                    Text(text = "Confirmar")
                }

                OutlinedButton(
                    onClick = onDismissRequest
                ) {
                    Text(text = "Cancelar")
                }
            }
        }
    }
}