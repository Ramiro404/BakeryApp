package com.ramir.bakeryapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dessert_additional_ingredient_entity")
data class DessertAdditionalIngredientEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "dessert_id") val dessertId: Int = 0,
    @ColumnInfo(name = "additional_ingredient_id") val additionalIngredientId: Int = 0,
    @ColumnInfo(name = "additional_ingredient_quantity") val additionalIngredientQuantity: Int = 0
)