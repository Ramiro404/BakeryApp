package com.ramir.bakeryapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ramir.bakeryapp.data.database.entities.AdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.DessertEntity

@Dao
interface AdditionalIngredientDao {
    @Query("SELECT * FROM additional_ingredient_table ORDER BY name ASC")
    suspend fun getAllAdditionalIngredient(): List<AdditionalIngredientEntity>

    @Query("SELECT * FROM additional_ingredient_table WHERE id = :id")
    suspend fun getAdditionalIngredientById(id: Int): AdditionalIngredientEntity?

    @Insert
    suspend fun insertAdditionalIngredient(additionalIngredientEntity: AdditionalIngredientEntity): Unit

    @Update
    suspend fun updateAdditionalIngredient(additionalIngredientEntity: AdditionalIngredientEntity): Int

    @Query("DELETE FROM additional_ingredient_table WHERE id=:id")
    suspend fun deleteAdditionalIngredientById(id: Int)
}