package com.ramir.bakeryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramir.bakeryapp.domain.additionalIngredient.GetAllIAdditionalngredientsUseCase
import com.ramir.bakeryapp.domain.additionalIngredient.PostNewAdditionalIngredient
import com.ramir.bakeryapp.domain.additionalIngredient.UpdateAdditionalIngredient
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
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
): ViewModel() {
    private val _additionalIngredientList = MutableStateFlow<List<AdditionalIngredient>>(emptyList())
    val additionalIngredientList: Flow<List<AdditionalIngredient>> = _additionalIngredientList.asStateFlow()

    init {
        getList()
    }

    private fun getList(){
        viewModelScope.launch {
            _additionalIngredientList.update {
                getAllIAdditionalngredientsUseCase()
            }
        }
    }

    fun postIngredient(name:String, description:String, unitAvailable:Int, price: BigDecimal){
        val ingredient = AdditionalIngredient(name=name, description=description, unitAvailable=unitAvailable, price=price)
        viewModelScope.launch {
            postNewAdditionalIngredient(ingredient)
        }
    }

    fun updateIngredient(id: Int, name:String, description:String, unitAvailable:Int, price: BigDecimal){
        val ingredient = AdditionalIngredient(id, name, description, unitAvailable, price)
        viewModelScope.launch {
            updateAdditionalIngredient(ingredient)
        }
    }
}