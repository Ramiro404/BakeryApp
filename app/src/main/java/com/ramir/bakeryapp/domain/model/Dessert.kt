package com.ramir.bakeryapp.domain.model

import com.ramir.bakeryapp.data.database.entities.DessertEntity
import com.ramir.bakeryapp.utils.Resource
import java.math.BigDecimal

data class Dessert(
    val id: Int = 0,
    val name: String,
    val description: String,
    val unitAvailable: Int,
    val price: BigDecimal
)

fun DessertEntity.toDomain() = Dessert(id, name, description, unitAvailable, price)

data class DessertUiState(
    val dessertResource: Resource<Dessert> = Resource.Loading
)

data class DessertListUiState(
    val dessertListUiState: Resource<List<Dessert>> = Resource.Loading
)