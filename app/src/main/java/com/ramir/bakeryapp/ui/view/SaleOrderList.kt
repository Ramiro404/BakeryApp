package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.domain.model.Order
import com.ramir.bakeryapp.domain.model.OrderListUiState
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.OrderViewModel
import com.ramir.bakeryapp.utils.Resource

@Composable
fun SaleOrderListScreen(
    orderViewModel: OrderViewModel = hiltViewModel(),
    onPaymentDetail: (orderId:String) -> Unit
){
    val orderListState by orderViewModel.orderListUiState.collectAsStateWithLifecycle(initialValue = OrderListUiState())
    var showLoading by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {BakeryTopAppBar("Ordenes")}
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){
            when(val resource = orderListState.orderListUiState) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> {
                    showLoading = true
                    LoadingProgress(showLoading)
                }
                is Resource.Success<List<Order>> -> {
                    showLoading = false
                    if(resource.data.isNotEmpty()){
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row {
                                Text(text = "#")
                                Text(text = "Fecha")
                                Text(text = "Total")
                            }
                            LazyColumn() {
                                itemsIndexed(resource.data){ index, order ->
                                    Card(modifier = Modifier.clickable{
                                        onPaymentDetail(order.id.toString())
                                    }) {
                                        Row {
                                            Text(text = "$index")
                                            Text(text = "${order.timestamp.dayOfMonth} / ${order.timestamp.month} /  ${order.timestamp.year} , ${order.timestamp.hour} : ${order.timestamp.minute}")
                                            Text(text = "${order.total}")
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        Text(text = "No hay articulos en este momento", fontSize = 48.sp)
                    }
                }
            }
        }
    }

}