package com.ramir.bakeryapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

/*
* Deprecated class, please, don't use it*/
@Entity(tableName = "cart_item_table")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "dessert_additional_ingredient_id") val dessertAdditionalIngredientId: Int = 0,
    @ColumnInfo(name = "total") val total: BigDecimal = BigDecimal.ZERO
)