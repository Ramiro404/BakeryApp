package com.ramir.bakeryapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramir.bakeryapp.data.database.relations.CartItemDetails
import com.ramir.bakeryapp.domain.Cart.DeleteAllCartIngredientDessert
import com.ramir.bakeryapp.domain.Cart.GetAllCartIngredientDessert
import com.ramir.bakeryapp.domain.Cart.PostCartIngredientDessert
import com.ramir.bakeryapp.domain.Cart.PostPurchaseUseCase
import com.ramir.bakeryapp.domain.model.Cart
import com.ramir.bakeryapp.domain.model.CartListUiState
import com.ramir.bakeryapp.domain.model.SaveUiState
import com.ramir.bakeryapp.utils.Resource
import com.ramir.bakeryapp.utils.SaveResource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCartIngredientDessert: GetAllCartIngredientDessert,
    private val postCartIngredientDessert: PostCartIngredientDessert,
    private val deleteAllCartIngredientDessert: DeleteAllCartIngredientDessert,
    private val postPurchaseUseCase: PostPurchaseUseCase
): ViewModel(){
    private val _cart = MutableStateFlow(CartListUiState())
    val cart: StateFlow<CartListUiState> = _cart.asStateFlow()

    private val _saveUiState = MutableStateFlow(SaveUiState())
    val saveUiState: StateFlow<SaveUiState> = _saveUiState.asStateFlow()

    init {
        getAllCart()
    }

    private fun getAllCart(){
        _cart.update { it.copy(cartList = Resource.Loading) }
        viewModelScope.launch {
            try {
                val result = getAllCartIngredientDessert()
                Log.i("RESULTADO",result.toString())
                _cart.update { it.copy(cartList = Resource.Success(result)) }
            }catch (e: Exception){
                Log.e("ERROR", e.message.toString())
                _cart.update { it.copy(cartList = Resource.Error("Ocurrio un error")) }
            }
        }
    }

     fun postCart(id:Int, dessertId:Int, additionalIngredientId: Int, additionalIngredientQuantity:Int, total: BigDecimal){
        _saveUiState.update { it.copy(saveUiResource = SaveResource.Loading) }
        val cart = Cart(id,dessertId,additionalIngredientId,additionalIngredientQuantity, total)
        viewModelScope.launch {
            try {
                postCartIngredientDessert(cart)
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Success) }
            }catch (e: Exception){
                Log.e("ERROR", e.message.toString())
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Error("Ocurrio un error")) }
            }
        }
    }

    fun makePurchase(){
        _saveUiState.update { it.copy(saveUiResource = SaveResource.Loading) }
        viewModelScope.launch {
            try {
                val currentState = _cart.value
                val currentResource = currentState.cartList
                if(currentResource is Resource.Success){
                    postPurchaseUseCase(currentResource.data)
                    deleteAllCartIngredientDessert()
                }
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Success) }
            }catch (e: Exception){
                Log.e("ERROR", e.message.toString())
                _saveUiState.update { it.copy(saveUiResource = SaveResource.Error("Ocurrio un error")) }
            }
        }
    }



}