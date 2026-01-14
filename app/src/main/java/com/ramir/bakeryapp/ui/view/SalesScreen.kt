package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ramir.bakeryapp.R
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.NavButtonCard

@Preview
@Composable
fun SalesScreen(
    navigateToSaleDessertList: () -> Unit = {},
    navigateToPaymentList:() -> Unit = {}
){
    Scaffold(
        topBar = {BakeryTopAppBar("Realizar una venta")}
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)){
            SalesMenu(
                modifier = Modifier.fillMaxWidth(),
                navigateToSaleDessertList,
                navigateToPaymentList
            )
        }
    }
}

@Composable
fun SalesMenu(
    modifier: Modifier = Modifier,
    navigateToSaleDessertList: () -> Unit,
    navigateToPaymentList:() -> Unit
){
    Column() {

        NavButtonCard(
            onClick = navigateToSaleDessertList,
            icon = R.drawable.ic_sale,
            title = "Realizar una nueva venta"
        )
        NavButtonCard(
            onClick = navigateToPaymentList,
            icon = R.drawable.ic_eye_sales,
            title = "Ver ventas"
        )
    }
}