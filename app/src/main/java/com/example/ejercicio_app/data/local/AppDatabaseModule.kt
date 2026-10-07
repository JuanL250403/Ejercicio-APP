package com.example.ejercicio_app.data.local

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.example.ejercicio_app.data.local.dao.EjercicioActualDao
import com.example.ejercicio_app.data.local.dao.EjercicioDataSetDao
import com.example.ejercicio_app.data.local.dao.RutinaActualDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {

    @Provides
    @Singleton
    fun provideDbInstance(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder<AppDatabase>(context,"ejercicio_db")
            .setDriver(AndroidSQLiteDriver())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRutinaActualDao(db: AppDatabase): RutinaActualDao {
        return db.rutinaActualDao()
    }

    @Provides
    @Singleton
    fun provideEjercicioDatasetDao(db: AppDatabase): EjercicioDataSetDao {
        return db.ejercicioDatasetDao()
    }

    @Provides
    @Singleton
    fun provideEjercicioActualDao(db: AppDatabase): EjercicioActualDao {
        return db.ejercicioActualDao()
    }
}