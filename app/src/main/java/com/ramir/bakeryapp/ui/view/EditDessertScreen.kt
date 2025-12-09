package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.domain.model.DessertListUiState
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.DessertViewModel
import com.ramir.bakeryapp.utils.Resource

@Composable
fun EditDessertScreen(
    dessertViewModel: DessertViewModel = hiltViewModel(),
    onEditDessert: (id:String)  -> Unit){
    val dessertListState by dessertViewModel.dessertListUiState.collectAsStateWithLifecycle(initialValue = DessertListUiState())

    Scaffold(
        topBar = { BakeryTopAppBar("Editar Postre") }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            when(val resource = dessertListState.dessertListUiState) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<List<Dessert>> -> {
                    if(resource.data.isNotEmpty()){
                        DessertList(resource.data,onEditDessert)
                    }else{
                        Text(text = "No hay articulos en este momento")
                    }
                }
            }

        }
    }

}

@Composable
private fun DessertList(dessertList: List<Dessert>, onEditDessert: (id:String)  -> Unit){
    LazyColumn() {
        items(dessertList){ dessert ->
            DessertItem(Modifier.fillMaxWidth().clickable{ onEditDessert(dessert.id.toString()) },dessert)
        }
    }
}

@Composable
private fun DessertItem(
    modifier: Modifier = Modifier,
    dessert: Dessert){
    Row(modifier){
        Text(text = dessert.name)
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = "Siguiente"
        )
    }
    HorizontalDivider(thickness = 2.dp)
}