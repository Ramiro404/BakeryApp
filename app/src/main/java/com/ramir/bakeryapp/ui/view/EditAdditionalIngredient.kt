package com.ramir.bakeryapp.ui.view

import android.app.AlertDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.R
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import com.ramir.bakeryapp.ui.viewmodel.AdditionalIngredientViewModel

@Composable
fun EditAdditionalIngredient(additionalIngredientViewModel: AdditionalIngredientViewModel = hiltViewModel()){
    val additionalIngredientList by additionalIngredientViewModel.additionalIngredientList.collectAsStateWithLifecycle(initialValue = emptyList())

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        if(additionalIngredientList.isNotEmpty()){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(additionalIngredientList){ item ->
                    IngredientItemEdit(
                        item,
                        Modifier.fillMaxWidth(),
                        { quantityAvailable ->

                            additionalIngredientViewModel.updateIngredient(
                                item.id, item.name, item.description, quantityAvailable, item.price
                            )
                        })
                }
            }
        }else{

        }
    }
}

@Composable
fun IngredientItemEdit(
    item: AdditionalIngredient,
    modifier: Modifier =  Modifier,
    onSubmit: (quantityAvailable:Int) -> Unit){
    val openDialog = remember { mutableStateOf(false) }
    val quantityAvailable = remember { mutableStateOf(value = item.unitAvailable) }
    Card(modifier) {
        Column {
            Text(text = item.name)
            Text(text = item.description)
            Text(text = item.unitAvailable.toString())
            Text(text = item.price.toString())
            Button(
                onClick = {
                    openDialog.value = true
                }
            ) {
                Text(text = "Agregar/Restar iNVERNTARIO")
            }
        }
    }

    if(openDialog.value){
        AddRemoveIngredientDialog(
            quantity = quantityAvailable.value,
            onAdd = { quantityAvailable.value++ },
            onSubstract = { quantityAvailable.value-- },
            onDismissRequest = { openDialog.value = false},
            onSubmit = {onSubmit(quantityAvailable.value)}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRemoveIngredientDialog(
    quantity: Int,
    onAdd: () -> Unit,
    onSubstract: () -> Unit,
    onDismissRequest: () -> Unit,
    onSubmit: () -> Unit){

    BasicAlertDialog(
        onDismissRequest = {onDismissRequest()},
    ) {
        Row() {
            IconButton(
                onClick = { onAdd() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Sumar"
                )
            }

            Text(text = "$quantity")

            IconButton(
                onClick = { onSubstract() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Restar"
                )
            }
        }
    }
    Button(
        onClick = { onSubmit() }
    ) {
        Text(text = "Confirmar")
    }
}