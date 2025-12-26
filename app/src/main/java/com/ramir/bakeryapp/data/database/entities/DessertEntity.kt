package com.ramir.bakeryapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ramir.bakeryapp.domain.model.Dessert
import java.math.BigDecimal


@Entity(tableName = "dessert_table")
data class DessertEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "unit_available") val unitAvailable: Int = 0,
    @ColumnInfo(name = "price") val price: BigDecimal = BigDecimal("0.00"),
    @ColumnInfo(name = "image_path") val imagePath:String = "",

)

fun Dessert.toEntity() = DessertEntity(id, name, description, unitAvailable,  price, imagePath)