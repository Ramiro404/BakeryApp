package com.ramir.bakeryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramir.bakeryapp.domain.additionalIngredient.GetAllIAdditionalngredientsUseCase
import com.ramir.bakeryapp.domain.additionalIngredient.PostNewAdditionalIngredient
import com.ramir.bakeryapp.domain.additionalIngredient.UpdateAdditionalIngredient
import com.ramir.bakeryapp.domain.model.AdditionalIngredient
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
}