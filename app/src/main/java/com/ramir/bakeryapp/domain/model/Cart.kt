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
    val dessertItemNumber: String
)

fun Cart.toEntity() = CartIngredientDessertEntity(id,dessertId,additionalIngredientId, additionalIngredientQuantity,total, dessertItemNumber)

data class CartListUiState(
    val cartList: Resource<List<CartItemDetails>> = Resource.Loading
)

data class CartIngredientView(
    val additionalIngredientId:Int,
    val additionalIngredientQuantity:Int,
)

data class CartView(
    val id:Int,
    val dessertId:Int,
    val total: BigDecimal,
    val ingredients: List<CartIngredientView>
)