package com.ramir.bakeryapp.domain.dessert

import com.ramir.bakeryapp.data.DessertRepository
import com.ramir.bakeryapp.domain.model.Dessert
import javax.inject.Inject

class UpdateDessertByIdUseCase @Inject() constructor(
    private val repository: DessertRepository
){
    suspend operator fun invoke(dessert: Dessert) {
        repository.updateDessert(dessert)
    }
}