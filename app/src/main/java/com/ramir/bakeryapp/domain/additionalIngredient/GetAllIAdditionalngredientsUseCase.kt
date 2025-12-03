package com.ramir.bakeryapp.domain.additionalIngredient

import com.ramir.bakeryapp.data.IngredientRepository
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import javax.inject.Inject

class GetAllIAdditionalngredientsUseCase @Inject constructor(
    private val repository: IngredientRepository
){
    suspend operator fun invoke(): List<AdditionalIngredient>{
        return repository.getAllIngredients()
    }
}