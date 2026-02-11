package com.ramir.bakeryapp.domain.model

import com.ramir.bakeryapp.data.database.entities.DessertEntity
import com.ramir.bakeryapp.utils.Resource
import com.ramir.bakeryapp.utils.SaveResource
import java.math.BigDecimal

data class Dessert(
    val id: Int = 0,
    val name: String,
    val description: String,
    val unitAvailable: Int,
    val price: BigDecimal,
    val imagePath:String = ""
)

fun DessertEntity.toDomain() = Dessert(id, name, description, unitAvailable, price, imagePath)

data class DessertUiState(
    val dessertResource: Resource<Dessert> = Resource.Loading
)

data class DessertListUiState(
    val dessertListUiState: Resource<List<Dessert>> = Resource.Loading
)

data class SaveUiState(
    val saveUiResource: SaveResource = SaveResource.Idle
)