package com.ramir.bakeryapp.data

import com.ramir.bakeryapp.data.database.dao.AdditionalIngredientDao
import com.ramir.bakeryapp.data.database.entities.AdditionalIngredientEntity
import com.ramir.bakeryapp.data.database.entities.toEntity
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import com.ramir.bakeryapp.domain.model.toDomain
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val ingredientDao: AdditionalIngredientDao
){
    suspend fun getAllIngredients(): List<AdditionalIngredient>{
        val response:List<AdditionalIngredientEntity> = ingredientDao.getAllAdditionalIngredient()
        return response.map { it.toDomain() }
    }

    suspend fun updateIngredient(ingredientEntity: AdditionalIngredientEntity){
        ingredientDao.updateAdditionalIngredient(ingredientEntity)
    }

    suspend fun addNewIngredient(ingredient: AdditionalIngredient){
        val entity = ingredient.toEntity()
        ingredientDao.insertAdditionalIngredient(entity)
    }
}