package com.ramir.bakeryapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ramir.bakeryapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogError(
    onDismissRequest: () -> Unit,
    message: String,
    showDialog: Boolean = false
){
    if(showDialog){
        Dialog(
            onDismissRequest = onDismissRequest,
        ){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painterResource(id=R.drawable.ic_close), contentDescription = "Error", tint = Color.Red)
                    Text(text = "Ocurrio un error", fontSize = 24.sp)
                    Text(text = message, fontSize = 18.sp)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = onDismissRequest) {
                        Text(text = "Cerrar")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogSuccess(
    onDismissRequest: () -> Unit,
    message: String,
    showDialog: Boolean = false
){
    if(showDialog){
        Dialog(
            onDismissRequest = onDismissRequest,
        ){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painterResource(id=R.drawable.ic_check), contentDescription = "Check", tint = Color.Green)
                    Text(text = "Correcto!", fontSize = 24.sp)
                    Text(text = message, fontSize = 18.sp)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = onDismissRequest) {
                        Text(text = "Cerrar")
                    }
                }
            }
        }
    }
}