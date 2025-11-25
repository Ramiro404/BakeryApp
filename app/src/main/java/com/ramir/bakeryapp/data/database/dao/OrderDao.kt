package com.ramir.bakeryapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ramir.bakeryapp.data.database.entities.OrderEntity

@Dao
interface OrderDao {
    @Query("SELECT * FROM order_table ")
    suspend fun getAllOrder(): List<OrderEntity>

    @Query("SELECT * FROM order_table WHERE id = :id")
    suspend fun getOrderById(id: Int): OrderEntity?

    @Insert
    suspend fun insertOrder(orderEntity: OrderEntity): Unit

    @Update
    suspend fun updateOrder(orderEntity: OrderEntity): Int

    @Query("DELETE FROM order_table WHERE id=:id")
    suspend fun deleteOrderById(id: Int)
}