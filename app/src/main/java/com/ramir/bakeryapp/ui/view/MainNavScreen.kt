package com.ramir.bakeryapp.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ramir.bakeryapp.core.navigation.NavDestination
import com.ramir.bakeryapp.ui.Destination

@Preview
@Composable
fun MainNavScreen(){
    val navController = rememberNavController()
    val startDestination = Destination.SALES
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.route)
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = { Text(text = destination.label) }
                    )
                }
            }
        }) { paddingValues ->
        AppNavHost(
            modifier = Modifier.padding(paddingValues),
            startDestination = startDestination,
            navController= navController
        )
    }
}

@Composable
private fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: Destination,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier
    ){
        Destination.entries.forEach { destination ->
            composable(destination.route){
                when(destination) {
                    Destination.INVENTORY -> InventoryScreen(
                        navigateToCreateNewDessert = {
                            navController.navigate(route = NavDestination.CreateNewDessert) },
                        navigateToEditDessert = {
                            navController.navigate(route = NavDestination.EditDessertList)
                        },
                        navigateToListIngredients = {
                            navController.navigate(route = NavDestination.IngredientList)
                        },
                        navigateToEditIngredients = {
                            navController.navigate( route = NavDestination.EditIngredient)
                        },
                        navigateToCreateIngredients = {
                            navController.navigate( route = NavDestination.CreateIngredient)
                        },

                    )
                    Destination.SALES -> SalesScreen(
                        navigateToSaleDessertList = {
                            navController.navigate( route = NavDestination.SaleDessertList)
                        }
                    )
                    Destination.USER -> TODO()
                }
            }
        }
    }
}