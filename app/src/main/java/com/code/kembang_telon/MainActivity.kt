package com.code.kembang_telon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.code.kembang_telon.databinding.ActivityMainBinding
import com.code.kembang_telon.model.UserModel
import com.code.kembang_telon.model.UserPreferences
import com.code.kembang_telon.view.DataSourceManager
import com.code.kembang_telon.view.history.HistoryShopFragment
import com.code.kembang_telon.view.home.HomeFragment
import com.code.kembang_telon.view.login.LoginActivity
import com.code.kembang_telon.view.profile.ProfileFragment
import com.code.kembang_telon.view.register.RegisterActivity
import com.code.kembang_telon.view.shop.ShopFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener  {

    lateinit var bottomNavigationView:BottomNavigationView
    private lateinit var mainDataSource: MainDataSource
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: UserModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mainDataSource = ViewModelProvider(
            this,
            DataSourceManager(UserPreferences.getInstance(dataStore))
        )[MainDataSource::class.java]


        mainDataSource.getUser().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                this.user = user
                Log.e("USER", "MASUK UDAH LOGIN" + user.toString())
                binding = ActivityMainBinding.inflate(layoutInflater)
                setContentView(binding.root)

                bottomNavigationView = findViewById(R.id.bottomNavMenu)
                bottomNavigationView.setOnNavigationItemSelectedListener(this)

                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, HomeFragment() ).commit()


            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeMenu -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.shopMenu -> {
                val fragment = ShopFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.bagMenu -> {
                val fragment = HistoryShopFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.profileMenu -> {
                val fragment = ProfileFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
        }
        return false
    }
}