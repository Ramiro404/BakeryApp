package com.ramir.bakeryapp.domain.order

import com.ramir.bakeryapp.data.OrderRepository
import com.ramir.bakeryapp.domain.model.Order
import javax.inject.Inject

class GetOrderByIdUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(orderId:Int): Order?{
        return repository.getOrderById(orderId)
    }
}