package com.ramir.bakeryapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramir.bakeryapp.data.database.dao.DessertDao
import com.ramir.bakeryapp.data.database.dao.OrderDao
import com.ramir.bakeryapp.data.database.entities.AdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.CustomerEntity
import com.ramir.bakeryapp.data.database.entities.DessertAdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.DessertEntity
import com.ramir.bakeryapp.data.database.entities.OrderDessertEntity
import com.ramir.bakeryapp.data.database.entities.OrderEntity

@Database(
    entities = [
        DessertEntity::class, OrderEntity::class,
        AdditionalIngredientEntity::class, CustomerEntity::class,
        OrderDessertEntity::class, DessertAdditionalIngredientEntity::class], version = 1)
abstract class BakeryDatabase: RoomDatabase(){

    abstract fun getDessertDao(): DessertDao

    abstract fun getOrderDao(): OrderDao
}