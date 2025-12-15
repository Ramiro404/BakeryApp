package com.ramir.bakeryapp.domain.Cart

import com.ramir.bakeryapp.data.CartRepository
import com.ramir.bakeryapp.data.database.relations.CartItemDetails
import com.ramir.bakeryapp.domain.model.Cart
import javax.inject.Inject

class GetAllCartIngredientDessert @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(): List<CartItemDetails>{
        return repository.getAll()
    }
}