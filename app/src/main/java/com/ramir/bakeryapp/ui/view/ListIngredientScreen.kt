package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import com.ramir.bakeryapp.domain.model.AdditionalIngredientListUiState
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.AdditionalIngredientViewModel
import com.ramir.bakeryapp.utils.Resource

@Composable
fun ListIngredientScreen(additionalIngredientViewModel: AdditionalIngredientViewModel = hiltViewModel()){
    val additionalIngredientList by additionalIngredientViewModel.additionalIngredientListUiState.collectAsStateWithLifecycle(initialValue = AdditionalIngredientListUiState())
    Scaffold(
        topBar = { BakeryTopAppBar("Mostrar Ingredientes") }
    ){ paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){
            when(val resource = additionalIngredientList.additionalIngredientList) {
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
                                IngredientItem(item, Modifier.fillMaxWidth())
                            }
                        }
                    }else{
                        DialogError({}, "No hay Ingredientes")
                    }
                }
            }

        }
    }

}

@Composable
private fun IngredientItem(item: AdditionalIngredient, modifier: Modifier =  Modifier){
    Card(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = item.imagePath, // Si ya se guardó, usa el path; si no, la URI temporal
                contentDescription = "Imagen seleccionada",
                modifier = Modifier.size(200.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(text = item.name)
            Text(text = item.description)
            Text(text = "Unidades: ${item.unitAvailable.toString()}")
            Text(text = "Precio: $ ${item.price.toString()}")

        }
    }
}