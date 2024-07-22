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
import com.code.kembang_telon.data.remote.response.DataCart

class ItemAdapter(private val ctx: Context):RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val cartList: ArrayList<DataCart> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val cartView = LayoutInflater.from(ctx).inflate(R.layout.card_item_checkout,parent,false)

        return ItemViewHolder(cartView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {


        val cartItem: DataCart = cartList[position]

        holder.itemName.text = cartItem.namaProduk
        holder.countTvItem.text = "Jumlah "+  cartItem.qty.toString()

        if(cartItem.besarDiskon != null){
            holder.diskonTvItem.text = cartItem.besarDiskon.toString() + "%"
            holder.totalCountTvItem.text = "Rp." + ((cartItem.price!!.toDouble() * cartItem.besarDiskon!!.toDouble() / 100) * cartItem.qty!!.toDouble()).toInt().toString()

        }else{
            holder.diskonTvItem.visibility = View.GONE
            holder.itemDiskon.visibility = View.GONE
            holder.totalCountTvItem.text = "Rp." + (cartItem.price!! * cartItem.qty!!).toString()


        }


    }

    override fun getItemCount(): Int {
        return cartList.size
    }



    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val countTvItem: TextView = itemView.findViewById(R.id.countTvItem)
        val totalCountTvItem: TextView = itemView.findViewById(R.id.totalCountTvItem)
        val diskonTvItem: TextView = itemView.findViewById(R.id.diskonTvItem)
        val itemDiskon: TextView = itemView.findViewById(R.id.itemDiskon)
    }

    fun updateList(newList: List<DataCart>){
        cartList.clear()
        cartList.addAll(newList)
        notifyDataSetChanged()
    }


}