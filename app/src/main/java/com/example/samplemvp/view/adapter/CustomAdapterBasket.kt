package com.example.samplemvp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.samplemvp.R
import com.example.samplemvp.model.BasketModel
import com.example.samplemvp.realm.BasketRealmManager


class CustomAdapterBasket(
    private var context: Context,
    private var mListProduct: List<BasketModel>,
    val mBasketRealmManager: BasketRealmManager = BasketRealmManager(),
    private var contProduct: Double = 0.0,
    private var dicount: Double = 0.0,
    private var inputDiscount: Double = 0.0,
    private var mOnClickList: (Int) -> (Unit)

) : RecyclerView.Adapter<CustomAdapterBasket.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_basket_data,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = mListProduct.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.edt_di.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                holder.edt_di.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {}
                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) {
                            contProduct = if (holder.edt_sumproduct.text.isEmpty()) {
                                1.0
                            } else {
                                holder.edt_sumproduct.text.toString().toInt().toDouble()
                            }

                            inputDiscount = if (s.toString().isEmpty()) {
                                0.0
                            } else {
                                s.toString().toDouble()
                            }

                            holder.sum_product.text =
                                findFinalPrice(
                                    mListProduct[position].prieProducts.toDouble(),
                                    inputDiscount, contProduct
                                ).toString()

                            mBasketRealmManager.update(mListProduct[position].id,findFinalPrice(
                                mListProduct[position].prieProducts.toDouble(),
                                inputDiscount, contProduct
                            ).toInt(),contProduct.toInt())

                            mBasketRealmManager.updateDicount(mListProduct[position].id,inputDiscount.toInt())
                            mOnClickList.invoke(1234)


                        } else {
                            inputDiscount = if (s.toString().isEmpty()) {
                                0.0
                            } else {
                                s.toString().toInt().toDouble()
                            }

                            contProduct = if (holder.edt_sumproduct.text.isEmpty()) {
                                1.0
                            } else {
                                holder.edt_sumproduct.text.toString().toInt().toDouble()
                            }

                            holder.sum_product.text =
                                findFinalPrice(
                                    mListProduct[position].prieProducts.toDouble(),
                                    inputDiscount, contProduct
                                ).toString()


                            mBasketRealmManager.update(mListProduct[position].id,findFinalPrice(
                                mListProduct[position].prieProducts.toDouble(),
                                inputDiscount, contProduct
                            ).toInt(),contProduct.toInt())

                            mBasketRealmManager.updateDicount(mListProduct[position].id,inputDiscount.toInt())

                            mOnClickList.invoke(1234)
                        }

                    }
                })
            } else {

            }
        }

        holder.edt_sumproduct.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {

                holder.edt_sumproduct.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {}
                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().isNotEmpty()) {

                            val inputSumProduct = s.toString().toInt().toDouble()

                            dicount = if (holder.edt_di.text.isEmpty()) {
                                0.0
                            } else {
                                holder.edt_di.text.toString().toDouble()
                            }

                            holder.sum_product.text =
                                findFinalPrice(
                                    mListProduct[position].prieProducts.toDouble(),
                                    dicount
                                    , inputSumProduct
                                ).toString()

                            mBasketRealmManager.update(mListProduct[position].id, findFinalPrice
                                (mListProduct[position].prieProducts.toDouble(), dicount
                                , inputSumProduct
                            ).toInt(),inputSumProduct.toInt())

                            mOnClickList.invoke(1234)

                        } else {
                            dicount = if (holder.edt_di.text.isEmpty()) {
                                0.0
                            } else {
                                holder.edt_di.text.toString().toDouble()
                            }

                            holder.sum_product.text =
                                findFinalPrice(
                                    mListProduct[position].prieProducts.toDouble(),
                                    dicount
                                    , 1.0
                                ).toString()

                            mBasketRealmManager.update(mListProduct[position].id,  findFinalPrice(
                                mListProduct[position].prieProducts.toDouble(),
                                dicount, 1.0).toInt(), 1 )

                            mOnClickList.invoke(1234)
                        }
                    }
                })
            } else {

            }
        }

        Glide.with(context)
            .load(mListProduct[position].imageProduct)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.ivCircleOut)

        holder.tv_product_name.text = mListProduct[position].nameProduct
        holder.tv_prie.text = mListProduct[position].prieProducts.toString()
        holder.sum_product.text = mListProduct[position].sumPrieProducts.toString()
        holder.edt_sumproduct.setText(mListProduct[position].sumProducts.toString())
        holder.edt_di.hint = mListProduct[position].Persen.toString()
        holder.img_delet.setOnClickListener {
            mOnClickList.invoke(position)
        }
    }

    fun findFinalPrice(price: Double, discount: Double, contProduct: Double): Double {
        val mResultPriceProduct = price * contProduct
        return mResultPriceProduct - ((mResultPriceProduct * discount) / 100)
    }

    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {

        val ivCircleOut: ImageView = itemsView.findViewById<ImageView>(R.id.ivCircleOut)
        val img_delet: ImageView = itemsView.findViewById<ImageView>(R.id.img_delet)
        val edt_sumproduct: EditText = itemsView.findViewById<EditText>(R.id.edt_sumproduct)
        val edt_di: EditText = itemsView.findViewById<EditText>(R.id.edt_di)
        val tv_product_name: TextView = itemsView.findViewById<TextView>(R.id.tv_product_name)
        val tv_prie: TextView = itemsView.findViewById<TextView>(R.id.tv_prie)
        val sum_product: TextView = itemsView.findViewById<TextView>(R.id.sum_product)

    }
}
