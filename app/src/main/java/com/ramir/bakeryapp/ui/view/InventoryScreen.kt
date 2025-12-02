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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ramir.bakeryapp.R

@Preview(showBackground = true)
@Composable
fun InventoryScreen(
    navigateToCreateNewDessert: () -> Unit,
    navigateToEditDessert: () -> Unit
){
    Scaffold { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)){
            InventoryMenu(
                modifier = Modifier.fillMaxWidth(),
                navigateToCreateNewDessert,
                navigateToEditDessert,
            )
        }
    }
}


@Composable
private fun InventoryMenu(
    modifier: Modifier = Modifier,
    navigateToCreateNewDessert: () -> Unit,
    navigateToEditDessert: () ->  Unit
    ){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = {
                navigateToCreateNewDessert
            }
        ) {
            Text(text = stringResource(R.string.add_new_dessert))
        }

        OutlinedButton(
            onClick = {
                navigateToEditDessert()
            }
        ) {
            Text(text = stringResource(R.string.edit_dessert))
        }

        OutlinedButton(
            onClick = {}
        ) {
            Text(text = stringResource(R.string.list_desserts))
        }

        OutlinedButton(
            onClick = {}
        ) {
            Text(text = stringResource(R.string.delete_dessert))
        }
    }
}


