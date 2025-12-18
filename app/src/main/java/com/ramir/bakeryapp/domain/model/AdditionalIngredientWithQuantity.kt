package com.ramir.bakeryapp.domain.model

import com.ramir.bakeryapp.utils.Resource

data class AdditionalIngredientWithQuantity (
    val ingredient: AdditionalIngredient,
    val quantity: Int = 0
)

data class AdditionalIngredientWithQuantityListUiState(
    val ingredientResource: Resource<List<AdditionalIngredientWithQuantity>> = Resource.Loading
)

