package com.ramir.bakeryapp.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramir.bakeryapp.ui.viewmodel.AdditionalIngredientViewModel

@Composable
fun ListIngredientScreen(additionalIngredientViewModel: AdditionalIngredientViewModel = viewModel()){
    val additionalIngredientList by additionalIngredientViewModel.additionalIngredientList.collectAsStateWithLifecycle(initialValue = emptyList())
}