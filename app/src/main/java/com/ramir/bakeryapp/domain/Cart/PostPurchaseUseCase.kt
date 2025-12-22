package com.ramir.bakeryapp.domain.Cart

import com.ramir.bakeryapp.data.CartRepository
import com.ramir.bakeryapp.data.database.relations.CartItemDetails
import javax.inject.Inject

class PostPurchaseUseCase @Inject constructor(
    private val repository: CartRepository
){
    suspend operator fun invoke(cartList: List<CartItemDetails>){
        repository.insertPurchase(cartList)
    }
}