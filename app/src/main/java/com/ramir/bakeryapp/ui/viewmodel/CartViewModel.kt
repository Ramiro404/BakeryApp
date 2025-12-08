package com.ramir.bakeryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ramir.bakeryapp.domain.model.Cart
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

@HiltViewModel
class CartViewModel @Inject constructor(): ViewModel(){
    private val _cart = MutableStateFlow<List<Cart>>(emptyList())
    val cart: Flow<List<Cart>> = _cart.asStateFlow()


    fun addItemToCart(newCart: Cart){
        _cart.value += newCart

    }

    fun removeItemFromCart(index: Int){
        val currentList = _cart.value
        if(index < 0 || index >= currentList.size){
            return
        }
        _cart.value = currentList.toMutableList().apply {
            removeAt(index)
        }
    }

    fun getCart(){

    }

}