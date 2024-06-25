package com.code.kembang_telon.view.shop

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.code.kembang_telon.R
import com.code.kembang_telon.data.local.entity.ProductEntity
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.databinding.FragmentShopBinding
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.view.detailProduct.DetailProductActivity
import com.code.kembang_telon.view.detailTransaksi.DetailTransaksiActivity
import com.code.kembang_telon.view.shop.adapter.CartAdapter
import com.code.kembang_telon.view.shop.adapter.CartItemClickAdapter

@Suppress("UNREACHABLE_CODE")
class ShopFragment : Fragment(), CartItemClickAdapter {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    lateinit var animationView: LottieAnimationView
    lateinit var Item: ArrayList<ProductEntity>

    private lateinit var shopViewModel: ShopViewModel
    lateinit var cartAdapter: CartAdapter

    var sum:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        shopViewModel = ViewModelProvider(this, factory).get(ShopViewModel::class.java)
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

        animationView.playAnimation()
        animationView.loop(true)
        binding.bottomCartLayout.visibility = View.GONE
        binding.MybagText.visibility = View.GONE
        binding.emptyBagMsgLayout.visibility = View.VISIBLE

        binding.cartRecView.layoutManager = LinearLayoutManager(context)
        cartAdapter = CartAdapter(activity as Context, this )
        binding.cartRecView.adapter = cartAdapter

        shopViewModel.allproducts.observe(viewLifecycleOwner) {result ->


            result?.let {
                cartAdapter.updateList(it)
                Item.clear()
                sum = 0
                Item.addAll(it)
            }

            if (result.size == 0){
                animationView.playAnimation()
                animationView.loop(true)
                binding.bottomCartLayout.visibility = View.GONE
                binding.MybagText.visibility = View.GONE
                binding.emptyBagMsgLayout.visibility = View.VISIBLE

            }
            else{
                binding.emptyBagMsgLayout.visibility = View.GONE
                binding.bottomCartLayout.visibility = View.VISIBLE
                binding.MybagText.visibility = View.VISIBLE
                animationView.pauseAnimation()
            }

            Item.forEach {
                sum += it.price * it.qua
            }
            binding.totalPriceBagFrag.text = "Rp." + sum
        }

        binding.checkOutBagPage.setOnClickListener {
            val intent = Intent(requireActivity(), DetailTransaksiActivity::class.java)
            startActivity(intent)
        }

        showRecyclerList()
    }



    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(context)
        binding.cartRecView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.cartRecView.addItemDecoration(itemDecoration)
    }


    override fun onItemDeleteClick(product: ProductEntity) {
        shopViewModel.deleteCart(product)
        Toast.makeText(context,"Removed From Bag", Toast.LENGTH_SHORT).show()
    }

    override fun onItemUpdateClick(product: ProductEntity) {
        shopViewModel.updateCart(product)
    }

}