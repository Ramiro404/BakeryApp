package com.ramir.bakeryapp.data

import android.util.Log
import com.ramir.bakeryapp.data.database.dao.CartIngredientDessertDao
import com.ramir.bakeryapp.data.database.entities.CartIngredientDessertEntity
import com.ramir.bakeryapp.data.database.entities.toDomain
import com.ramir.bakeryapp.data.database.relations.CartItemDetails
import com.ramir.bakeryapp.domain.model.Cart
import com.ramir.bakeryapp.domain.model.toEntity
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartIngredientDessertDao
){
    suspend fun getAll(): List<CartItemDetails>{
        val result = cartDao.getAllCartIngredientsDessert()
        Log.i("CartRepo",result.toString())
        return result
    }

    suspend fun insertCartItem(cartItem: Cart){
        val entity: CartIngredientDessertEntity = cartItem.toEntity()
        cartDao.insertIngredientDessert(entity)
    }

    suspend fun deleteAll(){
        cartDao.deleteAll()
    }
}