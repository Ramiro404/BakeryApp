package com.ramir.bakeryapp.domain.model

import com.ramir.bakeryapp.data.database.entities.DessertEntity
import java.math.BigDecimal

data class Dessert(
    val id: Int = 0,
    val name: String,
    val description: String,
    val unitAvailable: Int,
    val price: BigDecimal
)

fun DessertEntity.toDomain() = Dessert(id, name, description, unitAvailable, price)