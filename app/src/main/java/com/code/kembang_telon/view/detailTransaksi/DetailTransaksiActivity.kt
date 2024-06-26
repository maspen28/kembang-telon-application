package com.code.kembang_telon.view.detailTransaksi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.code.kembang_telon.MainDataSource
import com.code.kembang_telon.databinding.ActivityDetailTransaksiBinding
import com.code.kembang_telon.model.UserModel
import com.code.kembang_telon.model.UserPreferences
import com.code.kembang_telon.view.DataSourceManager
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.view.detailTransaksi.adapter.ItemAdapter
import com.code.kembang_telon.view.login.dataStore
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.DataCart

class DetailTransaksiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailTransaksiBinding
    private lateinit var detailTransaksiViewModel: DetailTransaksiViewModel
    private lateinit var mainDataSource: MainDataSource
    private lateinit var user: UserModel
    lateinit var itemAdapter: ItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
    }

    private fun setupViewModel(){

        mainDataSource = ViewModelProvider(
            this,
            DataSourceManager(UserPreferences.getInstance(dataStore))
        )[MainDataSource::class.java]

        mainDataSource.getUser().observe(this) { user ->
            this.user = user
            setupContentData()
        }

        detailTransaksiViewModel = viewModels<DetailTransaksiViewModel> {
            ViewModelFactory.getInstance(application)
        }.value
    }

    private fun setupContentData(){
        detailTransaksiViewModel.getCart(user.id)
        binding.tvName.text = user.name
        binding.tvAddress.text = user.alamat
        binding.tvPhoneNumber.text = "08122331633170"

        binding.itemRecView.layoutManager = LinearLayoutManager(this)
        itemAdapter = ItemAdapter(this)
        binding.itemRecView.adapter = itemAdapter

        detailTransaksiViewModel.allCart.observe(this) {result ->
            if(result != null){
                when(result){
                    is Result.Loading ->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success ->{
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        if(data.status == 200){
                            val nonNullCart = data.data?.filterNotNull() ?: emptyList()
                            itemAdapter.updateList(nonNullCart)
                        }
                    }
                    is Result.Error ->{
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }

        }
    }
}