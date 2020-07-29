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
import com.example.samplemvp.model.Responsesubcategory
import java.util.ArrayList

class CustomAdapterSubcategory(

private var context: Context,
private var mListProduct: ArrayList<Responsesubcategory.Data>,
private var mOnClickList: (Int,String,Int) -> (Unit))

: RecyclerView.Adapter<CustomAdapterSubcategory.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_supcategory,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mListProduct.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = mListProduct[position].name
        holder.productcode.text=mListProduct[position].productCode
        holder.tv_prie.text=mListProduct[position].price.toString()

        Glide.with(context)
            .load(mListProduct[position].image)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.ivCircleOut)

        holder.itemView.setOnClickListener {
            mOnClickList.invoke(mListProduct[position].id,mListProduct[position].name,0)
        }

        holder.img_inser_product.setOnClickListener {
            mOnClickList.invoke(1234,"1234",position)
        }

    }

    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        val ivCircleOut: ImageView = itemsView.findViewById<ImageView>(R.id.ivCircleOut)
        val tv_name: TextView = itemsView.findViewById<TextView>(R.id.name)
        val productcode: TextView = itemsView.findViewById<TextView>(R.id.tv_productcode)
        val tv_prie: TextView = itemsView.findViewById<TextView>(R.id.tv_prie)
        val img_inser_product: ImageView = itemsView.findViewById<ImageView>(R.id.img_inser_product)

    }
}
