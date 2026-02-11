package com.ramir.bakeryapp.core.navigation

import kotlinx.serialization.Serializable


 class NavDestination {
    companion object{
        @Serializable
        val MainNavBarDestination:String = "main"

        @Serializable
        val CreateNewDessert:String = "createNewDessert"

        @Serializable
        val EditDessertList:String = "editDessertList"

        @Serializable
        val EditDessertForm:String = "editDessertList/{id}"

        fun EditDessertFormRoute(id:String) = "editDessertList/$id"

        @Serializable
        val DessertList:String = "dessertList"

        @Serializable
        val IngredientList:String = "ingredientList"

        @Serializable
        val EditIngredient:String = "ingredientEdit"

        @Serializable
        val CreateIngredient:String = "ingredientCreate"


        @Serializable
        val SaleDessertList:String = "saleDessertList"

        @Serializable
        val SaleIngredientList:String = "saleDessertList/{dessertId}"

        fun SaleIngredientListRoute(dessertId: String) = "saleDessertList/$dessertId"

        @Serializable
        val Payment = "purchase"

        @Serializable
        val PaymentList:String = "payment"

        @Serializable
        val PaymentDetail:String = "${PaymentList}/{orderId}"

        fun PaymentDetailRoute(orderId: String) = "${PaymentList}/${orderId}"

        @Serializable
        val RemoveDessert = "removeDessert"

        @Serializable
        val RemoveIngredient = "removeIngredient"
    }

}

