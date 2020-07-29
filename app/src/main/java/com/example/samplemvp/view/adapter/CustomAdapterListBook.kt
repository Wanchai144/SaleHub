package com.example.samplemvp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvp.R
import com.example.samplemvp.model.ResponseListBook

class CustomAdapterListBook (
    private var context: Context,
    private var mListProduct: ArrayList<ResponseListBook.DataListBook>,
    private var mOnClickList: (Int,String) -> (Unit)

) : RecyclerView.Adapter<CustomAdapterListBook.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_listbook_product,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mListProduct.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = mListProduct[position].name
        holder.tv_time.text = mListProduct[position].time
        holder.tv_day.text = mListProduct[position].date

        holder.itemView.setOnClickListener {
            mOnClickList.invoke(mListProduct[position].id,mListProduct[position].name)
        }
    }

    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val tv_name: TextView = itemsView.findViewById<TextView>(R.id.tv_name_listbook)
        val tv_time: TextView = itemsView.findViewById<TextView>(R.id.tv_time_listbook)
        val tv_day: TextView = itemsView.findViewById<TextView>(R.id.tv_day_listbook)
    }


}