package com.code.kembang_telon.view.shop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.code.kembang_telon.MainDataSource
import com.code.kembang_telon.R
import com.code.kembang_telon.data.local.entity.ProductEntity
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.DataCart
import com.code.kembang_telon.databinding.FragmentShopBinding
import com.code.kembang_telon.model.UserModel
import com.code.kembang_telon.model.UserPreferences
import com.code.kembang_telon.view.DataSourceManager
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.view.detailTransaksi.DetailTransaksiActivity
import com.code.kembang_telon.view.login.dataStore
import com.code.kembang_telon.view.shop.adapter.CartAdapter
import com.code.kembang_telon.view.shop.adapter.CartItemClickAdapter

class ShopFragment : Fragment(), CartItemClickAdapter {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainDataSource: MainDataSource
    private lateinit var user: UserModel

    lateinit var animationView: LottieAnimationView
    lateinit var Item: ArrayList<DataCart>

    private lateinit var shopViewModel: ShopViewModel
    lateinit var cartAdapter: CartAdapter

    var sum:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainDataSource = ViewModelProvider(
            this,
            DataSourceManager(UserPreferences.getInstance(requireActivity().dataStore))
        )[MainDataSource::class.java]


        val factory = ViewModelFactory.getInstance(requireContext())
        shopViewModel = ViewModelProvider(this, factory).get(ShopViewModel::class.java)

        mainDataSource.getUser().observe(this) { user ->
            this.user = user
            shopViewModel.getCart(user.id)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animationView = view.findViewById(R.id.animationViewCartPage)


        Item = arrayListOf()

        shopViewModel.allCart.observe(viewLifecycleOwner){ result->
            if(result != null){
                when(result){
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE

                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        if(data.status == 200){
                            binding.emptyBagMsgLayout.visibility = View.GONE
                            Item.clear()
                            sum = 0
                            Item.addAll(data.data as ArrayList<DataCart>)
                            setUpdateList(data.data)
                            Item.forEach {
                                if(it.besarDiskon != null){
                                    sum += (it.price!! * it.besarDiskon.toInt() / 100) * it.qty!!
                                }else sum += it.price!! * it.qty!!
                            }

                            binding.totalPriceBagFrag.text = "Rp." + sum
                            animationView.pauseAnimation()

                        }else{
                            Item.clear()
                            sum = 0
                            binding.totalPriceBagFrag.text = "Rp." + sum
                            setUpdateList(data.data)
                            Toast.makeText(requireContext(), "Silahkan pilih item!", Toast.LENGTH_SHORT).show()
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

        shopViewModel.deleteCart.observe(viewLifecycleOwner) { result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if(result.data.status == 200){
                        shopViewModel.getCart(user.id)
                        Toast.makeText(requireContext(), "Data Berhasil dihapus", Toast.LENGTH_SHORT).show()
                    }
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.checkOutBagPage.setOnClickListener {
            if(Item.isNotEmpty()){
                val intent = Intent(requireActivity(), DetailTransaksiActivity::class.java)
                startActivity(intent)
            }else Toast.makeText(requireContext(), "Silahkan pilih item terlebih dahulu!", Toast.LENGTH_SHORT).show()

        }

        showRecyclerList()
    }

    private fun setUpdateList(data: List<DataCart?>?) {

        val nonNullCart = data?.filterNotNull() ?: emptyList()
        binding.cartRecView.layoutManager = LinearLayoutManager(context)
        cartAdapter = CartAdapter(nonNullCart,activity as Context, this )
        binding.cartRecView.adapter = cartAdapter
    }

    private fun getDataCart(customer_id: String){
        shopViewModel.getCart(customer_id)
    }



    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(context)
        binding.cartRecView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.cartRecView.addItemDecoration(itemDecoration)
    }

    override fun onItemDeleteClick(cart_id: String) {
        shopViewModel.deleteCart(cart_id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}