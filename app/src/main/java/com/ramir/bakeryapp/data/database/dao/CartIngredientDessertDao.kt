package com.ramir.bakeryapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ramir.bakeryapp.data.database.entities.AdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.CartIngredientDessertEntity
import com.ramir.bakeryapp.data.database.entities.CustomerEntity
import com.ramir.bakeryapp.data.database.relations.CartItemDetails

@Dao
interface CartIngredientDessertDao {

    @Query("""
        SELECT 
            cart_item.*,           -- Selecciona todas las columnas de la tabla de unión
            d.*,                   -- Selecciona todas las columnas del postre
            ai.* -- Selecciona todas las columnas del ingrediente
        FROM cart_dessert_additional_ingredient_entity AS cart_item
        
        INNER JOIN dessert_table AS d
        ON cart_item.dessert_id = d.id
        
        INNER JOIN additional_ingredient_table AS ai
        ON cart_item.additional_ingredient_id = ai.id
        """)
    suspend fun getAllCartIngredientsDessert(): List<CartItemDetails>

    @Insert
    suspend fun insertIngredientDessert(cartIngredientDessertEntity: CartIngredientDessertEntity): Unit

    @Query("DELETE FROM cart_dessert_additional_ingredient_entity")
    suspend fun deleteAll(): Unit

    @Query("SELECT * FROM additional_ingredient_table WHERE id IN (:ids)")
    suspend fun getIngredientsFromIdList(ids: List<Int>): List<AdditionalIngredientEntity>

    //@Query("Select count(*) FROM cart_dessert_additional_ingredient_entity")
    //suspend fun getConsecutiveDessertNumber()

}