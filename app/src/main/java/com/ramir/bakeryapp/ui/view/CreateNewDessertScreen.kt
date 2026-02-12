package com.ramir.bakeryapp.ui.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.DialogSuccess
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.DessertViewModel
import com.ramir.bakeryapp.ui.viewmodel.SaveImageViewModel
import com.ramir.bakeryapp.utils.SaveResource
import java.math.BigDecimal

@Preview(showBackground = true)
@Composable
fun CreateNewDessertScreen(
    dessertViewModel: DessertViewModel = hiltViewModel(),
    imageViewModel: SaveImageViewModel = hiltViewModel()
) {
    val nameState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }
    val unitAvailableState = remember { mutableStateOf("") }
    val priceState = remember { mutableStateOf("") }

    val saveUiState by dessertViewModel.saveUiState.collectAsStateWithLifecycle()

    val showDialog = remember { mutableStateOf(true) }

    val context = LocalContext.current
    //var imageUri by remember { mutableStateOf<Uri?>(null) }
    //var savedPath by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            imageViewModel.updateTemporaryUri(uri)
            //imageUri = it
            // Guardamos físicamente y obtenemos el path
            //val path = imageViewModel.saveImageToInternalStorage(context, it)
            //savedPath = path
            // Guardamos en Room a través del ViewModel
            //viewModel.saveImagePath(path)
        }
    }

    Scaffold(
        topBar = { BakeryTopAppBar("Crear Nuevo Postre") }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = nameState.value,
                    onValueChange = { nameState.value = it },
                    label = { Text(text = "Nombre del postre") }
                )

                OutlinedTextField(
                    value = descriptionState.value,
                    onValueChange = { descriptionState.value = it },
                    label = { Text(text = "Descripcion del postre") }
                )

                OutlinedTextField(
                    value = unitAvailableState.value,
                    onValueChange = {
                        if (it.isDigitsOnly() || it.isEmpty()) {
                            unitAvailableState.value = it
                        }
                    },
                    label = { Text(text = "Unidades disponibles") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = priceState.value.toString(),
                    onValueChange = {
                        priceState.value = it
                    },
                    label = { Text(text = "Precio unitario del postre") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )


                // Mostrar la imagen (usando Coil)
                imageViewModel.temporaryImageUri.let { uri ->
                    AsyncImage(
                        model = uri, // Si ya se guardó, usa el path; si no, la URI temporal
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }


                Button(onClick = {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                    Text("Seleccionar Imagen")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {

                        imageViewModel.temporaryImageUri?.let { uri ->

                            val imagePath = imageViewModel.saveImageToInternalStorage(context, uri)
                            dessertViewModel.saveNewDessert(
                                nameState.value,
                                descriptionState.value,
                                unitAvailableState.value.toIntOrNull() ?: 0,
                                priceState.value.toBigDecimalOrNull() ?: BigDecimal.ZERO,
                                imagePath
                            )
                        }
                    }
                ) {
                    Text(text = "Guardar este nuevo postre")
                }
            }
        }
        when (val resource = saveUiState.saveUiResource) {
            SaveResource.Idle -> {}
            is SaveResource.Error -> DialogError(
                { showDialog.value = false },
                "Ocurrio un problema, no se pudo guardar el postre",
                showDialog.value
            )

            SaveResource.Loading -> LoadingProgress()
            SaveResource.Success -> DialogSuccess(
                { showDialog.value = false },
                "Guardado correctamente",
                showDialog.value
            )
        }
    }
}
