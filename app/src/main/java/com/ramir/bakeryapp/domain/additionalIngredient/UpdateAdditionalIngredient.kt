package com.ramir.bakeryapp.domain.additionalIngredient

import com.ramir.bakeryapp.data.IngredientRepository
import com.ramir.bakeryapp.data.database.entities.toEntity
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import javax.inject.Inject

class UpdateAdditionalIngredient @Inject constructor(
    private val repository: IngredientRepository
){
    suspend operator fun invoke(additionalIngredient: AdditionalIngredient){
        val entity = additionalIngredient.toEntity()
        repository.updateIngredient(entity)

    }
}