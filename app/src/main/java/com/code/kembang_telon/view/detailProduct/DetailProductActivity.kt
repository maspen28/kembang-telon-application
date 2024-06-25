package com.code.kembang_telon.view.detailProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.code.kembang_telon.R
import com.code.kembang_telon.data.local.entity.ProductEntity
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.DataItem
import com.code.kembang_telon.data.remote.response.DetailData
import com.code.kembang_telon.data.remote.response.DetailProductResponse
import com.code.kembang_telon.databinding.ActivityDetailProductBinding
import com.code.kembang_telon.model.UserPreferences
import com.code.kembang_telon.view.DataSourceManager
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.view.login.LoginDataSource
import com.code.kembang_telon.view.login.LoginViewModel
import com.code.kembang_telon.view.login.dataStore

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var detailProductViewModel: DetailProductViewModel

    private var qtyProduct = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        val idProduct = intent.getStringExtra(ID_PRODUCT)
        if (idProduct != null) {
            detailProductViewModel.getProduct(idProduct)
        }

        detailProductViewModel.product.observe(this) { result ->

            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        setupContentData(data.data!!)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Data tidak tersedia!", Toast.LENGTH_SHORT).show()
                    }

                }
            }

        }

    }

    private fun setupContentData(data: DetailData) {
        val correctedImageUrl = data.image!!.replace("127.0.0.1", "192.168.0.3")
        Glide.with(this)
            .load(correctedImageUrl)
            .into(binding.productImage)

        val cleanDescription = data.description!!.replace("<p>", "").replace("</p>", "")

        binding.productName.text = data.name
        binding.productPrice.text = "Rp."+data.price
        binding.productWeight.text = data.weight.toString()
        binding.productStock.text = data.stock.toString()
        binding.productDescription.text = cleanDescription

        binding.decreaseQtyButton.setOnClickListener {
            if (qtyProduct > 1) {
                qtyProduct--
                binding.qtyText.text = qtyProduct.toString()
            }
        }

        binding.increaseQtyButton.setOnClickListener {
            if (qtyProduct < 5) {
                qtyProduct++
                binding.qtyText.text = qtyProduct.toString()
            }
        }


        binding.bagButton.setOnClickListener {
            if(qtyProduct > 0){
                addProductToBag(data.name!!, qtyProduct, data.price!!.toInt(), data.id!!.toString(), data.image!!)
            } else {
                Toast.makeText(this, "Inputkan jumlah barangnya!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addProductToBag(pName: String, qua: Int, pPrice: Int, pPid: String, pImage: String) {

        detailProductViewModel.insert(ProductEntity(pName, qua, pPrice, pPid, pImage))
        Toast.makeText(this, "Add to Bag Successfully", Toast.LENGTH_SHORT).show()

    }


    private fun setupViewModel(){
        detailProductViewModel = viewModels<DetailProductViewModel> {
            ViewModelFactory.getInstance(application)
        }.value
    }


    companion object {
        const val ID_PRODUCT = "id_product"
    }
}