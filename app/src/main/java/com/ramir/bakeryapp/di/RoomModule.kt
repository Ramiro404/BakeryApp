package com.ramir.bakeryapp.di

import android.content.Context
import androidx.room.Room
import com.ramir.bakeryapp.data.database.BakeryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val BAKERY_DATABASE_NAME = "bakery_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            BakeryDatabase::class.java,
            BAKERY_DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideDessertDao(db: BakeryDatabase) = db.getDessertDao()

    @Singleton
    @Provides
    fun provideOrderDao(db: BakeryDatabase) = db.getOrderDao()
}