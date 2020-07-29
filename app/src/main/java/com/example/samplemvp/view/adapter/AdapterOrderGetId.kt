package com.example.samplemvp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplemvp.R
import com.example.samplemvp.model.Responseorderslist
import java.util.ArrayList

class AdapterOrderGetId (
    private var context: Context,
    private var mOrderproductlist: MutableList<Responseorderslist.Dataorder.Product> = ArrayList(),
    private var mOnClickList: (Int) -> (Unit))

    : RecyclerView.Adapter<AdapterOrderGetId.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_orderlistproducts,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mOrderproductlist.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_product_name.text = mOrderproductlist[position].productName
        holder.TV_num.text = mOrderproductlist[position].productAmount.toString()
        holder.sum_product.text = mOrderproductlist[position].productPriceTotal.toString()
        holder.Tv_persen.text = mOrderproductlist[position].productPercent.toString()

        Glide.with(context).load(mOrderproductlist[position].productImage)
            .into(holder.ivCircleOut)

    }

    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val tv_product_name: TextView = itemsView.findViewById<TextView>(R.id.tv_product_name)
        val TV_num: TextView = itemsView.findViewById<TextView>(R.id.TV_num)
        val sum_product: TextView = itemsView.findViewById<TextView>(R.id.sum_product)
        val Tv_persen: TextView = itemsView.findViewById<TextView>(R.id.Tv_persen)
        val ivCircleOut: ImageView = itemsView.findViewById<ImageView>(R.id.ivCircleOut)


    }
}