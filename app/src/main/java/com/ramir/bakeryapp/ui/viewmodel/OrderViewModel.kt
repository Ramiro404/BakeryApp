package com.ramir.bakeryapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramir.bakeryapp.domain.model.OrderDetailUiState
import com.ramir.bakeryapp.domain.model.OrderListUiState
import com.ramir.bakeryapp.domain.model.OrderUiState
import com.ramir.bakeryapp.domain.order.GetAllOrdersUseCase
import com.ramir.bakeryapp.domain.order.GetOrderByIdUseCase
import com.ramir.bakeryapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val getOrderByIdUseCase: GetOrderByIdUseCase
): ViewModel(){
    private val _orderListUiState = MutableStateFlow(OrderListUiState())
    val orderListUiState: Flow<OrderListUiState> = _orderListUiState.asStateFlow()

    private val _orderUiState = MutableStateFlow(OrderUiState())
    val orderUiState: Flow<OrderUiState> = _orderUiState.asStateFlow()

    private val _orderDetail = MutableStateFlow(OrderDetailUiState())
    val orderDetailUiState: Flow<OrderDetailUiState> = _orderDetail.asStateFlow()

    init {
        getAllOrders()
    }

    private fun getAllOrders(){
        viewModelScope.launch {
            _orderListUiState.update { it.copy(Resource.Loading) }
            try {
                val result = getAllOrdersUseCase()
                _orderListUiState.update { it.copy(Resource.Success(result)) }
            }catch (e: Exception){
                _orderListUiState.update { it.copy(Resource.Error("${e.message}")) }
                Log.e("ERROR", e.message.toString())
            }
        }
    }

    fun getOrderById(orderId:Int){
        viewModelScope.launch {
            _orderDetail.update { it.copy(Resource.Loading) }
            try {
                val result = getOrderByIdUseCase(orderId)
                Log.i("RESUL", result.toString())
                _orderDetail.update { it.copy(Resource.Success(result)) }
            }catch (e: Exception){
                _orderDetail.update { it.copy(Resource.Error("${e.message}")) }
                Log.e("ERROR", e.message.toString())
            }
        }
    }
}