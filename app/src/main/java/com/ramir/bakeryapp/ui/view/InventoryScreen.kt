package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramir.bakeryapp.R
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.NavButtonCard

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    navigateToCreateNewDessert: () -> Unit = {},
    navigateToEditDessert: () -> Unit = {},
    navigateToListIngredients: () -> Unit = {},
    navigateToEditIngredients: () -> Unit = {},
    navigateToCreateIngredients: () -> Unit = {},
    navigateToListDesserts: () -> Unit = {}
){
    Scaffold(
        topBar = { BakeryTopAppBar("Inventario") }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)){
            InventoryMenu(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                navigateToCreateNewDessert,
                navigateToEditDessert,
                navigateToListIngredients,
                navigateToEditIngredients,
                navigateToCreateIngredients,
                navigateToListDesserts
            )
        }
    }
}


@Composable
private fun InventoryMenu(
    modifier: Modifier = Modifier,
    navigateToCreateNewDessert: () -> Unit,
    navigateToEditDessert: () ->  Unit,
    navigateToListIngredients: () -> Unit,
    navigateToEditIngredients: () -> Unit,
    navigateToCreateIngredients: () -> Unit,
    navigateToListDesserts: () -> Unit
    ){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        NavButtonCard(
            onClick = navigateToCreateNewDessert,
            icon = R.drawable.ic_add,
            title = stringResource(R.string.add_new_dessert)
        )
        Spacer(modifier = Modifier.height(12.dp))
        NavButtonCard(
            onClick = navigateToListDesserts,
            icon = R.drawable.ic_cake,
            title = stringResource(R.string.list_desserts)
        )
        Spacer(modifier = Modifier.height(12.dp))

        NavButtonCard(
            onClick = navigateToEditDessert,
            icon = R.drawable.ic_edit,
            title = "Editar Ingrediente"
        )
        Spacer(modifier = Modifier.height(12.dp))

        NavButtonCard(
            onClick = navigateToListIngredients,
            icon = R.drawable.ic_eye_sales,
            title = "Ver Ingredientes"
        )
        Spacer(modifier = Modifier.height(12.dp))
        NavButtonCard(
            onClick = navigateToEditIngredients,
            icon = R.drawable.ic_edit,
            title ="Agregar/Restar Ingredientes"
        )
        Spacer(modifier = Modifier.height(12.dp))
        NavButtonCard(
            onClick = navigateToCreateIngredients,
            icon = R.drawable.ic_add,
            title = "Crear Nuevo Ingrediente"
        )
    }
}


