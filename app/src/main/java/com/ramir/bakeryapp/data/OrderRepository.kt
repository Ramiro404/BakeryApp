package com.ramir.bakeryapp.data

import android.util.Log
import com.ramir.bakeryapp.data.database.dao.OrderDao
import com.ramir.bakeryapp.data.database.entities.toDomain
import com.ramir.bakeryapp.data.database.relations.OrderDetail
import com.ramir.bakeryapp.domain.model.Order
import javax.inject.Inject

class OrderRepository @Inject() constructor(
    private val orderDao: OrderDao
){
    suspend fun getAllOrders(): List<Order>{
        return  orderDao.getAllOrder().map { it.toDomain() }
    }

    suspend fun getOrderById(id:Int): Map<String, List<OrderDetail>>{
        val result = orderDao.getOrderById(id)
        val resultGroupedItemNumber = result.groupBy { it.dessertAdditionalIngerdient.itemNumber}
        Log.i("Re", resultGroupedItemNumber.toString())
        return resultGroupedItemNumber
    }
}