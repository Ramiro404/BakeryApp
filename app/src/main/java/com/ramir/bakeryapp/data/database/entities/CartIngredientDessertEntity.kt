package com.ramir.bakeryapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import com.ramir.bakeryapp.domain.model.Cart
import java.math.BigDecimal

@Entity(tableName = "cart_dessert_additional_ingredient_entity")
data class CartIngredientDessertEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "dessert_id") val dessertId: Int = 0,
    @ColumnInfo(name = "additional_ingredient_id") val additionalIngredientId: Int = 0,
    @ColumnInfo(name = "additional_ingredient_quantity") val additionalIngredientQuantity: Int = 0,
    @ColumnInfo(name = "total") val total: BigDecimal = BigDecimal.ZERO,
    @ColumnInfo(name = "dessert_item_number") val dessertItemNumber: Int = 0,

)

fun CartIngredientDessertEntity.toDomain() = Cart(id, dessertId, additionalIngredientId,additionalIngredientQuantity,total)