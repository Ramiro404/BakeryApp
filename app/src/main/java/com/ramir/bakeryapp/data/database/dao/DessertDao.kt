package com.ramir.bakeryapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ramir.bakeryapp.data.database.entities.DessertEntity

@Dao
interface DessertDao {
    @Query("SELECT * FROM dessert_table ORDER BY name ASC")
    suspend fun getAllDesserts(): List<DessertEntity>

    @Query("SELECT * FROM dessert_table WHERE id = :id")
    suspend fun getDessertById(id: Int): DessertEntity?

    @Insert
    suspend fun insertDessert(dessertEntity: DessertEntity): Unit

    @Update
    suspend fun updateDessert(dessertEntity: DessertEntity): Int

    @Query("DELETE FROM dessert_table WHERE id=:id")
    suspend fun deleteDessertById(id: Int)
}