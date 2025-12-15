package com.ramir.bakeryapp.domain.Cart

import com.ramir.bakeryapp.data.CartRepository
import com.ramir.bakeryapp.domain.model.Cart
import javax.inject.Inject

class PostCartIngredientDessert @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(cart: Cart){
        repository.insertCartItem(cart)
    }
}