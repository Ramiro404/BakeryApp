package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.viewmodel.DessertViewModel

@Composable
fun SaleDessertScreen(
    onAddIngredients: (id:Int) -> Unit,
    desserViewModel: DessertViewModel = hiltViewModel()
){
    val dessertListState by desserViewModel.dessertListUiState.collectAsStateWithLifecycle(initialValue = emptyList())
    Scaffold(
        topBar = {BakeryTopAppBar("Venta")}
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            if(dessertListState.isNotEmpty()){
                DessertList(dessertListState, onAddIngredients)
            }else{
                Text(text = "No hay postres en este momento")
            }
        }
    }

}

@Composable
private fun DessertList(
    dessertList: List<Dessert>,
    onAddIngredients:(id:Int) -> Unit ){
    Row(Modifier.fillMaxWidth()){
        Text(text = "#", modifier  = Modifier.weight(1f))
        Text(text = "Nombre", modifier  = Modifier.weight(1f))
        Text(text = "Unidades disponibles", modifier  = Modifier.weight(1f))
        Text(text = "Descripcion", modifier  = Modifier.weight(1f))
        Text(text = "Precio unitario", modifier  = Modifier.weight(1f))
    }
    HorizontalDivider(thickness = 2.dp)
    LazyColumn() {
        itemsIndexed(dessertList){ index, dessert ->
            DessertItem(Modifier.fillMaxWidth().clickable{
                //navigate to ingredient with id Dessert
                onAddIngredients(dessert.id)
            },dessert, index +1)
        }
    }
}

@Composable
private fun DessertItem(
    modifier: Modifier = Modifier,
    dessert: Dessert,
    index: Int){
    Row(modifier){
        Column {
            Text(text = dessert.name, modifier  = Modifier.weight(1f))
            Text(text = dessert.description, modifier  = Modifier.weight(1f))
            Text(text = dessert.unitAvailable.toString(), modifier  = Modifier.weight(1f))
        }

        Text(text = dessert.price.toString(), modifier  = Modifier.weight(1f))
    }
    HorizontalDivider(thickness = 2.dp)
}