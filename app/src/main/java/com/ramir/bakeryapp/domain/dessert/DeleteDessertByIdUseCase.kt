package com.ramir.bakeryapp.domain.dessert

import com.ramir.bakeryapp.data.DessertRepository
import javax.inject.Inject

class DeleteDessertByIdUseCase @Inject constructor(
    private val repository: DessertRepository
) {
    suspend operator fun invoke(id:Int){
        return repository.deleteDessertById(id)
    }
}