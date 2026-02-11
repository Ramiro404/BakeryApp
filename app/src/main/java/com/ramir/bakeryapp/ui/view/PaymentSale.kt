package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramir.bakeryapp.data.database.relations.CartItemDetails
import com.ramir.bakeryapp.domain.model.Customer
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.DialogSuccess
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.CartViewModel
import com.ramir.bakeryapp.utils.Resource
import com.ramir.bakeryapp.utils.SaveResource
import java.math.BigDecimal

@Composable
fun PaymentSaleScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    navigateToSales: () -> Unit) {
    val cartList by cartViewModel.cart.collectAsStateWithLifecycle()
    val saveUiState by cartViewModel.saveUiState.collectAsStateWithLifecycle()
    var quantityPaid by rememberSaveable { mutableStateOf(BigDecimal.ZERO) }
    var changeToReturn by rememberSaveable { mutableStateOf(BigDecimal.ZERO) }
    var showDialog by rememberSaveable { mutableStateOf(true) }
    var showLoading by rememberSaveable { mutableStateOf(false) }

    var customerName by rememberSaveable { mutableStateOf("") }
    var customerLastname by rememberSaveable { mutableStateOf("") }
    var customerPhone by rememberSaveable { mutableStateOf("") }
    var showQuantityMessageError by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = { BakeryTopAppBar("Confirmar pago") }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            when (val resource = cartList.cartList) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<List<CartItemDetails>> -> {
                    val totalToPay = remember(resource.data) {
                        derivedStateOf {
                            resource.data.fold(BigDecimal.ZERO) { acc, item ->
                                acc.add(item.cartItem.total)
                            }
                        }
                    }
                    PaymentSaleContentent(
                        resource.data,
                        totalToPay.value,
                        quantityPaid.toString(),
                        {
                            quantityPaid = it
                            changeToReturn = totalToPay.value - quantityPaid
                        },
                        changeToReturn.toString(),
                        customerName,
                        { customerName = it },
                        customerLastname,
                        { customerLastname = it },
                        customerPhone,
                        { customerPhone = it },
                        showQuantityMessageError,
                        onConfirmPurchase = {
                            if ((quantityPaid - changeToReturn) >= BigDecimal.ZERO) {
                                showLoading = true
                                val customer = Customer(
                                    name = customerName,
                                    lastname = customerLastname,
                                    phoneNumber = customerPhone
                                )
                                cartViewModel.makePurchase(customer)
                                showLoading = false
                            } else {
                                showQuantityMessageError = true
                            }
                        },
                        onUnsavePurchase= {
                            cartViewModel.deleteCurrentCart()
                            navigateToSales()
                        }
                    )
                }
            }

            when (val resource = saveUiState.saveUiResource) {
                SaveResource.Idle -> {}
                is SaveResource.Error -> DialogError(
                    { showDialog = false },
                    "Ocurrio un problema, no se pudo guardar el postre",
                    showDialog
                )

                SaveResource.Loading -> {
                    LoadingProgress(showLoading)
                }

                SaveResource.Success -> DialogSuccess(
                    { showDialog = false },
                    "Guardado correctamente",
                    showDialog
                )
            }
        }
    }
}

@Composable
fun PaymentSaleContentent(
    cartList: List<CartItemDetails>,
    totalToPay: BigDecimal,
    quantityPaid: String,
    onQuantityPaid: (BigDecimal) -> Unit,
    changeToReturn: String,
    customerName: String,
    onCustomerName: (String) -> Unit,
    customerLastname: String,
    onCustomerLastname: (String) -> Unit,
    customerPhone: String,
    onCustomerPhone: (String) -> Unit,
    showQuantityMessageError: Boolean,
    onConfirmPurchase: () -> Unit,
    onUnsavePurchase: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Text(text = "Postre", modifier = Modifier.weight(1f))
            Text(text = "Precio del Postre", modifier = Modifier.weight(1f))
            Text(text = "Ingrediente", modifier = Modifier.weight(1f))
            Text(text = "Precio del ingrediente", modifier = Modifier.weight(1f))
            Text(text = "Cantidad de ingrediente", modifier = Modifier.weight(1f))
            Text(text = "Costo", modifier = Modifier.weight(1f))
        }
        var prevItemTitle = ""
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(cartList) { index, item ->
                Row {
                    if(prevItemTitle != item.cartItem.dessertItemNumber){
                        Text(text = item.dessert.name, modifier = Modifier.weight(5f))

                        Text(text = item.dessert.price.toString(), modifier = Modifier.weight(1f))
                    }else{
                        Text("",modifier = Modifier.weight(1f))
                    }
                    prevItemTitle = item.cartItem.dessertItemNumber
                    Text(text = item.dessert.price.toString(), modifier = Modifier.weight(1f))
                    Text(text = item.additionalIngredient.name, modifier = Modifier.weight(1f))
                    Text(
                        text = item.additionalIngredient.price.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = item.cartItem.additionalIngredientQuantity.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = item.cartItem.total.toString(), modifier = Modifier.weight(1f))
                }
            }
            item {
                Text(text = "Total: $$totalToPay")
                OutlinedTextField(
                    value = quantityPaid,
                    onValueChange = { onQuantityPaid(it.toBigDecimal()) },
                    label = { Text("Ingrese la cantidad") }
                )
                Text("Cambio a regresar al cliente:  $$changeToReturn")
                if (showQuantityMessageError) {
                    Text("La cantidad ingresada no puede ser menor al total")
                }

                OutlinedTextField(
                    value = customerName,
                    onValueChange = { onCustomerName(it) },
                    label = { Text("Nombre del cliente") }
                )

                OutlinedTextField(
                    value = customerLastname,
                    onValueChange = { onCustomerLastname(it) },
                    label = { Text("Apellidos del cliente") }
                )

                OutlinedTextField(
                    value = customerPhone,
                    onValueChange = { onCustomerPhone(it) },
                    label = { Text("Numero de telefono") }
                )
                Button(onClick = onConfirmPurchase) {
                    Text("Confirmar compra")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onUnsavePurchase){
                    Text("Salir sin guardar compra")
                }
            }
        }
    }
}