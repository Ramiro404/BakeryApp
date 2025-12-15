package com.ramir.bakeryapp.domain.Cart

import com.ramir.bakeryapp.data.CartRepository
import javax.inject.Inject

class DeleteAllCartIngredientDessert @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(){
        repository.deleteAll()
    }
}