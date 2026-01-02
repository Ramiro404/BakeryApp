package com.ramir.bakeryapp.domain.order

import com.ramir.bakeryapp.data.OrderRepository
import com.ramir.bakeryapp.domain.model.Order
import javax.inject.Inject

class GetAllOrdersUseCase @Inject() constructor(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(): List<Order>{
        return repository.getAllOrders()
    }
}