package com.ramir.bakeryapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(
    val route: String,
    val label:  String,
    val icon: ImageVector,
    val contentDescription: String
){
    INVENTORY("inventory", "Inventario", Icons.Filled.DateRange, "Inventario"),
    SALES("sales", "Venta", Icons.Filled.ShoppingCart, "Venta"),
    USER("user", "Usuario", Icons.Filled.Face, "Usuario"),
}