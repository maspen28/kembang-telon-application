package com.code.kembang_telon.view.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.code.kembang_telon.R
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.databinding.ActivityMainBinding
import com.code.kembang_telon.databinding.ActivityRegisterBinding
import com.code.kembang_telon.model.RegisterRequestBody
import com.code.kembang_telon.model.UserPreferences
import com.code.kembang_telon.view.DataSourceManager
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.view.login.LoginDataSource
import com.code.kembang_telon.view.login.LoginViewModel
import com.code.kembang_telon.view.login.dataStore

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupAction()

    }

    private fun setupViewModel(){
        registerViewModel = viewModels<RegisterViewModel> {
            ViewModelFactory.getInstance(application)
        }.value
    }

    private fun setupAction(){
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val address = binding.etAddress.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                name.isEmpty() -> {
                    binding.etName.error = "Masukkan Nama"
                }
                email.isEmpty() -> {
                    binding.etEmail.error = "Masukkan Email"
                }
                address.isEmpty() -> {
                    binding.etAddress.error = "Masukkan Alamat"
                }
                username.isEmpty() -> {
                    binding.etUsername.error = "Masukkan Username"
                }
                password.isEmpty() -> {
                    binding.etPassword.error = "Masukkan Password"
                }
                else -> {
                    val registerRequestBody = RegisterRequestBody(name, email, address, username, password)
                    registerViewModel.postRegister(registerRequestBody)
                    registerViewModel.registerPost.observe(this){ result ->
                        if(result != null){
                            when (result) {
                                is Result.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    val data = result.data
                                    if(data.status == 201){
                                        AlertDialog.Builder(this).apply {
                                            setTitle("Yeah!")
                                            setMessage("Akunnya sudah jadi nih. Yuk, login dan selamat berbelanja!.")
                                            setPositiveButton("Lanjut") { _, _ ->
                                                finish()
                                            }
                                            create()
                                            show()
                                        }

                                    }else{
                                        Toast.makeText(this, data.message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                                is Result.Error -> {
                                    binding.progressBar.visibility = View.GONE
                                    Toast.makeText(this, "Data tidak tersedia!", Toast.LENGTH_SHORT).show()
                                }

                            }
                        }
                    }
                }
            }

        }
    }
}