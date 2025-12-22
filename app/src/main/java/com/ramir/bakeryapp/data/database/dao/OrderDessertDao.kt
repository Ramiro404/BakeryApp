package com.ramir.bakeryapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ramir.bakeryapp.data.database.entities.AdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.OrderDessertEntity

@Dao
interface OrderDessertDao {

    @Query("SELECT * FROM order_dessert_table where order_id = :orderId")
    suspend fun getAllAdditionalIngredient(orderId: Int): List<OrderDessertEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdditionalIngredient(orderDessertEntity: List<OrderDessertEntity>)

}