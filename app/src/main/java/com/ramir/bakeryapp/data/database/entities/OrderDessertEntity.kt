package com.ramir.bakeryapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "order_dessert_table")
data class OrderDessertEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "order_id") val orderId: Int = 0,
    @ColumnInfo(name = "dessert_additional_ingredient_id") val dessertAdditionalIngredientId: Int = 0,
    @ColumnInfo(name = "total") val total: BigDecimal = BigDecimal.ZERO
)