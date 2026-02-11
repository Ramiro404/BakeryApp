package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
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


@Composable
fun RemoveAdditionalIngredientScreen(
    additionalIngredientViewModel: AdditionalIngredientViewModel = hiltViewModel()
){
    val ingredientList by additionalIngredientViewModel.additionalIngredientListUiState.collectAsStateWithLifecycle(initialValue = AdditionalIngredientListUiState())
    val saveUiState by additionalIngredientViewModel.saveUiState.collectAsStateWithLifecycle(initialValue = SaveUiState())
    val showLoadingProgress = remember { mutableStateOf(false) }
    val openAlertDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { BakeryTopAppBar("Mostrar Ingredientes") }
    ){ paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){
            when(val resource = ingredientList.additionalIngredientList) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<List<AdditionalIngredient>> -> {
                    if(resource.data.isNotEmpty()){
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(resource.data){ item ->
                                IngredientItem(
                                    item,
                                    Modifier.fillMaxWidth(),
                                    onRemoveDessert = {
                                        additionalIngredientViewModel.deleteIngredient(item.id)
                                    },
                                    openAlertDialog = openAlertDialog.value,
                                    setOpenDialog = {  openAlertDialog.value = it})
                            }
                        }
                    }else{
                        DialogError({}, "No hay Ingredientes")
                    }
                }
            }

            when(val state = saveUiState.saveUiResource){
                SaveResource.Idle -> {}
                is SaveResource.Error -> {
                    DialogError(
                        onDismissRequest = {
                            openAlertDialog.value = false
                            additionalIngredientViewModel.resetSaveState()
                                           },
                        message = "No se pudo eliminar el ingrediente, intentelo mas tarde",
                        showDialog = openAlertDialog.value
                    )
                }
                SaveResource.Loading -> {
                    LoadingProgress(true)
                }
                SaveResource.Success -> {
                    DialogSuccess(
                        onDismissRequest = {
                            openAlertDialog.value = false
                            additionalIngredientViewModel.resetSaveState()
                                           },
                        message = "Se elimino correctamente",
                        showDialog = openAlertDialog.value
                    )
                }
            }
        }
    }

}


@Composable
private fun IngredientItem(
    item: AdditionalIngredient,
    modifier: Modifier =  Modifier,
    onRemoveDessert: () -> Unit,
    openAlertDialog: Boolean,
    setOpenDialog: (value: Boolean) -> Unit = {}){



    Card(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = item.imagePath,
                contentDescription = "Imagen seleccionada",
                modifier = Modifier.size(200.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(text = item.name)
            Text(text = item.description)
            Text(text = "Unidades: ${item.unitAvailable}")
            Text(text = "Precio: $ ${item.price}")

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {setOpenDialog(true)}) {
                    Text("Eliminar")
                }
            }

        }
    }
    if(openAlertDialog){
        AlertRemoveIngredient(
            onDismissRequest = { setOpenDialog(false) },
            onConfirmation = {
                onRemoveDessert()
            },
            ingredientName = item.name,
        )
    }

}

@Composable
private fun AlertRemoveIngredient(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    ingredientName: String,
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painterResource(id=R.drawable.ic_info), contentDescription = "Info", tint = Color.Red)
                Text(text = "Confirmar eliminacion para este ingrediente: ${ingredientName}", fontSize = 24.sp)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onConfirmation) {
                    Text(text = "Confirmar")
                }
                Button(onClick = onDismissRequest) {
                    Text(text = "Cerrar")
                }
            }
        }
    }
}