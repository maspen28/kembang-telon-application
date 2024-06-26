package com.code.kembang_telon.view.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.code.kembang_telon.MainActivity
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.databinding.ActivityLoginBinding
import com.code.kembang_telon.model.LoginRequestBody
import com.code.kembang_telon.model.UserModel
import com.code.kembang_telon.model.UserPreferences
import com.code.kembang_telon.view.DataSourceManager
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.view.register.RegisterActivity


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginDataSource: LoginDataSource
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupAction()
    }

    private fun setupViewModel(){
        loginDataSource = ViewModelProvider(
            this,
            DataSourceManager(UserPreferences.getInstance(dataStore))
        )[LoginDataSource::class.java]

        loginDataSource.getUser().observe(this) { user ->
            this.user = user
        }
        loginViewModel = viewModels<LoginViewModel> {
            ViewModelFactory.getInstance(application)
        }.value
    }

    private fun setupAction() {

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            when {
                username.isEmpty() -> {
                    Toast.makeText(this, "Username tidak boleh kosong!", Toast.LENGTH_SHORT).show()

                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()

                }

                else -> {
                    val loginRequestBody = LoginRequestBody(username, password)

                    loginViewModel.postLogin(loginRequestBody)
                    loginViewModel.loginPost.observe(this) { result ->
                        if(result != null){
                            when (result) {
                                is Result.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    val data = result.data
                                    if(data.status == 200){
                                        loginDataSource.saveUser(UserModel(data.data!!.id.toString(),data.data.name!!, data.data.email!!, data.data.address!!, phoneNumber = if(data.data.phoneNumber == null) "" else data.data.phoneNumber.toString(), username = data.data.username!!, isLogin = true))

                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()

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

    fun clickToRegister(view: View) {
        val intent = Intent(  this, RegisterActivity::class.java)
        startActivity(intent)
    }

}