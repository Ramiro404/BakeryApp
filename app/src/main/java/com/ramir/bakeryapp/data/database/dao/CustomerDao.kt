package com.ramir.bakeryapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ramir.bakeryapp.data.database.entities.CustomerEntity
import com.ramir.bakeryapp.data.database.entities.DessertEntity

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customer_table ORDER BY name ASC")
    suspend fun getAllCustomer(): List<CustomerEntity>

    @Query("SELECT * FROM customer_table WHERE id = :id")
    suspend fun getCustomerById(id: Int): CustomerEntity?

    @Insert
    suspend fun insertCustomer(customerEntity: CustomerEntity): Unit

    @Update
    suspend fun updateCustomer(customerEntity: CustomerEntity): Int

}