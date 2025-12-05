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
import com.ramir.bakeryapp.ui.view.SaleDessertScreen
import com.ramir.bakeryapp.ui.view.SaleIngredientlist

@Composable
fun NavigationWrapper(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavDestination.CreateNewDessert){
        composable(
            route = NavDestination.CreateNewDessert,
            content = { CreateNewDessertScreen() }
        )
        composable(
            route = NavDestination.EditDessertList,
            content = {
                EditDessertScreen(
                    onEditDessert={id->navController.navigate(NavDestination.EditDessertFormRoute(id))}
                )
            }
        )
        composable(
            route = NavDestination.EditDessertForm,
            arguments = listOf(
                navArgument("id"){
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if(id != null){
                EditDessertFormScreen(dessertId = id)
            }else{

            }
        }

        composable(
            route = NavDestination.SaleDessertList,
            content = {
                SaleDessertScreen(
                    onAddIngredients={dessertId -> navController.navigate(NavDestination.SaleIngredientListRoute(dessertId))}
                )
            }
        )

        composable(
            route= NavDestination.SaleIngredientList,
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                }
            )
        ){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if(id != null){
                SaleIngredientlist(id)
            }
        }
    }
}