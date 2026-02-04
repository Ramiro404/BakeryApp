package com.ramir.bakeryapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ramir.bakeryapp.MainDispatcherRule
import com.ramir.bakeryapp.domain.dessert.GetAllDessertsUseCase
import com.ramir.bakeryapp.domain.dessert.GetDessertByIdUseCase
import com.ramir.bakeryapp.domain.dessert.PostNewDessertUseCase
import com.ramir.bakeryapp.domain.dessert.UpdateDessertByIdUseCase
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.domain.model.DessertListUiState
import com.ramir.bakeryapp.domain.model.SaveUiState
import com.ramir.bakeryapp.utils.Resource
import com.ramir.bakeryapp.utils.SaveResource
import io.mockk.MockKAnnotations
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.core.ValueClassSupport.boxedValue
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
class DessertViewModelTest {
    //@RelaxedMockK
    private var getAllDessertsUseCase: GetAllDessertsUseCase = mockk()
    //@RelaxedMockK
    private  var postNewDessertUseCase: PostNewDessertUseCase = mockk()
    //@RelaxedMockK
    private  var getDessertByIdUseCase:GetDessertByIdUseCase = mockk()
    //@RelaxedMockK
    private  var updateDessertByIdUseCase:UpdateDessertByIdUseCase = mockk()

    private lateinit var dessertViewModel: DessertViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun onBefore() {
        //MockKAnnotations.init(this)
        dessertViewModel = DessertViewModel(
            getAllDessertsUseCase,
            postNewDessertUseCase,
            getDessertByIdUseCase,
            updateDessertByIdUseCase
            )
        //Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun when_call_getDessertById_should_return_null() = runTest {
        val id = 9999
        coEvery { getDessertByIdUseCase(id) } returns null

        dessertViewModel.getDessertById(id)
        //awaits()

        val currentState = dessertViewModel.dessertUiState.value
        assertTrue(currentState.dessertResource is Resource.Error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun when_call_getDessertById_should_return_result_success() = runTest {
        //Given
        val id = 9999
        coEvery { getDessertByIdUseCase(id) } returns Dessert(0,"","",0, BigDecimal.ZERO)

        //When
        dessertViewModel.getDessertById(id)

        //Then
        val currentState = dessertViewModel.dessertUiState.value
        assertTrue(currentState.dessertResource is Resource.Success)
    }

    @Test
    fun when_call_updateDessertById_should_return_success(){
        //Given
        val dessert = Dessert(0,"","",0, BigDecimal.ZERO)
        coEvery { updateDessertByIdUseCase(dessert) } returns Unit

        //When
        dessertViewModel.updateDessertById(0,"","",0, BigDecimal.ZERO,"")

        //Then
        val currentState = dessertViewModel.saveUiState.value
        assertEquals(SaveResource.Success, currentState.saveUiResource)
    }

    @Test
    fun when_call_updateDessertById_should_return_error_not_found(){
        //Given
        val dessert = Dessert(0,"","",0, BigDecimal.ZERO)
        coEvery { getDessertByIdUseCase(0) } returns null
        coEvery { updateDessertByIdUseCase(dessert) } returns Unit

        //When
        dessertViewModel.updateDessertById(0,"","",0, BigDecimal.ZERO,"")

        //Then
        val currentState = dessertViewModel.saveUiState.value
        assertEquals(SaveResource.Error("Dessert does not found"), currentState.saveUiResource)
    }

    @Test
    fun when_call_getDessertList_should_return_emptyList(){
        //Given
        coEvery { getAllDessertsUseCase() } returns emptyList()

        //When
        dessertViewModel.getDessertList()

        //Then
        val currentState = dessertViewModel.dessertListUiState.value
        assertEquals(currentState, DessertListUiState(Resource.Success(emptyList())))
    }


}