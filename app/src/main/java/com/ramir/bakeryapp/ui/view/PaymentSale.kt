package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.data.database.relations.CartItemDetails
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.CartViewModel
import com.ramir.bakeryapp.utils.Resource
import java.math.BigDecimal

@Composable
fun PaymentSaleScreen(cartViewModel: CartViewModel = hiltViewModel()){
    val cartList by cartViewModel.cart.collectAsStateWithLifecycle()
    Scaffold(
        topBar = { BakeryTopAppBar("Confirmar pago") }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)){
            when(val resource = cartList.cartList) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<List<CartItemDetails>> -> {
                    val totalToPay = remember(resource.data){
                        derivedStateOf {
                            resource.data.fold(BigDecimal.ZERO){ acc, item ->
                                acc.add(item.cartItem.total)
                            }
                        }
                    }

                    PaymentSaleContentent(resource.data, totalToPay.value)
                    Button(onClick = {}) {
                        Text(text = "Confirmar")
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentSaleContentent(cartList: List<CartItemDetails>,  totalToPay: BigDecimal){

    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(cartList){index, item ->
                Row {
                    Text(text = item.dessert.name, modifier = Modifier.weight(1f))
                    Text(text = item.dessert.price.toString(), modifier = Modifier.weight(1f))
                    Text(text = item.additionalIngredient.name, modifier = Modifier.weight(1f))
                    Text(text = item.additionalIngredient.price.toString(), modifier = Modifier.weight(1f))
                    Text(text = item.additionalIngredient.name, modifier = Modifier.weight(1f))
                    Text(text = item.cartItem.additionalIngredientQuantity.toString(), modifier = Modifier.weight(1f))
                    Text(text = item.cartItem.total.toString(), modifier = Modifier.weight(1f))

                }
            }
        }
        Text(text = "Total:: $$totalToPay")
    }
}