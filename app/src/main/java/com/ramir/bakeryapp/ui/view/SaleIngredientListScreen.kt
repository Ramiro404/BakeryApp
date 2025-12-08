package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import com.ramir.bakeryapp.domain.model.IngredientCart
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.viewmodel.AdditionalIngredientViewModel
import com.ramir.bakeryapp.ui.viewmodel.CartViewModel
import kotlin.collections.mutableListOf

@Composable
fun SaleIngredientListSale(
    dessertId:Int,
    additionalIngredientViewModel: AdditionalIngredientViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    navigateToSaleDessertList: () -> Unit
    ){
    val additionalIngredientList by additionalIngredientViewModel.additionalIngredientList.collectAsStateWithLifecycle(initialValue = emptyList())
    val cartList by cartViewModel.cart.collectAsStateWithLifecycle(initialValue = emptyList())
    var showDialog = remember{ mutableStateOf(false) }
    var quantity = remember { mutableStateOf(0) }
    var ingredientCartList = remember { mutableListOf<IngredientCart>() }

    Scaffold(
        topBar = {BakeryTopAppBar("Selecciona los ingredientes")}
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){
            if(additionalIngredientList.isNotEmpty()){
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2)
                ) {
                    items(additionalIngredientList){ item ->
                        IngredientItem(
                            item,
                            Modifier.fillMaxWidth().clickable{ showDialog.value = true },
                            showDialog.value,
                            quantity= quantity.value,
                            onAdd = { quantity.value++ },
                            onSubstract = { quantity.value-- },
                            onSubmit = {
                                val ingredientCart = IngredientCart(item, quantity.value)
                                ingredientCartList.add(ingredientCart)
                            },
                            onDismissRequest = { showDialog.value = false })
                    }
                }
                Row(modifier = Modifier.fillMaxWidth()){
                    Button(
                        onClick = {
                            navigateToSaleDessertList()
                        }) {
                        Text(text = "Agregar otro postre")
                    }
                }

            }else{

            }
        }
    }
}

@Composable
private fun IngredientItem(
    item: AdditionalIngredient,
    modifier: Modifier =  Modifier,
    showDialog: Boolean,
    quantity:Int,
    onAdd: () -> Unit,
    onSubstract: () -> Unit,
    onSubmit: () -> Unit,
    onDismissRequest: () -> Unit){
    Card(modifier) {
        Column {
            Text(text = item.name)
            Text(text = item.description)
            Text(text = item.unitAvailable.toString())
            Text(text = item.price.toString())

        }
    }

    if(showDialog){
        AddRemoveIngredientDialog(
            quantity,
            onAdd,
            onSubstract,
            onDismissRequest,
            onSubmit
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddRemoveIngredientDialog(
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