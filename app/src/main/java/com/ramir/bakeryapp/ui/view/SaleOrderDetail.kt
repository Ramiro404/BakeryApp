package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.data.database.relations.OrderDetail
import com.ramir.bakeryapp.domain.model.OrderDetailUiState
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.OrderViewModel
import com.ramir.bakeryapp.utils.Resource
import java.math.BigDecimal

@Composable
fun SaleOrderDetailScreen(
    orderId: String,
    navigateToPaymentList: () -> Unit,
    orderViewModel: OrderViewModel = hiltViewModel()
) {
    val orderState by orderViewModel.orderDetailUiState.collectAsStateWithLifecycle(initialValue = OrderDetailUiState())
    var total by rememberSaveable { mutableStateOf(BigDecimal.ZERO) }
    LaunchedEffect(Unit) {
        orderViewModel.getOrderById(orderId.toInt())
    }
    Scaffold(
        topBar = { BakeryTopAppBar("Detalle de la orden") }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val resource = orderState.orderUiState) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<Map<String, List<OrderDetail>>> -> {

                    LazyColumn {
                        resource.data.forEach{ (key, value) ->
                            item(key = key){
                                HorizontalDivider(modifier = Modifier.height(6.dp))
                                Text(text = key)
                            }
                            itemsIndexed(value){ index, order ->
                                if(index == 0){
                                    total = order.order.total
                                    Text(text = order.dessert.name)
                                    Text(text = order.order.total.toString())

                                }
                                Text("index $index")
                                Text(text = order.ingredient.name)
                                Text(text = "Cantidad: ${order.dessertAdditionalIngerdient.additionalIngredientQuantity}")

                            }
                        }
                        item {
                            HorizontalDivider(modifier = Modifier.height(6.dp))
                            Text(text = "TOTAL: $total")
                        }
                    }
                }
            }
        }
    }
}