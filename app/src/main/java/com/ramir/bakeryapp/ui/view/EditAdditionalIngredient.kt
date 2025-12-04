package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
                    IngredientItemEdit(item, Modifier.fillMaxWidth())
                }
            }
        }else{

        }
    }
}

@Composable
fun IngredientItemEdit(item: AdditionalIngredient, modifier: Modifier =  Modifier){
    Card(modifier) {
        Column {
            Text(text = item.name)
            Text(text = item.description)
            Text(text = item.unitAvailable.toString())
            Text(text = item.price.toString())
            Button(
                onClick = {}
            ) {
                Text(text = "Agregar/Restar iNVERNTARIO")
            }
        }
    }
}