package com.code.kembang_telon.view.shop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.code.kembang_telon.data.local.entity.ProductEntity
import com.code.kembang_telon.R

class CartAdapter(private val ctx: Context, val listener: CartItemClickAdapter):RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private val cartList: ArrayList<ProductEntity> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val cartView = LayoutInflater.from(ctx).inflate(R.layout.cart_item_single,parent,false)

        return CartViewHolder(cartView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {


        val cartItem: ProductEntity = cartList[position]

        holder.cartName.text = cartItem.name
        holder.cartPrice.text = "Rp."+ cartItem.price + " x " + cartItem.qua.toString()
        holder.totalTvCart.text = "total : " + (cartItem.price * cartItem.qua).toString()
        holder.cartMore.setOnClickListener {

        }

        // url gambar harus ip local yang sama dengan penambahan produk di website
        // kalau ip nya sama tingaal ubah correctedImageUrl menajdi cartItem.Image pada load di Glidenya
        val correctedImageUrl = cartItem.Image.replace("127.0.0.1", "192.168.0.3")


        Glide.with(ctx)
            .load(correctedImageUrl)
            .into(holder.cartImage)

        holder.cartMore.setOnClickListener {
            listener.onItemDeleteClick(cartItem)
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }



    class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val cartImage: ImageView = itemView.findViewById(R.id.cartImage)
        val cartMore: ImageView = itemView.findViewById(R.id.cartMore)
        val cartName: TextView = itemView.findViewById(R.id.cartName)
        val cartPrice: TextView = itemView.findViewById(R.id.cartPrice)
        val totalTvCart: TextView = itemView.findViewById(R.id.totalTvCart)
    }

    fun updateList(newList: List<ProductEntity>){
        cartList.clear()
        cartList.addAll(newList)
        notifyDataSetChanged()
    }


}

interface CartItemClickAdapter{
    fun onItemDeleteClick(product: ProductEntity)
    fun onItemUpdateClick(product: ProductEntity)

}