package com.ramir.bakeryapp.data

import android.util.Log
import com.ramir.bakeryapp.data.database.dao.AdditionalIngredientDao
import com.ramir.bakeryapp.data.database.dao.CartIngredientDessertDao
import com.ramir.bakeryapp.data.database.dao.DessertAdditionalIngredientDao
import com.ramir.bakeryapp.data.database.dao.DessertDao
import com.ramir.bakeryapp.data.database.dao.OrderDao
import com.ramir.bakeryapp.data.database.dao.OrderDessertDao
import com.ramir.bakeryapp.data.database.entities.CartIngredientDessertEntity
import com.ramir.bakeryapp.data.database.entities.CustomerEntity
import com.ramir.bakeryapp.data.database.entities.DessertAdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.OrderDessertEntity
import com.ramir.bakeryapp.data.database.entities.OrderEntity
import com.ramir.bakeryapp.data.database.entities.toDomain
import com.ramir.bakeryapp.data.database.relations.CartItemDetails
import com.ramir.bakeryapp.domain.model.Cart
import com.ramir.bakeryapp.domain.model.toEntity
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.Collections.emptyList
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartDao: CartIngredientDessertDao,
    private val orderDao: OrderDao,
    private val orderDessertDao: OrderDessertDao,
    private val dessertAdditionalIngredientDao: DessertAdditionalIngredientDao
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

    suspend fun  insertPurchase(cartList: List<CartItemDetails>){
        var total = BigDecimal.ZERO
        val orderList = mutableListOf<OrderDessertEntity>()
        val dessertIngredientIdList = mutableListOf<Int>()
        var dessertItem = ""

        cartList.forEach {
            if(dessertItem != it.cartItem.dessertItemNumber){
                dessertItem = it.cartItem.dessertItemNumber
                total += it.dessert.price
            }
            val diEntity = DessertAdditionalIngredientEntity(0, it.dessert.id, it.additionalIngredient.id, it.cartItem.additionalIngredientQuantity, it.cartItem.dessertItemNumber)
            val dessertIngredientId = dessertAdditionalIngredientDao.insertDessertAdditionalIgredient(diEntity)
            total += it.cartItem.total
            dessertIngredientIdList.add(dessertIngredientId.toInt())
        }

        val order = OrderEntity( total= total, customerId = 0)
        val orderId = orderDao.insertOrder(order)

        cartList.forEachIndexed { index, it ->
            val orderDessert = OrderDessertEntity(
                orderId =  orderId.toInt(),
                dessertAdditionalIngredientId = dessertIngredientIdList[index],
                total=it.cartItem.total)
            orderList.add(orderDessert)
        }
        orderDessertDao.insertAdditionalIngredient(orderList)
    }
}