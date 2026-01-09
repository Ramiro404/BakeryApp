package com.ramir.bakeryapp.ui.viewmodel

import android.util.Log
import com.ramir.bakeryapp.domain.dessert.GetAllDessertsUseCase
import com.ramir.bakeryapp.domain.dessert.GetDessertByIdUseCase
import com.ramir.bakeryapp.domain.dessert.PostNewDessertUseCase
import com.ramir.bakeryapp.domain.dessert.UpdateDessertByIdUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import androidx.arch.core.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.utils.Resource
import com.ramir.bakeryapp.utils.SaveResource
import io.mockk.coEvery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.Dispatcher
import org.junit.After
import org.junit.Test
import java.math.BigDecimal

class DessertViewModelTest {
    @RelaxedMockK
    private lateinit var getAllDessertsUseCase: GetAllDessertsUseCase
    @RelaxedMockK
    private lateinit var postNewDessertUseCase: PostNewDessertUseCase
    @RelaxedMockK
    private lateinit var getDessertByIdUseCase:GetDessertByIdUseCase
    @RelaxedMockK
    private lateinit var updateDessertByIdUseCase:UpdateDessertByIdUseCase

    private lateinit var dessertViewModel: DessertViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        dessertViewModel = DessertViewModel(getAllDessertsUseCase, postNewDessertUseCase,getDessertByIdUseCase, updateDessertByIdUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun when_save_dessert_uistate_should_be_success() = runTest {
        //Given
        val name=""
        val description=""
        val unitAvailable=0
        val price= BigDecimal.ZERO
        val image=""
        coEvery { postNewDessertUseCase(Dessert(0,name,description,unitAvailable,price, image)) }

        //When
        dessertViewModel.saveNewDessert(name,description,unitAvailable,price,image)
        advanceUntilIdle()
        //  Then
        assert(dessertViewModel.saveUiState.value == SaveResource.Success)
    }

    @Test
    fun when_call_getDessertById_should_return_null() = runTest {
        //Given
        val mockDessert = Dessert(id = 1, name = "Cake", description = "Sweet", unitAvailable = 5, price = BigDecimal.TEN)
        coEvery { getDessertByIdUseCase(1) } returns mockDessert
/*
        dessertViewModel.dessertUiState.t {
            // 1. Initial state or Loading state
            val firstItem = awaitItem()
            // If your initial state is Loading, you might need to check it here

            // 2. Trigger the action
            dessertViewModel.getDessertById(1)

            // 3. Await the Success state
            val successItem = awaitItem()
            assert(successItem.dessertResource is Resource.Success)
            assertEquals(mockDessert, (successItem.dessertResource as Resource.Success).data)
        }*/

    }

}