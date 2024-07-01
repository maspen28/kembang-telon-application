package com.code.kembang_telon.view.history.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.code.kembang_telon.R
import com.code.kembang_telon.data.remote.response.DataCart
import com.code.kembang_telon.data.remote.response.OrdersItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryItemAdapter(private val productList: List<OrdersItem>, private val ctx: Context):
    RecyclerView.Adapter<HistoryItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemAdapter.ItemViewHolder {
        val cartView = LayoutInflater.from(ctx).inflate(R.layout.cart_item_history,parent,false)
        return ItemViewHolder(cartView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val cartItem: OrdersItem = productList[position]
        holder.invoice.text = "Invoice: ${cartItem.invoice}"
        holder.status.text = extractTextFromHtml(cartItem.statusLabel!!)
        holder.name.text = "Nama: ${cartItem.customerName}"
        holder.customerPhone.text = "Tlp: ${cartItem.customerPhone}"
        holder.address.text = "Alamat: ${cartItem.customerAddress}"
        holder.created_at.text = "Tgl: " + formatDate(cartItem.createdAt!!)

        holder.nameProduct.text = cartItem.details!![0]!!.product!!.name
        holder.priceProduct.text = "Harga: " + cartItem.details[0]!!.product!!.price.toString()
        holder.qtyProduct.text = "Jumlah: " +cartItem.details[0]!!.qty.toString()
        holder.cost.text = "Total: " + cartItem.cost.toString()
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val invoice = itemView.findViewById<TextView>(R.id.invoice)
        val status = itemView.findViewById<TextView>(R.id.status)
        val name = itemView.findViewById<TextView>(R.id.customer_name)
        val customerPhone = itemView.findViewById<TextView>(R.id.customer_phone)
        val address = itemView.findViewById<TextView>(R.id.customer_address)
        val created_at = itemView.findViewById<TextView>(R.id.created_at)
        val nameProduct = itemView.findViewById<TextView>(R.id.product_name)
        val priceProduct = itemView.findViewById<TextView>(R.id.product_price)
        val qtyProduct = itemView.findViewById<TextView>(R.id.product_qty)
        val cost = itemView.findViewById<TextView>(R.id.total_cost)
    }

    override fun getItemCount(): Int {
        return productList.size
    }


    fun formatDate(inputDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
            val date: Date = inputFormat.parse(inputDate) ?: return "Invalid Date"
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            outputFormat.format(date)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }

    fun extractTextFromHtml(html: String): String {
        return Regex("<.*?>(.*?)</.*?>").find(html)?.groupValues?.get(1) ?: "Invalid HTML"
    }
}