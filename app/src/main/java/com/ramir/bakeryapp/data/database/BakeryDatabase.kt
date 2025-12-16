package com.ramir.bakeryapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ramir.bakeryapp.data.database.dao.AdditionalIngredientDao
import com.ramir.bakeryapp.data.database.dao.CartIngredientDessertDao
import com.ramir.bakeryapp.data.database.dao.CustomerDao
import com.ramir.bakeryapp.data.database.dao.DessertAdditionalIngredientDao
import com.ramir.bakeryapp.data.database.dao.DessertDao
import com.ramir.bakeryapp.data.database.dao.OrderDao
import com.ramir.bakeryapp.data.database.dao.OrderDessertDao
import com.ramir.bakeryapp.data.database.entities.AdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.CartIngredientDessertEntity
import com.ramir.bakeryapp.data.database.entities.CustomerEntity
import com.ramir.bakeryapp.data.database.entities.DessertAdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.DessertEntity
import com.ramir.bakeryapp.data.database.entities.OrderDessertEntity
import com.ramir.bakeryapp.data.database.entities.OrderEntity
import com.ramir.bakeryapp.utils.BigDecimalConverter
import com.ramir.bakeryapp.utils.LocalDateTimeConverter


@Database(
    entities = [
        DessertEntity::class, OrderEntity::class,
        AdditionalIngredientEntity::class, CustomerEntity::class,
        OrderDessertEntity::class, DessertAdditionalIngredientEntity::class, CartIngredientDessertEntity::class], version = 2)
@TypeConverters(BigDecimalConverter::class, LocalDateTimeConverter::class)
abstract class BakeryDatabase: RoomDatabase(){

    abstract fun getDessertDao(): DessertDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getAdditionalIngredientDao(): AdditionalIngredientDao
    abstract fun getCustomerDao(): CustomerDao
    abstract fun getDessertAdditionalIngredientDao(): DessertAdditionalIngredientDao
    abstract fun getOrderDessertDao(): OrderDessertDao
    abstract fun getCartIngredientDessertDao(): CartIngredientDessertDao
}