package com.code.kembang_telon.view.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.code.kembang_telon.MainActivity
import com.code.kembang_telon.MainDataSource
import com.code.kembang_telon.R
import com.code.kembang_telon.view.login.dataStore
import com.code.kembang_telon.databinding.FragmentProfileBinding
import com.code.kembang_telon.model.UserModel
import com.code.kembang_telon.model.UserPreferences
import com.code.kembang_telon.view.DataSourceManager
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.view.login.LoginActivity
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var mainDataSource: MainDataSource
    private lateinit var user: UserModel

    lateinit var animationView: LottieAnimationView

    var cards: Int = 0

    lateinit var linearLayout2: LinearLayout
    lateinit var linearLayout3: LinearLayout
    lateinit var linearLayout4: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainDataSource = ViewModelProvider(
            this,
            DataSourceManager(UserPreferences.getInstance(requireActivity().dataStore))
        )[MainDataSource::class.java]

        mainDataSource.getUser().observe(this) { user ->
            this.user = user
            getUserData()
        }

        val factory = ViewModelFactory.getInstance(requireContext())
        profileViewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settingCd_profileFrag = view.findViewById<CardView>(R.id.settingCd_profileFrag)


        val shippingAddressCard_ProfilePage = view.findViewById<CardView>(R.id.shippingAddressCard_ProfilePage)


        binding.logout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle("LogOut")
            builder.setMessage("Apakah yakin mau keluar?")

            builder.setPositiveButton("Yes") { dialog, _ ->
                mainDataSource.logout()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
                dialog.dismiss()
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }



        settingCd_profileFrag.setOnClickListener {
//            val intent = Intent(context, SettingsActivity::class.java)
//            startActivity(intent)
        }

    }

    private fun getUserData(){
        binding.profileNameProfileFrag.text = user.name
        binding.profileEmailProfileFrag.text = user.email
        binding.tvAddress.text = user.alamat
    }

    private fun hideLayout(){
        animationView.playAnimation()
        animationView.loop(true)
        binding.linearLayout2.visibility = View.GONE
        binding.linearLayout3.visibility = View.GONE
        binding.linearLayout4.visibility = View.GONE
        binding.animationView.visibility = View.VISIBLE
    }
    private fun showLayout(){
        animationView.pauseAnimation()
        binding.animationView.visibility = View.GONE
        binding.linearLayout2.visibility = View.VISIBLE
        binding.linearLayout3.visibility = View.VISIBLE
        binding.linearLayout4.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}