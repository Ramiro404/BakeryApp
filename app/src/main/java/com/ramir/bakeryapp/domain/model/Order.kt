package com.ramir.bakeryapp.domain.model

import com.ramir.bakeryapp.utils.Resource
import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    val id: Int = 0,
    val timestamp: LocalDateTime,
    val total: BigDecimal,
    val customerId: Int
)

data class OrderListUiState(
    val orderListUiState: Resource<List<Order>> = Resource.Loading
)

data class OrderUiState(
    val orderUiState: Resource<Order?> = Resource.Loading
)

