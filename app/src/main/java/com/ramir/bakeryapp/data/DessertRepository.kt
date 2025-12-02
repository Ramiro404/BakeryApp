package com.ramir.bakeryapp.data

import com.ramir.bakeryapp.data.database.dao.DessertDao
import com.ramir.bakeryapp.data.database.entities.DessertEntity
import com.ramir.bakeryapp.data.database.entities.toEntity
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.domain.model.toDomain
import javax.inject.Inject

class DessertRepository @Inject constructor(
    private val dessertDao: DessertDao
){

    suspend fun getAllDesserts(): List<Dessert>{
        val response: List<DessertEntity> = dessertDao.getAllDesserts()
        return response.map { it.toDomain() }
    }

    suspend fun getDessertById(id: Int): Dessert?{
        val response: DessertEntity? = dessertDao.getDessertById(id)
        return response?.toDomain()
    }

    suspend fun insertDessert(dessert: Dessert): Unit{
        val entity: DessertEntity = dessert.toEntity()
        dessertDao.insertDessert(entity)
    }

    suspend fun updateDessert(dessert: Dessert): Int{
        val entity = dessert.toEntity()
        return dessertDao.updateDessert(entity)
    }

    suspend fun deleteDessertById(id: Int): Unit{
        dessertDao.deleteDessertById(id)
    }
}