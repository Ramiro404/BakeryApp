package com.ramir.bakeryapp.data

import com.ramir.bakeryapp.data.database.dao.OrderDao
import com.ramir.bakeryapp.data.database.entities.toDomain
import com.ramir.bakeryapp.domain.model.Order
import javax.inject.Inject

class OrderRepository @Inject() constructor(
    private val orderDao: OrderDao
){
    suspend fun getAllOrders(): List<Order>{
        return  orderDao.getAllOrder().map { it.toDomain() }
    }

    suspend fun getOrderById(id:Int): Order?{
        val result = orderDao.getOrderById(id)
        return result?.toDomain()
    }
}