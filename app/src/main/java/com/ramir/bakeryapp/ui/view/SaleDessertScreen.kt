package com.ramir.bakeryapp.ui.view

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.domain.model.DessertListUiState
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.DessertViewModel
import com.ramir.bakeryapp.utils.Resource

@Composable
fun SaleDessertScreen(
    onAddIngredients: (id:String) -> Unit,
    desserViewModel: DessertViewModel = hiltViewModel()
){
    val dessertListState by desserViewModel.dessertListUiState.collectAsStateWithLifecycle(initialValue = DessertListUiState())
    Scaffold(
        topBar = {BakeryTopAppBar("Venta")}
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            when(val resource = dessertListState.dessertListUiState) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<List<Dessert>> -> {
                    if(resource.data.isNotEmpty()){
                        DessertListSale(resource.data, onAddIngredients)
                    }else{
                        Text(text = "No hay postres en este momento")
                    }
                }
            }
        }
    }
}

@Composable
private fun DessertListSale(
    dessertList: List<Dessert>,
    onAddIngredients:(id:String) -> Unit ){

    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        itemsIndexed(dessertList){ index, dessert ->
            DessertItem(Modifier.fillMaxWidth(),dessert, onAddIngredients = {onAddIngredients(dessert.id.toString())})
        }
    }
}

@Composable
private fun DessertItem(
    modifier: Modifier = Modifier,
    dessert: Dessert,
    onAddIngredients:() -> Unit){
    Card(modifier = Modifier.padding(8.dp).clickable{
        Log.i("ASDFG2", dessert.toString())
        onAddIngredients()
    }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = dessert.name)
            Text(text = dessert.description)
            Text(text = dessert.unitAvailable.toString())
            Text(text = dessert.price.toString())
        }
    }
}