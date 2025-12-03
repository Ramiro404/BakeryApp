package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.ui.viewmodel.DessertViewModel

@Preview(showBackground = true)
@Composable
fun ListDessertScreen(
    dessertViewModel: DessertViewModel = viewModel()
){
    val dessertListState by dessertViewModel.dessertList.collectAsStateWithLifecycle(initialValue = emptyList())

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(dessertListState.isNotEmpty()){
            DessertList(dessertListState)
        }else{
            Text(text = "No hay articulos en este momento")
        }
    }
}

@Composable
private fun DessertList(dessertList: List<Dessert>){
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
            DessertItem(Modifier.fillMaxWidth(),dessert, index +1)
        }
    }
}

@Composable
private fun DessertItem(
    modifier: Modifier = Modifier,
    dessert: Dessert,
    index: Int){
    Row(modifier){
        Text(text = "$index", modifier  = Modifier.weight(1f))
        Text(text = dessert.name, modifier  = Modifier.weight(1f))
        Text(text = dessert.unitAvailable.toString(), modifier  = Modifier.weight(1f))
        Text(text = dessert.description, modifier  = Modifier.weight(1f))
        Text(text = dessert.price.toString(), modifier  = Modifier.weight(1f))
    }
    HorizontalDivider(thickness = 2.dp)
}