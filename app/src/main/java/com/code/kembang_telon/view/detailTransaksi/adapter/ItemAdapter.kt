package com.code.kembang_telon.view.detailTransaksi.adapter

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

class ItemAdapter(private val ctx: Context):RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val cartList: ArrayList<ProductEntity> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val cartView = LayoutInflater.from(ctx).inflate(R.layout.card_item_checkout,parent,false)

        return ItemViewHolder(cartView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {


        val cartItem: ProductEntity = cartList[position]

        holder.itemName.text = cartItem.name
        holder.countTvItem.text = "Jumlah "+  cartItem.qua.toString()
        holder.totalCountTvItem.text = "Rp." + cartItem.price


    }

    override fun getItemCount(): Int {
        return cartList.size
    }



    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val countTvItem: TextView = itemView.findViewById(R.id.countTvItem)
        val totalCountTvItem: TextView = itemView.findViewById(R.id.totalCountTvItem)
    }

    fun updateList(newList: List<ProductEntity>){
        cartList.clear()
        cartList.addAll(newList)
        notifyDataSetChanged()
    }


}