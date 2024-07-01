package com.code.kembang_telon.view.detailTransaksi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.code.kembang_telon.BuildConfig
import com.code.kembang_telon.MainDataSource
import com.code.kembang_telon.R
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.CitiesItem
import com.code.kembang_telon.data.remote.response.DataCart
import com.code.kembang_telon.data.remote.response.DistrictsItem
import com.code.kembang_telon.data.remote.response.ProvincesResponseItem
import com.code.kembang_telon.databinding.ActivityDetailTransaksiBinding
import com.code.kembang_telon.model.UserModel
import com.code.kembang_telon.model.UserPreferences
import com.code.kembang_telon.view.DataSourceManager
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.view.detailTransaksi.adapter.ItemAdapter
import com.code.kembang_telon.view.login.dataStore
import com.code.kembang_telon.view.payments.PaymentMidtransActivity
import com.midtrans.sdk.corekit.api.model.TransactionResult
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.midtrans.sdk.uikit.external.UiKitApi
import com.midtrans.sdk.uikit.internal.util.UiKitConstants


class DetailTransaksiActivity : AppCompatActivity() {
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityDetailTransaksiBinding
    private lateinit var detailTransaksiViewModel: DetailTransaksiViewModel
    private lateinit var mainDataSource: MainDataSource
    private lateinit var user: UserModel
    lateinit var itemAdapter: ItemAdapter

    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerCity: Spinner
    private lateinit var spinnerSubDistrict: Spinner
    private lateinit var spinnerPostalCode: Spinner

    private val ID_MALANG = 257

    private var province_id = 0
    private var city_id = 0
    private var district_id = 0
    private var ongkos_kirim = 0

    private var courier = "jne"
    var sum:Int = 0
    var weight:Int = 0
    lateinit var Item: ArrayList<DataCart>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                result.data?.let {
                    val transactionResult = it.getParcelableExtra<TransactionResult>(UiKitConstants.KEY_TRANSACTION_RESULT)
                    Toast.makeText(this,"${transactionResult?.transactionId}", Toast.LENGTH_LONG).show()
                }
            }
        }
        setupViewModel()

        Item = arrayListOf()

        spinnerProvince = findViewById(R.id.spinnerProvince)
        spinnerCity = findViewById(R.id.spinnerCity)
        spinnerSubDistrict = findViewById(R.id.spinnerSubDistrict)
        spinnerPostalCode = findViewById(R.id.spinnerPostalCode)




    }
    private fun setupProvinceSpinner(provinces: List<ProvincesResponseItem>) {
        val provinceNames = provinces.map { it.name }
        val provinceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinceNames)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerProvince.adapter = provinceAdapter

        binding.spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedProvince = provinces[position]
                val selectedProvinceId = selectedProvince.id
                province_id = selectedProvinceId
                // mendapatkan kota denga id provinsi
                detailTransaksiViewModel.getCity(selectedProvinceId.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun setupCitySpinner(city: List<CitiesItem>) {
        val cities = city.map { it.name }
        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCity.adapter = cityAdapter

        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCity = city[position]
                val selectedCityId = selectedCity.id
                city_id = selectedCityId
                detailTransaksiViewModel.getDistrict(province_id.toString(), selectedCityId.toString())
                detailTransaksiViewModel.getOngkir(ID_MALANG.toString(), city_id.toString(), weight.toString(), courier)


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun setupSubDistrictSpinner(district: List<DistrictsItem>) {
        val subDistricts = district.map { it.name }
        val subDistrictAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subDistricts)
        subDistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSubDistrict.adapter = subDistrictAdapter

        spinnerSubDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedSubDistrict = district[position]
                val selectedSubDistrictId = selectedSubDistrict.id
                district_id = selectedSubDistrictId
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun setupCourierSpinner() {
        val couriers = listOf("jne", "pos")
        val courierAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, couriers)
        courierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPostalCode.adapter = courierAdapter

        spinnerPostalCode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCourier = couriers[position]
                courier = selectedCourier
                detailTransaksiViewModel.getOngkir(ID_MALANG.toString(), city_id.toString(), weight.toString(), courier)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
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
        setupCourierSpinner()
        detailTransaksiViewModel.getCart(user.id)
        detailTransaksiViewModel.getProvinces()
        binding.tvName.text = user.name
        binding.tvAddress.text = user.alamat

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
                            Item.clear()
                            sum = 0
                            weight = 0
                            val nonNullCart = data.data?.filterNotNull() ?: emptyList()
                            itemAdapter.updateList(nonNullCart)

                            Item.addAll(nonNullCart as ArrayList<DataCart>)
                            Item.forEach {
                                sum += it.price!! * it.qty!!
                                weight += it.qty * it.weight!!
                            }

                            binding.totalItem.text = "Rp." + sum
                        }
                    }
                    is Result.Error ->{
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }

        }
        binding.phoneNumberEdit.setOnClickListener {
            showEditDialog()
        }

        detailTransaksiViewModel.provinces.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE

                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        val data = result.data
                        if (data.isNotEmpty()){
                            setupProvinceSpinner(data)
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Provinsi error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        detailTransaksiViewModel.listCity.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE

                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        val data = result.data
                        if (data.cities.isNotEmpty()) {
                            setupCitySpinner(data.cities)
                        }
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                        Toast.makeText(this, "Kota error", Toast.LENGTH_SHORT).show()
                        Log.e("klikitem", "error city = " + result.error)
                    }
                }
            }
        }

        detailTransaksiViewModel.listDistrict.observe(this){result->
            if(result != null){
                when(result){
                    is Result.Loading ->{
                        binding.progressBar.visibility = View.VISIBLE

                    }
                    is Result.Success ->{
                        binding.progressBar.visibility = View.GONE

                        val data = result.data
                        if(data.districts.isNotEmpty()){
                            setupSubDistrictSpinner(data.districts)
                        }
                    }
                    is Result.Error ->{
                        binding.progressBar.visibility = View.GONE

                        Toast.makeText(this, "Kecamatan error", Toast.LENGTH_SHORT).show()
                        Log.e("klikitem", "error city = " + result.error)
                    }
                }
            }
        }

        detailTransaksiViewModel.ongkir.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        val data = result.data
                        if (data.isNotEmpty()) {
                            val consts = data[0]
                            if (consts.costs!!.isNotEmpty()) {
                                val cost = consts.costs[0]!!.cost!![0]!!.value
                                ongkos_kirim = cost!!
                                binding.totalOngkir.text = "Rp. " + cost
                                binding.totalPriceBagFrag.text = "Rp. " + (sum + cost!!).toString()
                            }
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
//                        Toast.makeText(this, "Ongkir error", Toast.LENGTH_SHORT).show()
                        Log.e("klikitem", "ongkir city = " + result.error)

                    }
                }
            }
        }

        detailTransaksiViewModel.checkout.observe(this){result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        if(data.idOrder != null){
                            detailTransaksiViewModel.payment(data.idOrder.toString())
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Checkout error", Toast.LENGTH_SHORT).show()
                        Log.e("klikitem", "error checkout = " + result.error)
                    }
                }
            }
        }

        detailTransaksiViewModel.payment.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        if (data.snapToken != null) {
                            val url = BuildConfig.PAYMENT_URL + "/${data.snapToken}"
                            val intent = Intent(this, PaymentMidtransActivity::class.java)
                            intent.putExtra("URL", url)
                            startActivity(intent)
                        }
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Payment error", Toast.LENGTH_SHORT).show()
                        Log.e("klikitem", "error payment = " + result.error)
                    }
                }
            }
        }

        binding.checkOutPage.setOnClickListener {
            // kasih validasi ongkos kirim, city, province, and disctrit harus lebih dari 0
            if(binding.tvPhoneNumber.text != "wajib diisi*" && province_id > 0 && city_id > 0 && district_id > 0 && ongkos_kirim > 0){
                detailTransaksiViewModel.checkout(user.id, province_id.toString(), city_id.toString(), district_id.toString(), ongkos_kirim.toString(), binding.tvPhoneNumber.text.toString())

            }else Toast.makeText(this, "Data kurang lengkap!", Toast.LENGTH_SHORT).show()
        }
    }



    private fun showEditDialog() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_edit_phone_number, null)
        val editText = view.findViewById<EditText>(R.id.editTextPhoneNumber)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Phone Number")
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                val newPhoneNumber = editText.text.toString()
                binding.tvPhoneNumber.text = newPhoneNumber
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}