package com.ramir.bakeryapp.domain.additionalIngredient

import com.ramir.bakeryapp.data.IngredientRepository
import javax.inject.Inject

class DeleteAdditionalIngredientUseCase @Inject constructor(
    private val repository: IngredientRepository
) {
    suspend operator fun invoke(id: Int){
        repository.deleteIngredientById(id)
    }
}