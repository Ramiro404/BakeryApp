package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.viewmodel.AdditionalIngredientViewModel

@Composable
fun ListIngredientScreen(additionalIngredientViewModel: AdditionalIngredientViewModel = hiltViewModel()){
    val additionalIngredientList by additionalIngredientViewModel.additionalIngredientList.collectAsStateWithLifecycle(initialValue = emptyList())
    Scaffold(
        topBar = { BakeryTopAppBar("Mostrar Ingredientes") }
    ){ paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){
            if(additionalIngredientList.isNotEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(additionalIngredientList){ item ->
                        IngredientItem(item, Modifier.fillMaxWidth())
                    }
                }
            }else{

            }
        }
    }

}

@Composable
private fun IngredientItem(item: AdditionalIngredient, modifier: Modifier =  Modifier){
    Card(modifier) {
        Column {
            Text(text = item.name)
            Text(text = item.description)
            Text(text = item.unitAvailable.toString())
            Text(text = item.price.toString())

        }
    }
}