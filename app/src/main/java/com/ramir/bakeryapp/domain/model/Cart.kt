package com.ramir.bakeryapp.domain.model

data class IngredientCart(
    val additionalIngredient: AdditionalIngredient,
    val quantity:Int
)

data class Cart(
    val dessert: Dessert,
    val ingredientList:List<IngredientCart>
)