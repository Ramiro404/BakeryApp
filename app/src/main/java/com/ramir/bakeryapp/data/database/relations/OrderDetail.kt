package com.ramir.bakeryapp.data.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ramir.bakeryapp.data.database.entities.AdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.CustomerEntity
import com.ramir.bakeryapp.data.database.entities.DessertAdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.DessertEntity
import com.ramir.bakeryapp.data.database.entities.OrderDessertEntity
import com.ramir.bakeryapp.data.database.entities.OrderEntity

data class OrderDetail(
    @Embedded val order: OrderEntity,
    @Embedded(prefix = "cust_") val customer: CustomerEntity,
    @Embedded(prefix = "od_") val orderDessert: OrderDessertEntity,
    @Embedded(prefix = "di_") val dessertAdditionalIngerdient: DessertAdditionalIngredientEntity,
    @Embedded(prefix = "d_") val dessert: DessertEntity,
    @Embedded(prefix = "ai_") val ingredient: AdditionalIngredientEntity
)