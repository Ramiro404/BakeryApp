package com.ramir.bakeryapp.data

import com.ramir.bakeryapp.data.database.dao.CustomerDao
import com.ramir.bakeryapp.data.database.entities.CustomerEntity
import com.ramir.bakeryapp.data.database.entities.toEntity
import com.ramir.bakeryapp.domain.model.Customer
import javax.inject.Inject

class CustomerRepository @Inject() constructor(
    private val customerDao: CustomerDao
) {
    suspend fun insertCustomer(customer: Customer): Long{
        val customerEntity = customer.toEntity()
        return customerDao.insertCustomer(customerEntity)
    }
}