package com.code.kembang_telon.view.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code.kembang_telon.R
import com.code.kembang_telon.data.remote.response.ProductsItem
import com.code.kembang_telon.view.detailProduct.DetailProductActivity

class ProductAdapter(private val productList: List<ProductsItem>, context: Context): RecyclerView.Adapter<ProductAdapter.ViewHolder>()  {

    val ctx: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val productView = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(productView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = productList[position]
        holder.productName_singleProduct.text = product.name
        holder.productPrice_singleProduct.text = "Rp."+product.price


        Glide.with(ctx)
            .load("http://192.168.0.7:8000/storage/products/${product.image}")
            .into(holder.productImage_singleProduct)


        holder.itemView.setOnClickListener {
            goDetailsPage(product.id)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val productImage_singleProduct: ImageView = itemView.findViewById(R.id.productImage_singleProduct)
        val productName_singleProduct: TextView = itemView.findViewById(R.id.productName_singleProduct)
        val productPrice_singleProduct: TextView = itemView.findViewById(R.id.productPrice_singleProduct)


    }

    private fun goDetailsPage(id: Int?) {
        val intent = Intent(ctx , DetailProductActivity::class.java)
        intent.putExtra(DetailProductActivity.ID_PRODUCT, id.toString())
        ctx.startActivity(intent)
    }
}