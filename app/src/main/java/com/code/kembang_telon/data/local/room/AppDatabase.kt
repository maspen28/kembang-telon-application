package com.code.kembang_telon.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.code.kembang_telon.data.local.entity.CardEntity
import com.code.kembang_telon.data.local.entity.ProductEntity


@Database(entities = [ProductEntity::class, CardEntity::class], version = 1,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cardDao(): CardDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_database.db"
                ).build()
            }
    }

}