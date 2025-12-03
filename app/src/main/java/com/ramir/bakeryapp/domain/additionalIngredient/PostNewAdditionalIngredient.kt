package com.ramir.bakeryapp.domain.additionalIngredient

import com.ramir.bakeryapp.data.IngredientRepository
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import javax.inject.Inject

class PostNewAdditionalIngredient @Inject constructor(
    private val repository: IngredientRepository
) {
    suspend operator fun invoke(additionalIngredient: AdditionalIngredient){
        repository.addNewIngredient(additionalIngredient)
    }
}