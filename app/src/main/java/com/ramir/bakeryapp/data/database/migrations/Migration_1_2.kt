package com.ramir.bakeryapp.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(db: SupportSQLiteDatabase) {
        super.migrate(db)
        db.execSQL("""
            CREATE TABLE cart_dessert_additional_ingredient_entity (
                
            )
        """.trimIndent())
    }
}