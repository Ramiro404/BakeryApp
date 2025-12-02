package com.ramir.bakeryapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ramir.bakeryapp.ui.view.CreateNewDessertScreen
import com.ramir.bakeryapp.ui.view.EditDessertFormScreen
import com.ramir.bakeryapp.ui.view.EditDessertScreen

@Composable
fun NavigationWrapper(){
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
                EditDessertFormScreen(id)
            }else{

            }
        }
    }
}