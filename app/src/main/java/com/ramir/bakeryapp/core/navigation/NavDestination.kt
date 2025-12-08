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
        val SaleIngredientList:String = "saleIngredientList/{dessertId}"

        fun SaleIngredientListRoute(dessertId: Int) = "editDessertList/$dessertId"
    }

}

