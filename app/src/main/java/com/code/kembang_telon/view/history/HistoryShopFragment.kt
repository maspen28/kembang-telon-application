package com.code.kembang_telon.view.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.code.kembang_telon.MainDataSource
import com.code.kembang_telon.R
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.DataCart
import com.code.kembang_telon.data.remote.response.OrdersItem
import com.code.kembang_telon.databinding.FragmentHistoryShopBinding
import com.code.kembang_telon.databinding.FragmentShopBinding
import com.code.kembang_telon.model.UserModel
import com.code.kembang_telon.model.UserPreferences
import com.code.kembang_telon.view.DataSourceManager
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.view.detailTransaksi.DetailTransaksiActivity
import com.code.kembang_telon.view.history.adapter.HistoryItemAdapter
import com.code.kembang_telon.view.login.dataStore
import com.code.kembang_telon.view.shop.ShopViewModel
import com.code.kembang_telon.view.shop.adapter.CartAdapter


class HistoryShopFragment : Fragment() {

    private var _binding: FragmentHistoryShopBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainDataSource: MainDataSource
    private lateinit var user: UserModel

    lateinit var animationView: LottieAnimationView

    private lateinit var historyShopViewModel: HistoryShopViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainDataSource = ViewModelProvider(
            this,
            DataSourceManager(UserPreferences.getInstance(requireActivity().dataStore))
        )[MainDataSource::class.java]


        val factory = ViewModelFactory.getInstance(requireContext())
        historyShopViewModel = ViewModelProvider(this, factory).get(HistoryShopViewModel::class.java)

        mainDataSource.getUser().observe(this) { user ->
            this.user = user
            historyShopViewModel.getHistory(user.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animationView = view.findViewById(R.id.animationViewCartPage)


        historyShopViewModel.history.observe(viewLifecycleOwner){ result->
            if(result != null){
                when(result){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE

                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        if(data.status == "success"){
                            binding.emptyBagMsgLayout.visibility = View.GONE

                            setUpdateList(data.orders)

                            animationView.pauseAnimation()

                        }else{
                            Toast.makeText(requireContext(), "Silahkan berbelanja!", Toast.LENGTH_SHORT).show()
                            animationView.playAnimation()
                            animationView.loop(true)
                            binding.emptyBagMsgLayout.visibility = View.VISIBLE
                        }

                    }
                    is Result.Error -> {
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



        showRecyclerList()
    }

    private fun setUpdateList(data: List<OrdersItem?>?) {
        val nonNullCart = data?.filterNotNull() ?: emptyList()
        binding.cartRecView.layoutManager = LinearLayoutManager(context)
        val ItemAdapter = HistoryItemAdapter(nonNullCart,activity as Context )
        binding.cartRecView.adapter = ItemAdapter
    }

    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(context)
        binding.cartRecView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.cartRecView.addItemDecoration(itemDecoration)
    }

}