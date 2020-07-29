package com.example.samplemvp.view.adapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.samplemvp.R
import com.example.samplemvp.model.ResponseDetailProducts

class CustomAdapterImageList(
    private val context: Context,
    private val imageModelArrayList: MutableList<ResponseDetailProducts.Data.ImageProduct>
) : PagerAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageModelArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false)!!

        val imageView = imageLayout
            .findViewById(R.id.image) as ImageView


        Glide.with(context).load(imageModelArrayList[position].image)
            .into(imageView)
        view.addView(imageLayout, 0)

        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }


}