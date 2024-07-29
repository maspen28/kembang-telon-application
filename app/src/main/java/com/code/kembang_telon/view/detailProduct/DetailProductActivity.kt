package com.code.kembang_telon.view.detailProduct

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.code.kembang_telon.BuildConfig
import com.code.kembang_telon.MainDataSource
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.DetailData
import com.code.kembang_telon.databinding.ActivityDetailProductBinding
import com.code.kembang_telon.model.UserModel
import com.code.kembang_telon.model.UserPreferences
import com.code.kembang_telon.view.DataSourceManager
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.view.login.dataStore

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var detailProductViewModel: DetailProductViewModel
    private lateinit var mainDataSource: MainDataSource
    private lateinit var user: UserModel

    private var qtyProduct = 1
    private var diskon = 0
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()

        val idProduct = intent.getStringExtra(ID_PRODUCT)
        val diskonValue = intent.getStringExtra(DISKON)
        if (idProduct != null) {
            detailProductViewModel.getProduct(idProduct)
        }

        if(diskonValue != null) diskon = diskonValue.toInt()

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
        Glide.with(this)
            .load(BuildConfig.BASE_URL + "/storage/products/${data.image}")
            .into(binding.productImage)

        val cleanDescription = data.description!!.replace("<p>", "").replace("</p>", "")

        binding.productName.text = data.name
        binding.productWeight.text = data.weight.toString()
        binding.productStock.text = data.stock.toString()
        binding.productDescription.text = cleanDescription

        if(diskon > 0){
            total = data.price!! * (100 - diskon) / 100
            binding.productPrice.text = "Rp."+ (data.price!! * (100 - diskon) / 100).toString()
        }else {
            total = data.price!!
            binding.productPrice.text = "Rp."+data.price

        }

        binding.decreaseQtyButton.setOnClickListener {
            if (qtyProduct > 1) {
                qtyProduct--
                binding.qtyText.text = qtyProduct.toString()
                binding.productPrice.text = "Rp."+ (total * qtyProduct).toString()
            }
        }

        binding.increaseQtyButton.setOnClickListener {
            if (qtyProduct < 5) {
                qtyProduct++
                binding.qtyText.text = qtyProduct.toString()
                binding.productPrice.text = "Rp."+ (total * qtyProduct).toString()
            }
        }


        binding.bagButton.setOnClickListener {
            if(qtyProduct > 0){
                addProductToBag(data.id.toString(), user.id , qtyProduct.toString())
            } else {
                Toast.makeText(this, "Inputkan jumlah barangnya!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addProductToBag(product_id: String, customer_id: String, qty: String,) {
        detailProductViewModel.insertCart(product_id, customer_id, qty)
        Toast.makeText(this, "Add to Bag Successfully", Toast.LENGTH_SHORT).show()

    }


    private fun setupViewModel(){
        mainDataSource = ViewModelProvider(
            this,
            DataSourceManager(UserPreferences.getInstance(dataStore))
        )[MainDataSource::class.java]

        mainDataSource.getUser().observe(this) { user ->
            this.user = user
        }

        detailProductViewModel = viewModels<DetailProductViewModel> {
            ViewModelFactory.getInstance(application)
        }.value
    }


    companion object {
        const val ID_PRODUCT = "id_product"
        const val DISKON = "diskon"
    }
}