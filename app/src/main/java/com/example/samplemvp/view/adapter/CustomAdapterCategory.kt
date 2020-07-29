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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.samplemvp.R
import com.example.samplemvp.model.DataOrderList

import java.util.*

class CustomAdapterCategory(
    private var context: Context,
    private var mListProduct: ArrayList<DataOrderList>,
    private var mOnClickList: (String,String) -> (Unit)

) : RecyclerView.Adapter<CustomAdapterCategory.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product_category,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mListProduct.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvOrderNo.text = mListProduct[position].name

        Glide.with(context)
            .load(mListProduct[position].image)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.ivCircleOut)

        holder.itemView.setOnClickListener {
            mOnClickList.invoke(mListProduct[position].id.toString(),mListProduct[position].name)
        }

    }

    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val ivCircleOut: ImageView = itemsView.findViewById<ImageView>(R.id.ivCircleOut)
        val tvOrderNo: TextView = itemsView.findViewById<TextView>(R.id.tvOrderNo)

    }


}