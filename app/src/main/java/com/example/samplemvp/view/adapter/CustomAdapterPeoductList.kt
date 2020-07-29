package com.example.samplemvp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvp.R
import com.example.samplemvp.model.ResponsSeriesList
import java.util.*

class CustomAdapterPeoductList (
    private var context: Context,
    private var mListProduct: ArrayList<ResponsSeriesList.Data>,
    private var mOnClickList: (Int,String,Int) -> (Unit))

    : RecyclerView.Adapter<CustomAdapterPeoductList.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mListProduct.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvOrderNo.text = mListProduct[position].name

        holder.itemView.setOnClickListener {
            mOnClickList.invoke(
                mListProduct[position].id,
                mListProduct[position].name,
                mListProduct[position].categoryId)
        }

    }

    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val tvOrderNo: TextView = itemsView.findViewById<TextView>(R.id.tvOrderNo)

    }
}