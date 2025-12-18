package com.ramir.bakeryapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.ramir.bakeryapp.data.database.relations.CartItemDetails
import com.ramir.bakeryapp.domain.additionalIngredient.GetAllIAdditionalngredientsUseCase
import com.ramir.bakeryapp.domain.additionalIngredient.PostNewAdditionalIngredient
import com.ramir.bakeryapp.domain.additionalIngredient.UpdateAdditionalIngredient
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import com.ramir.bakeryapp.domain.model.AdditionalIngredientListUiState
import com.ramir.bakeryapp.domain.model.AdditionalIngredientWithQuantity
import com.ramir.bakeryapp.domain.model.AdditionalIngredientWithQuantityListUiState
import com.ramir.bakeryapp.domain.model.SaveUiState
import com.ramir.bakeryapp.utils.Resource
import com.ramir.bakeryapp.utils.SaveResource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal

@HiltViewModel
class AdditionalIngredientViewModel @Inject constructor(
    private val getAllIAdditionalngredientsUseCase: GetAllIAdditionalngredientsUseCase,
    private val postNewAdditionalIngredient: PostNewAdditionalIngredient,
    private val updateAdditionalIngredient: UpdateAdditionalIngredient
) : ViewModel() {
    private val _additionalIngredientListUiState =
        MutableStateFlow(AdditionalIngredientListUiState())
    val additionalIngredientListUiState: Flow<AdditionalIngredientListUiState> =
        _additionalIngredientListUiState.asStateFlow()

    private val _saveUiState = MutableStateFlow(SaveUiState())
    val saveUiState: Flow<SaveUiState> = _saveUiState.asStateFlow()

    private val _additionalIngredientWithQuantityListUiState = MutableStateFlow(
        AdditionalIngredientWithQuantityListUiState()
    )
    val additionalIngredientWithQuantityListUiState:Flow<AdditionalIngredientWithQuantityListUiState> = _additionalIngredientWithQuantityListUiState.asStateFlow()

    init {
        getList()
        getIngredientWithQuantityList()
    }

    private fun getList() {
        viewModelScope.launch {
            _additionalIngredientListUiState.update { it.copy(additionalIngredientList = Resource.Loading) }
            try {
                val result = getAllIAdditionalngredientsUseCase()
                _additionalIngredientListUiState.update {
                    it.copy(additionalIngredientList = Resource.Success(data = result))
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
                _additionalIngredientListUiState.update {
                    it.copy(
                        additionalIngredientList = Resource.Error(
                            message = "Ocurrio un error"
                        )
                    )
                }
            }
        }
    }

    private fun getIngredientWithQuantityList() {
        viewModelScope.launch {
            _additionalIngredientWithQuantityListUiState.update { it.copy(ingredientResource = Resource.Loading) }
            try {
                val result = getAllIAdditionalngredientsUseCase()
                _additionalIngredientWithQuantityListUiState.update {
                    it.copy(ingredientResource = Resource.Success(data = result.map { AdditionalIngredientWithQuantity(it) }))
                }
            } catch (e: Exception) {
                Log.e("ERROR", e.message.toString())
                _additionalIngredientWithQuantityListUiState.update {
                    it.copy(
                        ingredientResource = Resource.Error(
                            message = "Ocurrio un error"
                        )
                    )
                }
            }
        }
    }

    fun postIngredient(name: String, description: String, unitAvailable: Int, price: BigDecimal) {
        _saveUiState.update { it.copy(saveUiResource = SaveResource.Loading) }
        val ingredient = AdditionalIngredient(
            name = name,
            description = description,
            unitAvailable = unitAvailable,
            price = price
        )
        viewModelScope.launch {
            try{
                postNewAdditionalIngredient(ingredient)
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Success) }
            }catch (e: Exception){
                Log.e("ERROR", e.message.toString())
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Error("Ocurrio un error")) }
            }
        }
    }

    fun updateIngredient(
        id: Int,
        name: String,
        description: String,
        unitAvailable: Int,
        price: BigDecimal
    ) {
        _saveUiState.update { it.copy(saveUiResource = SaveResource.Loading) }
        val ingredient = AdditionalIngredient(id, name, description, unitAvailable, price)
        viewModelScope.launch {
            try {
                updateAdditionalIngredient(ingredient)
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Success) }
                getList()
            }catch (e: Exception){
                Log.e("ERROR", e.message.toString())
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Error("Ocurrio un error")) }
            }
        }
    }

    fun addIngredientToCart(index:Int){
        val currentState = _additionalIngredientWithQuantityListUiState.value
        val currentResource = currentState.ingredientResource

        if(currentResource is Resource.Success){
            val newList = currentResource.data.mapIndexed { i, item ->
                if(i == index &&  item.ingredient.unitAvailable > 0){

                    item.copy(
                        quantity = item.quantity  +1,
                        ingredient = item.ingredient.copy(unitAvailable = item.ingredient.unitAvailable -1)
                        )
                }else{
                    item
                }
            }
            _additionalIngredientWithQuantityListUiState.value = currentState.copy(
                ingredientResource = Resource.Success(newList)
            )
        }
    }

    fun substractIngredientToCart(index:Int){
        val currentState = _additionalIngredientWithQuantityListUiState.value
        val currentResource = currentState.ingredientResource

        if(currentResource is Resource.Success){
            val newList = currentResource.data.mapIndexed { i, item ->
                if(i == index && item.quantity > 0){
                    item.copy(
                        quantity = item.quantity  -1,
                        ingredient = item.ingredient.copy(unitAvailable = item.ingredient.unitAvailable +1)
                        )
                }else{
                    item
                }
            }
            _additionalIngredientWithQuantityListUiState.value = currentState.copy(
                ingredientResource = Resource.Success(newList)
            )
        }
    }

}