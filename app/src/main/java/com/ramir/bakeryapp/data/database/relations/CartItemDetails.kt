package com.ramir.bakeryapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ramir.bakeryapp.data.database.entities.AdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.CartIngredientDessertEntity
import com.ramir.bakeryapp.data.database.entities.DessertEntity


data class CartItemDetails(
    @Embedded val cartItem: CartIngredientDessertEntity,

    @Relation(
        parentColumn = "dessert_id",
        entityColumn = "id",
        entity = DessertEntity::class // Asume que tienes una DessertEntity
    )
    val dessert: DessertEntity,

    // Atributos del AdditionalIngredient
    @Relation(
        parentColumn = "additional_ingredient_id",
        entityColumn = "id",
        entity = AdditionalIngredientEntity::class // Asume que tienes una AdditionalIngredientEntity
    )
    val additionalIngredient: AdditionalIngredientEntity,
)