package com.code.kembang_telon.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.code.kembang_telon.data.remote.Repository
import com.code.kembang_telon.di.Injection
import com.code.kembang_telon.view.detailProduct.DetailProductViewModel
import com.code.kembang_telon.view.detailTransaksi.DetailTransaksiViewModel
import com.code.kembang_telon.view.history.HistoryShopViewModel
import com.code.kembang_telon.view.home.HomeViewModel
import com.code.kembang_telon.view.login.LoginViewModel
import com.code.kembang_telon.view.profile.ProfileViewModel
import com.code.kembang_telon.view.register.RegisterViewModel
import com.code.kembang_telon.view.shop.ShopViewModel

class ViewModelFactory private constructor(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
             return HomeViewModel(repository) as T
         }else if (modelClass.isAssignableFrom(DetailProductViewModel::class.java)) {
             return DetailProductViewModel(repository) as T
         }else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
             return RegisterViewModel(repository) as T
         }else if (modelClass.isAssignableFrom(ShopViewModel::class.java)) {
             return ShopViewModel(repository) as T
         }else if (modelClass.isAssignableFrom(DetailTransaksiViewModel::class.java)) {
             return DetailTransaksiViewModel(repository) as T
         }else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
             return ProfileViewModel(repository) as T
         }else if (modelClass.isAssignableFrom(HistoryShopViewModel::class.java)) {
             return HistoryShopViewModel(repository) as T
         }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}