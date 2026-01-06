package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.R
import com.ramir.bakeryapp.data.database.relations.CartItemDetails
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import com.ramir.bakeryapp.domain.model.AdditionalIngredientListUiState
import com.ramir.bakeryapp.domain.model.AdditionalIngredientWithQuantity
import com.ramir.bakeryapp.domain.model.AdditionalIngredientWithQuantityListUiState
import com.ramir.bakeryapp.domain.model.CartListUiState
import com.ramir.bakeryapp.domain.model.toDomain
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.AdditionalIngredientViewModel
import com.ramir.bakeryapp.ui.viewmodel.CartViewModel
import com.ramir.bakeryapp.utils.Resource
import java.util.UUID

@Preview(showBackground = true)
@Composable
fun SaleIngredientListSale(
    dessertId: String = "0",
    additionalIngredientViewModel: AdditionalIngredientViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    navigateToSaleDessertList: () -> Unit = {},
    navigateToPayment: () -> Unit = {}
) {
    val additionalIngredientWithQuantityList by additionalIngredientViewModel.additionalIngredientWithQuantityListUiState.collectAsStateWithLifecycle(
        initialValue = AdditionalIngredientWithQuantityListUiState()
    )
    val cartListUiState by cartViewModel.cart.collectAsStateWithLifecycle(initialValue = CartListUiState())
    var showDialog = remember { mutableStateOf(false) }
    var quantity = remember { mutableStateOf(0) }
    val itemNumber = UUID.randomUUID().toString()


    Scaffold(
        topBar = { BakeryTopAppBar("Selecciona los ingredientes") }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val resource = additionalIngredientWithQuantityList.ingredientResource) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<List<AdditionalIngredientWithQuantity>> -> {
                    if (resource.data.isNotEmpty()) {
                        LazyColumn() {
                            itemsIndexed(resource.data) { index, item ->
                                IngredientItem(
                                    ingredient = item.ingredient,
                                    modifier = Modifier.fillMaxWidth(),
                                    quantity = item.quantity,
                                    onAdd = {
                                        additionalIngredientViewModel.addIngredientToCart(
                                            index
                                        )
                                    },
                                    onSubstract = {
                                        additionalIngredientViewModel.substractIngredientToCart(
                                            index
                                        )
                                    })
                            }
                            item {
                                Row(Modifier.fillMaxWidth()) {
                                    Button(
                                        modifier = Modifier.weight(1f),
                                        onClick = {
                                            resource.data.forEach {
                                                if (it.quantity > 0) {
                                                    val total =
                                                        it.ingredient.price * it.quantity.toBigDecimal()
                                                    cartViewModel.postCart(
                                                        0,
                                                        dessertId.toInt(),
                                                        it.ingredient.id,
                                                        it.quantity,
                                                        total,
                                                        itemNumber
                                                    )
                                                }
                                            }

                                            navigateToSaleDessertList()
                                        }
                                    ) {
                                        Text(text = "Agregar otro postre")
                                    }
                                    Button(modifier = Modifier.weight(1f), onClick = {
                                        resource.data.forEach {
                                            if (it.quantity > 0) {
                                                val total =
                                                    it.ingredient.price * it.quantity.toBigDecimal()
                                                cartViewModel.postCart(
                                                    0,
                                                    dessertId.toInt(),
                                                    it.ingredient.id,
                                                    it.quantity,
                                                    total,
                                                    itemNumber
                                                )
                                            }
                                        }

                                        navigateToPayment()

                                    }) {
                                        Text(text = "Proceder al pago")
                                    }
                                }
                            }

                        }


                    } else {
                        DialogError({}, "No hay ingredientes")
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientItem(
    ingredient: AdditionalIngredient,
    modifier: Modifier = Modifier,
    quantity: Int,
    onAdd: () -> Unit,
    onSubstract: () -> Unit
) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = ingredient.name)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Disponible: ${ingredient.unitAvailable}")

                Row() {
                    IconButton(
                        onClick = onSubstract
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_substract_check),
                            contentDescription = "Minus"
                        )
                    }
                    Spacer(Modifier.width(5.dp))
                    Text(text = quantity.toString())
                    Spacer(Modifier.width(5.dp))
                    IconButton(
                        onClick = onAdd
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "Minus"
                        )
                    }
                }
            }
        }
    }
}
