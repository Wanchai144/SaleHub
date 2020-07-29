package com.example.samplemvp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvp.R
import com.example.samplemvp.model.ResponseDataManu
import java.util.*

class CustomAdapterManuList(
    private var context: Context,
    private var mProductList: ArrayList<ResponseDataManu>,
    private var mDataRespo: String,
    private var mOnClickList: (Int) -> (Unit)

) : RecyclerView.Adapter<CustomAdapterManuList.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_manulist_bar, parent, false
        )
        )
    }

    override fun getItemCount(): Int {
        return mProductList.size
    }


    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.imgicon.setImageResource(mProductList[position].mIconManu)
        holder.tvmanu.text = mProductList[position].mManuName
        if (position == 1){
            holder.visible_id.visibility = View.VISIBLE
        }else {
            holder.visible_id.visibility = View.GONE
        }


        holder.tv_number.text = mDataRespo
        if (mDataRespo == "0"){
            holder.visible_id.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            mOnClickList.invoke(position)
        }
    }


    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val imgicon: ImageView = itemsView.findViewById<ImageView>(R.id.imgiconView)
        val visible_id: RelativeLayout = itemsView.findViewById<RelativeLayout>(R.id.visible_id)
        val tvmanu: TextView = itemsView.findViewById<TextView>(R.id.tvmanu)
        val tv_number: TextView = itemsView.findViewById<TextView>(R.id.tv_number)
    }
}