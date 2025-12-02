package com.ramir.bakeryapp.core.navigation

import kotlinx.serialization.Serializable


 class NavDestination {
    companion object{
        @Serializable
        val CreateNewDessert:String = "createNewDessert"

        @Serializable
        val EditDessertList:String = "editDessertList"

        @Serializable
        val EditDessertForm:String = "editDessertList/{id}"

        fun EditDessertFormRoute(id:String) = "editDessertList/$id"

    }

}

