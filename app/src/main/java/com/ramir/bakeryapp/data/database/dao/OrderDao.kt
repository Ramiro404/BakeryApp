package com.ramir.bakeryapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ramir.bakeryapp.data.database.entities.OrderEntity
import com.ramir.bakeryapp.data.database.relations.OrderDetail

@Dao
interface OrderDao {
    @Query("SELECT * FROM order_table ")
    suspend fun getAllOrder(): List<OrderEntity>

    @Query("""
        SELECT o.*, 
        c.id AS cust_id, c.name AS cust_name, c.last_name AS cust_last_name, c.phone_number AS cust_phone_number,
        od.id AS od_id, od.order_id AS od_order_id, od.dessert_additional_ingredient_id AS od_dessert_additional_ingredient_id, od.total AS od_total,
        di.id AS di_id, di.dessert_id AS di_dessert_id, di.additional_ingredient_id AS di_additional_ingredient_id, di.additional_ingredient_quantity AS di_additional_ingredient_quantity, di.item_number AS di_item_number,
        d.id AS d_id, d.name AS d_name, d.description AS d_description, d.unit_available AS d_unit_available, d.price AS d_price, d.image_path AS d_image_path,
        ai.id AS ai_id, ai.name AS ai_name, ai.description AS ai_description, ai.unit_available AS ai_unit_available, ai.price AS ai_price, ai.image_path AS ai_image_path
        FROM order_table AS o
        
        INNER JOIN customer_table AS c
        ON o.customer_id = 0
        
        INNER JOIN order_dessert_table AS od
        ON od.order_id = o.id
        
        INNER JOIN dessert_additional_ingredient_entity AS di
        ON od.dessert_additional_ingredient_id = di.id
        
        INNER JOIN dessert_table AS d
        ON di.dessert_id = d.id
        
        INNER JOIN additional_ingredient_table AS ai
        ON di.additional_ingredient_id  = ai.id
        
        WHERE o.id = :id

    """)
    suspend fun getOrderById(id: Int): List<OrderDetail>

    @Insert
    suspend fun insertOrder(orderEntity: OrderEntity): Long

    @Update
    suspend fun updateOrder(orderEntity: OrderEntity): Int

    @Query("DELETE FROM order_table WHERE id=:id")
    suspend fun deleteOrderById(id: Int)
}