package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.domain.model.DessertListUiState
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.DessertViewModel
import com.ramir.bakeryapp.utils.Resource

@Preview(showBackground = true)
@Composable
fun ListDessertScreen(
    dessertViewModel: DessertViewModel = hiltViewModel()
){
    val dessertListState by dessertViewModel.dessertListUiState.collectAsStateWithLifecycle(initialValue = DessertListUiState())
    Scaffold(
        topBar = { BakeryTopAppBar("Mostrar Postres") }
    ){ paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            when(val resource = dessertListState.dessertListUiState) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<List<Dessert>> -> {
                    if(resource.data.isNotEmpty()){
                        DessertList(resource.data)
                    }else{
                        Text(text = "No hay articulos en este momento", fontSize = 48.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun DessertList(dessertList: List<Dessert>){
    Column {
        Row(Modifier.fillMaxWidth()){
            Text(text = "#", modifier  = Modifier.weight(1f))
            Text(text = "Nombre", modifier  = Modifier.weight(1f))
            Text(text = "Unidades", modifier  = Modifier.weight(1f), overflow = TextOverflow.Ellipsis)
            Text(text = "Descripcion", modifier  = Modifier.weight(2f))
            Text(text = "Precio", modifier  = Modifier.weight(1f), overflow = TextOverflow.Ellipsis)
        }
        HorizontalDivider(thickness = 2.dp)
        LazyColumn() {
            itemsIndexed(dessertList){ index, dessert ->
                DessertItem(Modifier.fillMaxWidth(),dessert, index +1)
            }
        }
    }
}

@Composable
private fun DessertItem(
    modifier: Modifier = Modifier,
    dessert: Dessert,
    index: Int){
    Row(modifier){
        AsyncImage(
            model = dessert.imagePath, // Si ya se guardó, usa el path; si no, la URI temporal
            contentDescription = "Imagen seleccionada",
            modifier = Modifier.size(200.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(text = "$index", modifier  = Modifier.weight(1f))
        Text(text = dessert.name, modifier  = Modifier.weight(1f))
        Text(text = dessert.unitAvailable.toString(), modifier  = Modifier.weight(1f))
        Text(text = dessert.description, modifier  = Modifier.weight(1f))
        Text(text = "$ ${dessert.price.toString()}", modifier  = Modifier.weight(1f))
    }
    HorizontalDivider(thickness = 2.dp)
}