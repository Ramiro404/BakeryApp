package com.ramir.bakeryapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "additional_ingredient_table")
data class AdditionalIngredientEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "unit_available") val unitAvailable: Int = 0,
    @ColumnInfo(name = "price") val price: BigDecimal = BigDecimal.ZERO
)