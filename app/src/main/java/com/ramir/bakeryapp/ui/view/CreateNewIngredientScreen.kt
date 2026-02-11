package com.ramir.bakeryapp.ui.view

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramir.bakeryapp.domain.model.SaveUiState
import com.ramir.bakeryapp.ui.components.BakeryTopAppBar
import com.ramir.bakeryapp.ui.components.DialogError
import com.ramir.bakeryapp.ui.components.DialogSuccess
import com.ramir.bakeryapp.ui.components.LoadingProgress
import com.ramir.bakeryapp.ui.viewmodel.AdditionalIngredientViewModel
import com.ramir.bakeryapp.ui.viewmodel.SaveImageViewModel
import com.ramir.bakeryapp.utils.SaveResource
import java.math.BigDecimal

@Composable
fun CreateNewIngredientScreen(
    additionalIngredientViewModel: AdditionalIngredientViewModel = hiltViewModel(),
    imageViewModel: SaveImageViewModel = hiltViewModel()) {
    val nameState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }
    val unitAvailableState = remember { mutableIntStateOf(0) }
    val priceState = remember { mutableStateOf(BigDecimal.ZERO) }

    val saveUiState by additionalIngredientViewModel.saveUiState.collectAsStateWithLifecycle(
        SaveUiState())

    val showDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
    //var imageUri by remember { mutableStateOf<Uri?>(null) }
    //var savedPath by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            imageViewModel.updateTemporaryUri(uri)
            //imageUri = it
            //val path = imageViewModel.saveImageToInternalStorage(context, it)
            //savedPath = path
        }
    }

    Scaffold(
        topBar = { BakeryTopAppBar("Crear Nuevo Ingrediente") }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize().verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    value = unitAvailableState.intValue.toString(),
                    onValueChange = { value: String ->
                        if (value.isDigitsOnly()) unitAvailableState.intValue = value.toInt() else unitAvailableState.intValue = 0
                    },
                    label = { Text(text = "Unidades disponibles") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = priceState.value.toString(),
                    onValueChange = {
                        if (it.isDigitsOnly() || it.equals(".")) priceState.value =
                            it.toBigDecimal() else 0
                    },
                    label = { Text(text = "Precio unitario del postre") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                imageViewModel.temporaryImageUri.let { uri ->
                    // Mostrar la imagen (usando Coil)
                    AsyncImage(
                        model = uri, // Si ya se guardó, usa el path; si no, la URI temporal
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier.size(200.dp).clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }



                    Button(onClick = {
                        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }) {
                        Text("Seleccionar Imagen")
                    }

                   /* savedPath?.let {
                        Text("Guardado en: $it", fontSize = 10.sp)
                    }*/

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        imageViewModel.temporaryImageUri?.let { uri ->
                            val imagePath = imageViewModel.saveImageToInternalStorage(context, uri)
                            additionalIngredientViewModel.postIngredient(
                                nameState.value,
                                descriptionState.value,
                                unitAvailableState.value,
                                priceState.value,
                                imagePath
                            )
                            showDialog.value = true
                        }

                    }
                ) {
                    Text(text = "Guardar este nuevo postre")
                }
            }


        }
        when (val resource = saveUiState.saveUiResource) {
            SaveResource.Idle -> {}
            is SaveResource.Error -> {
                DialogError(
                    {
                        showDialog.value = false
                        additionalIngredientViewModel.resetSaveState()},
                    "Ocurrio un error",
                    showDialog.value
                )
            }

            SaveResource.Loading -> LoadingProgress()
            SaveResource.Success -> DialogSuccess(
                {
                    showDialog.value = false
                additionalIngredientViewModel.resetSaveState()},
                "Guadardo con exito!",
                showDialog.value
            )
        }

    }
}