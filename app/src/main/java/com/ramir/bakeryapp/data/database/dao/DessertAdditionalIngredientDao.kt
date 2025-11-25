package com.ramir.bakeryapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ramir.bakeryapp.data.database.entities.OrderEntity

@Dao
interface DessertAdditionalIngredientDao {
    @Query("SELECT * FROM dessert_additional_ingredient_entity where id=:id ")
    suspend fun getAllDessertAdditionalIgredientById(id: Int): List<OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDessertAdditionalIgredient(orderEntity: OrderEntity): Unit

}