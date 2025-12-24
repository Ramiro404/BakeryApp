package com.ramir.bakeryapp.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class SaveImageViewModel @Inject constructor(): ViewModel() {
    private val _imagePath = MutableStateFlow("")
    val imagePath: StateFlow<String> = _imagePath.asStateFlow()

    var temporaryImageUri by mutableStateOf<Uri?>(null)
        private set

    fun updateTemporaryUri(uri: Uri?) {
        temporaryImageUri = uri
    }


    fun saveImageToInternalStorage(context: Context, uri: Uri): String {
        val fileName = "img_${System.currentTimeMillis()}.jpg"
        val directory = File(context.filesDir, "my_images")
        if (!directory.exists()) directory.mkdirs()

        val file = File(directory, fileName)

        context.contentResolver.openInputStream(uri)?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        _imagePath.update { fileName }
        return file.absolutePath
    }


}