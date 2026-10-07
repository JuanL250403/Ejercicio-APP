package com.example.ejercicio_app.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.ejercicio_app.data.local.dao.EjercicioActualDao
import com.example.ejercicio_app.data.local.dao.EjercicioDataSetDao
import com.example.ejercicio_app.data.local.dao.RutinaActualDao
import com.example.ejercicio_app.data.local.entity.DetalleRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.DetalleRutinaRealizadaEntity
import com.example.ejercicio_app.data.local.entity.DiaSemanaRutinaActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioDataSet
import com.example.ejercicio_app.data.local.entity.EjercicioActualEntity
import com.example.ejercicio_app.data.local.entity.EjercicioRealizadoEntity
import com.example.ejercicio_app.data.local.entity.RutinaActualEntity
import com.example.ejercicio_app.data.local.entity.RutinaRealizadaEntity

@Database(
    entities = [
        RutinaActualEntity::class,
        EjercicioActualEntity::class,
        DetalleRutinaActualEntity::class,
        RutinaRealizadaEntity::class,
        EjercicioRealizadoEntity::class,
        DetalleRutinaRealizadaEntity::class,
        DiaSemanaRutinaActualEntity::class,
        EjercicioDataSet::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rutinaActualDao(): RutinaActualDao
    abstract fun ejercicioDatasetDao(): EjercicioDataSetDao

    abstract fun ejercicioActualDao(): EjercicioActualDao
}
