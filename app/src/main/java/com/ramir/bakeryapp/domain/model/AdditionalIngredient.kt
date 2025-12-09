package com.ramir.bakeryapp.domain.model

import com.ramir.bakeryapp.data.database.entities.AdditionalIngredientEntity
import com.ramir.bakeryapp.utils.Resource
import java.math.BigDecimal

data class AdditionalIngredient(
    val id:Int =0,
    val name:String,
    val description:String,
    val unitAvailable:Int,
    val price: BigDecimal
)

fun AdditionalIngredientEntity.toDomain() = AdditionalIngredient(id,name,description,unitAvailable,price)

data class AdditionalIngredientUiState(
    val ingredientResource: Resource<AdditionalIngredient> = Resource.Loading
)

data class AdditionalIngredientListUiState(
    val additionalIngredientList: Resource<List<AdditionalIngredient>> = Resource.Loading
)