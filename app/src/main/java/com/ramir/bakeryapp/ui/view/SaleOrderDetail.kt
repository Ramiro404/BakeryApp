package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.domain.model.Order
import com.ramir.bakeryapp.domain.model.OrderUiState
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.OrderViewModel
import com.ramir.bakeryapp.utils.Resource

@Composable
fun SaleOrderDetailScreen(
    orderId: String,
    navigateToPaymentList: () -> Unit,
    orderViewModel: OrderViewModel = hiltViewModel()
){
    val orderState by orderViewModel.orderUiState.collectAsStateWithLifecycle(initialValue = OrderUiState())
    LaunchedEffect(Unit){
        orderViewModel.getOrderById(orderId.toInt())
    }
    Scaffold(
        topBar = { BakeryTopAppBar("Detalle de la orden") }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)){
            when(val resource = orderState.orderUiState) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<Order?> -> {
                    if(resource.data != null){

                    }else{
                        Text(text = "No hay informacion sobre esta orden")
                    }
                }
            }
        }
    }
}