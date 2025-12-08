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
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar

@Composable
fun SalesScreen(
    navigateToSaleDessertList: () -> Unit
){
    Scaffold(
        topBar = {BakeryTopAppBar("Realizar una venta")}
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)){
            SalesMenu(
                modifier = Modifier.fillMaxWidth(),
                navigateToSaleDessertList
            )
        }
    }
}

@Composable
fun SalesMenu(
    modifier: Modifier = Modifier,
    navigateToSaleDessertList: () -> Unit
){
    Column() {
        OutlinedButton(
            onClick = {  }
        ) {
            Text(text = "Realizar una nueva venta")
        }

        OutlinedButton(
            onClick = navigateToSaleDessertList
        ) {
            Text(text = "Ver ventas")
        }
    }
}