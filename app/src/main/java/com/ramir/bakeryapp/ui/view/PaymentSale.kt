package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    navigateToSales: () -> Unit
) {
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
    var prevItemTitle by rememberSaveable { mutableStateOf("") }
    var currentDessert by rememberSaveable { mutableStateOf("") }
    var totalToPay by rememberSaveable { mutableStateOf(BigDecimal.ZERO) }

    Scaffold(
        topBar = { BakeryTopAppBar("Confirmar pago") }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val resource = cartList.cartList) {
                is Resource.Error -> DialogError({}, resource.message)
                Resource.Loading -> LoadingProgress()
                is Resource.Success<List<CartItemDetails>> -> {
                    LaunchedEffect(Unit) {
                        resource.data.forEach {
                            if (currentDessert != it.cartItem.dessertItemNumber) {
                                totalToPay += it.dessert.price
                                currentDessert = it.cartItem.dessertItemNumber
                            }
                            totalToPay += it.cartItem.total
                        }
                    }
                    PaymentSaleContentent(
                        resource.data,
                        totalToPay,
                        quantityPaid.toString(),
                        {
                            quantityPaid = it
                            changeToReturn = totalToPay - quantityPaid
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
                        onUnsavePurchase = {
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
    onUnsavePurchase: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Text(text = "Producto", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            Text(text = "Unidades", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            Text(text = "Precio unitario", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        }
        var currentDessertItem = ""
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(cartList) { index, item ->
                if (currentDessertItem != item.cartItem.dessertItemNumber) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.SpaceAround) {
                        Text(text = item.dessert.name, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text(text = "1", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        Text(
                            text = item.dessert.price.toString(),
                            modifier = Modifier.weight(1f)
                            , textAlign = TextAlign.Center
                        )
                    }
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                    currentDessertItem = item.cartItem.dessertItemNumber

                }
                //Text(text = item.dessert.price.toString(), modifier = Modifier.weight(1f))
                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Text(text = item.additionalIngredient.name, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    Text(
                        text = item.cartItem.additionalIngredientQuantity.toString(),
                        modifier = Modifier.weight(1f), textAlign = TextAlign.Center
                    )
                    Text(
                        text = item.additionalIngredient.price.toString(),
                        modifier = Modifier.weight(1f), textAlign = TextAlign.Center
                    )
                }

                HorizontalDivider(modifier = Modifier.fillMaxWidth())

            }
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Total: $$totalToPay",textAlign = TextAlign.End)
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
                    Button(onClick = onUnsavePurchase) {
                        Text("Salir sin guardar compra")
                    }
                }

            }
        }
    }
}