package com.code.kembang_telon.view.shop.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code.kembang_telon.BuildConfig
import com.code.kembang_telon.data.local.entity.ProductEntity
import com.code.kembang_telon.R
import com.code.kembang_telon.data.remote.response.DataCart
import com.code.kembang_telon.data.remote.response.ProductsItem

class CartAdapter(private val productList: List<DataCart>, private val ctx: Context, val listener: CartItemClickAdapter):RecyclerView.Adapter<CartAdapter.CartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val cartView = LayoutInflater.from(ctx).inflate(R.layout.cart_item_single,parent,false)

        return CartViewHolder(cartView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Log.e("dataerror", "MASUK KE BIND VIEW HOLDER")

        val cartItem: DataCart = productList[position]

        holder.cartName.text = cartItem.namaProduk
        holder.cartPrice.text = "Rp."+ cartItem.price + " x " + cartItem.qty.toString()
        holder.totalTvCart.text = "total : " + (cartItem.price!! * cartItem.qty!!).toString()
        holder.cartMore.setOnClickListener {

        }




        // rubah ip lokal
        Glide.with(ctx)
            .load(BuildConfig.BASE_URL + "/storage/products/${cartItem.image}")
            .into(holder.cartImage)

        holder.cartMore.setOnClickListener {
            listener.onItemDeleteClick(cartItem.id.toString())
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }



    class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val cartImage: ImageView = itemView.findViewById(R.id.cartImage)
        val cartMore: ImageView = itemView.findViewById(R.id.cartMore)
        val cartName: TextView = itemView.findViewById(R.id.cartName)
        val cartPrice: TextView = itemView.findViewById(R.id.cartPrice)
        val totalTvCart: TextView = itemView.findViewById(R.id.totalTvCart)
    }
}

interface CartItemClickAdapter{
    fun onItemDeleteClick(cart_id: String)

}