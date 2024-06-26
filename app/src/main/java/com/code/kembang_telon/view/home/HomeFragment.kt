package com.code.kembang_telon.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.code.kembang_telon.databinding.FragmentHomeBinding
import com.code.kembang_telon.view.ViewModelFactory
import com.code.kembang_telon.data.remote.Result
import com.code.kembang_telon.data.remote.response.ProductsItem
import com.code.kembang_telon.view.home.adapter.ProductAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelFactory.getInstance(requireContext())
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getAllProduct()

        homeViewModel.allProduct.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE

                        val dataResult = result.data
                        if(dataResult.data!!.products!!.isNotEmpty()){
                            Log.e("DATA", "DATA KOSONG")
                            binding.emptyBagMsgLayout.visibility = View.GONE
                            setDataProduct(dataResult.data.products)

                            binding.animationViewCartPage.pauseAnimation()

                        }else{
                            binding.animationViewCartPage.playAnimation()
                            binding.animationViewCartPage.loop(true)
                            binding.emptyBagMsgLayout.visibility = View.VISIBLE
                        }
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Data tidak tersedia!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        showRecyclerList()
    }

    private fun setDataProduct(data: List<ProductsItem?>?) {
        val nonNullProducts = data?.filterNotNull() ?: emptyList()
        val adapter = ProductAdapter(nonNullProducts, requireContext())
        binding.productRecView.adapter = adapter
    }



    private fun showRecyclerList() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.productRecView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.productRecView.addItemDecoration(itemDecoration)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}