package com.ramir.bakeryapp.domain.model

import com.ramir.bakeryapp.data.database.entities.CustomerEntity

data class Customer(
    val id: Int = 0,
    val name: String,
    val lastname: String,
    val phoneNumber: String
)

fun CustomerEntity.toDomain() = Customer(id,name,lastname,phoneNumber)