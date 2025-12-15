package com.ramir.bakeryapp.domain.model

import com.ramir.bakeryapp.data.database.entities.CartIngredientDessertEntity
import com.ramir.bakeryapp.data.database.relations.CartItemDetails
import com.ramir.bakeryapp.utils.Resource
import java.math.BigDecimal


data class Cart(
    val id:Int,
    val dessertId:Int,
    val additionalIngredientId:Int,
    val additionalIngredientQuantity:Int,
    val total: BigDecimal,
)

fun Cart.toEntity() = CartIngredientDessertEntity(id,dessertId,additionalIngredientId, additionalIngredientQuantity,total)

data class CartListUiState(
    val cartList: Resource<List<CartItemDetails>> = Resource.Loading
)
