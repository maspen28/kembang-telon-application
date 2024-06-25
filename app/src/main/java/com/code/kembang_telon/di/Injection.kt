package com.code.kembang_telon.di

import android.content.Context
import com.code.kembang_telon.data.local.room.AppDatabase
import com.code.kembang_telon.data.remote.Repository
import com.code.kembang_telon.data.remote.retrofit.ApiConfig
import java.util.concurrent.Executors

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = AppDatabase.getInstance(context)
        val cardDao = database.cardDao()
        val productDao = database.productDao()
        val executorService = Executors.newSingleThreadExecutor()
        return Repository.getInstance(apiService, cardDao, productDao, executorService)
    }
}