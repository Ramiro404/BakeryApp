package com.ramir.bakeryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramir.bakeryapp.domain.dessert.GetAllDessertsUseCase
import com.ramir.bakeryapp.domain.dessert.GetDessertByIdUseCase
import com.ramir.bakeryapp.domain.dessert.PostNewDessertUseCase
import com.ramir.bakeryapp.domain.model.Dessert
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal

@HiltViewModel
class DessertViewModel @Inject constructor(
    private val getAllDessertsUseCase: GetAllDessertsUseCase,
    private val postNewDessertUseCase: PostNewDessertUseCase,
    private val getDessertByIdUseCase: GetDessertByIdUseCase
): ViewModel() {
    private val _dessertListUiState = MutableStateFlow<List<Dessert>>(emptyList())
    val dessertListUiState: Flow<List<Dessert>> = _dessertListUiState.asStateFlow()

    private val _dessertUiState = MutableStateFlow<Dessert?>(null)
    val dessertUiState: Flow<Dessert?> = _dessertUiState.asStateFlow()



    init {
        getDessertList()
    }

    fun saveNewDessert(name:String, description:String, unitAvailable:Int, price: BigDecimal){
        val dessert = Dessert(name= name, description= description, unitAvailable =  unitAvailable, price = price )
        viewModelScope.launch{
            postNewDessertUseCase(dessert = dessert)

        }

    }

    fun getDessertList(){
        viewModelScope.launch {
            _dessertListUiState.update {
                getAllDessertsUseCase()
            }
        }
    }

    fun getDessertById(id: Int){
        viewModelScope.launch {
            _dessertUiState.update {
                getDessertByIdUseCase(id)
            }
        }
    }
}