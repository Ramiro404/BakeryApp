package com.ramir.bakeryapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ramir.bakeryapp.ui.view.CreateNewDessertScreen
import com.ramir.bakeryapp.ui.view.EditDessertFormScreen
import com.ramir.bakeryapp.ui.view.EditDessertScreen
import com.ramir.bakeryapp.ui.view.MainNavScreen
import com.ramir.bakeryapp.ui.view.SaleDessertScreen
import com.ramir.bakeryapp.ui.view.SaleIngredientListSale

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavDestination.MainNavBarDestination){
        composable(
            route = NavDestination.MainNavBarDestination,
            content = { MainNavScreen() }
        )
    }
}