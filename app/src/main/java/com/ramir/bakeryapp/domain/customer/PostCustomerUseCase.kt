package com.ramir.bakeryapp.domain.customer

import com.ramir.bakeryapp.data.CustomerRepository
import com.ramir.bakeryapp.domain.model.Customer
import jakarta.inject.Inject

class PostCustomerUseCase @Inject() constructor(
    private val repository: CustomerRepository
){
    suspend operator fun invoke(customer: Customer):Long{
        return repository.insertCustomer(customer)
    }
}