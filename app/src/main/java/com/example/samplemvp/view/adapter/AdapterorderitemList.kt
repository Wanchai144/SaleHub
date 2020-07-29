package com.example.samplemvp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvp.R
import com.example.samplemvp.model.DataGetid

class AdapterorderitemList(
    private var context: Context,
    private var mOrderproductlist: ArrayList<DataGetid>,
    private var mOnClickList: (Int) -> (Unit)
)

    : RecyclerView.Adapter<AdapterorderitemList.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_byorder,
                parent,
                false
            )
        )


    }

    override fun getItemCount() = mOrderproductlist.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tv_product_name.text  = mOrderproductlist[position].name
        holder.Tv_Status.text  = mOrderproductlist[position].orderStatus
        holder.Tv_DateTime.text  = mOrderproductlist[position].date
        holder.Tv_Time.text  = mOrderproductlist[position].time

        holder.itemView.setOnClickListener {
            mOnClickList.invoke(mOrderproductlist[position].id)
        }
    }

    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val tv_product_name: TextView = itemsView.findViewById<TextView>(R.id.Tv_Codename)
        val Tv_Status: TextView = itemsView.findViewById<TextView>(R.id.Tv_Status)
        val Tv_DateTime: TextView = itemsView.findViewById<TextView>(R.id.Tv_DateTime)
        val Tv_Time: TextView = itemsView.findViewById<TextView>(R.id.Tv_Time)



    }
}