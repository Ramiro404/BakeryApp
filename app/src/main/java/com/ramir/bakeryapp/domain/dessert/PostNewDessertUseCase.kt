package com.ramir.bakeryapp.domain.dessert

import com.ramir.bakeryapp.data.DessertRepository
import com.ramir.bakeryapp.domain.model.Dessert
import javax.inject.Inject

class PostNewDessertUseCase @Inject() constructor(
    private val repository: DessertRepository
) {
    suspend operator fun invoke(dessert: Dessert){
        repository.insertDessert(dessert)
    }
}