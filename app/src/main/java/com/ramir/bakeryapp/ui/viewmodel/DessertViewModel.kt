package com.ramir.bakeryapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramir.bakeryapp.domain.dessert.GetAllDessertsUseCase
import com.ramir.bakeryapp.domain.dessert.GetDessertByIdUseCase
import com.ramir.bakeryapp.domain.dessert.PostNewDessertUseCase
import com.ramir.bakeryapp.domain.dessert.UpdateDessertByIdUseCase
import com.ramir.bakeryapp.domain.model.Dessert
import com.ramir.bakeryapp.domain.model.DessertListUiState
import com.ramir.bakeryapp.domain.model.DessertUiState
import com.ramir.bakeryapp.domain.model.SaveUiState
import com.ramir.bakeryapp.utils.Resource
import com.ramir.bakeryapp.utils.SaveResource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal

@HiltViewModel
class DessertViewModel @Inject constructor(
    private val getAllDessertsUseCase: GetAllDessertsUseCase,
    private val postNewDessertUseCase: PostNewDessertUseCase,
    private val getDessertByIdUseCase: GetDessertByIdUseCase,
    private val updateDessertByIdUseCase: UpdateDessertByIdUseCase
): ViewModel() {
    private val _dessertListUiState = MutableStateFlow(DessertListUiState())
    val dessertListUiState: Flow<DessertListUiState> = _dessertListUiState.asStateFlow()

    private val _dessertUiState = MutableStateFlow(DessertUiState())
    val dessertUiState: StateFlow<DessertUiState> = _dessertUiState.asStateFlow()

    private val _saveUiState = MutableStateFlow(SaveUiState())
    val saveUiState: StateFlow<SaveUiState> = _saveUiState.asStateFlow()

    init {
        getDessertList()
    }

    fun saveNewDessert(name:String, description:String, unitAvailable:Int, price: BigDecimal, image:String){
        _saveUiState.update { it.copy(saveUiResource = SaveResource.Loading) }
        val dessert = Dessert(name= name, description= description, unitAvailable =  unitAvailable, price = price, imagePath = image )
        viewModelScope.launch{
            try {
                postNewDessertUseCase(dessert = dessert)
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Success) }
            }catch (e: Exception){
                Log.e("ERROR", e.message.toString())
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Error(message = e.message.toString())) }
            }
        }

    }

    fun getDessertList(){
        viewModelScope.launch {
            _dessertListUiState.update { it.copy(dessertListUiState = Resource.Loading) }
            try{
                val result = getAllDessertsUseCase()
                Log.i("RESULTADO", result.toString())
                _dessertListUiState.update { it.copy(dessertListUiState = Resource.Success(data = result)) }
            }catch (e: Exception){
                Log.e("ERROR", e.message.toString())
                _dessertListUiState.update { it.copy(dessertListUiState = Resource.Error("Ocurrio un error ${e.message}")) }
            }

        }
    }

    fun getDessertById(id: Int){
        viewModelScope.launch {
            _dessertUiState.update { it.copy(dessertResource = Resource.Loading) }
            try {
                val result = getDessertByIdUseCase(id)
                _dessertUiState.update { it.copy(dessertResource = Resource.Success(result) as Resource<Dessert>) }
            }catch (e: Exception){
                Log.e("ERROR", e.message.toString())
                _dessertUiState.update { it.copy(dessertResource = Resource.Error("Ocurrio un error ${e.message}")) }
            }
        }
    }

    fun updateDessertById(id: Int,name:String, description:String, unitAvailable:Int, price: BigDecimal, image:String){
        viewModelScope.launch {
            _saveUiState.update { it.copy(saveUiResource = SaveResource.Loading) }
            try {
                val dessert = Dessert(id= id,name= name, description= description, unitAvailable =  unitAvailable, price = price, imagePath = image )
                updateDessertByIdUseCase(dessert)
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Success) }
            }catch (e: Exception){
                Log.e("ERROR", e.message.toString())
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Error("Ocurrio un error ${e.message}")) }
            }
        }
    }
}