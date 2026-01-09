package com.ramir.bakeryapp.domain.dessert

import com.ramir.bakeryapp.data.DessertRepository
import com.ramir.bakeryapp.data.database.entities.DessertEntity
import com.ramir.bakeryapp.domain.model.Dessert
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class GetAllDessertsUseCaseTest {

    @RelaxedMockK
    private lateinit var dessertRepository: DessertRepository

    lateinit var getAllDessertsUseCase:GetAllDessertsUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getAllDessertsUseCase = GetAllDessertsUseCase(dessertRepository)
    }

    @Test
    fun `when the db doesnt return anything then show a message`() = runBlocking {
        //Given
        coEvery { dessertRepository.getAllDesserts() } returns emptyList()

        //When
        getAllDessertsUseCase()

        //Then
        coVerify(exactly = 1) { dessertRepository.getAllDesserts() }
    }

    @Test
    fun `when the db return something then show the values`() = runBlocking {
        ///Given
        val myList = listOf<Dessert>(
            Dessert(1,"","",0, BigDecimal.ZERO,"")
        )
        coEvery { dessertRepository.getAllDesserts() } returns myList

        //When
        val response = getAllDessertsUseCase()

        //Then
        assert(response == myList)
    }

}