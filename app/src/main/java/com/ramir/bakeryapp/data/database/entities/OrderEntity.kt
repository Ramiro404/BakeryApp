package com.ramir.bakeryapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ramir.bakeryapp.domain.model.Order
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(tableName = "order_table")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "timestamp") val timestamp: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "total") val total: BigDecimal = BigDecimal.ZERO,
    @ColumnInfo(name = "customer_id") val customerId: Int
)

fun OrderEntity.toDomain() = Order(id, timestamp, total, customerId)