package com.example.samplemvp.view.basket

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvp.R
import com.example.samplemvp.dialog.DialogPresenter
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ResponseBasket
import com.example.samplemvp.model.modelProduct
import com.example.samplemvp.realm.BasketRealmManager
import com.example.samplemvp.view.adapter.CustomAdapterBasket
import com.example.samplemvp.view.billingaddress.Billingaddress
import com.example.samplemvp.view.main.ProductCategoryActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_basket.*
import kotlinx.android.synthetic.main.custom_toolbar_view.*
import kotlinx.android.synthetic.main.item_basket_data.*
import java.text.DecimalFormat






@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BasketActivity : AppCompatActivity() {
val mmodelProduct = ArrayList<modelProduct>()
    var mBasketPrester = BasketPrester()
    lateinit var mCustomAdapterBasket: CustomAdapterBasket
    lateinit var mPreferences: Preferences
    val mBasketRealmManager: BasketRealmManager = BasketRealmManager()
    val mData = mBasketRealmManager.findall()
    var mDialogPresenter = DialogPresenter()
    var sumPrie: Double = mBasketRealmManager.sumprie()
    var prie: Double = 0.0

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        Realm.init(this)
        onCilck()
//        myIntent()
        onSetDataBasket()
        onSetApiBasket()
    }


    private fun onSetDataBasket() {
        if (mData.isNotEmpty()) {
            mCustomAdapterBasket =
                CustomAdapterBasket(this, mData) { position ->
                    if (position == 1234) {
                        sumPrie = mBasketRealmManager.sumprie()
                        total_prie.text = sumPrie.toString()
                        sumprie_totalvat.text = sumPrie.toString()

                        edt_di.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(s: Editable) {}
                            override fun beforeTextChanged(
                                s: CharSequence,
                                start: Int,
                                count: Int,
                                after: Int
                            ) {
                            }

                            override fun onTextChanged(
                                s: CharSequence,
                                start: Int,
                                before: Int,
                                count: Int
                            ) {
                                if (s.toString().isNotEmpty()&&edt_discount_bas.text.isNotEmpty()) {
                                    total_prie.text = CaculetorDiscount(sumPrie,s.toString()    .toDouble())
                                }
                            }
                        })
                    } else {
                        mDialogPresenter.Dialogdeiete(this, position) {
                            mBasketRealmManager.deletwById(mData[it].id)

                            sumPrie = mBasketRealmManager.sumprie()
                            total_prie.text = sumPrie.toString()
                            sumprie_totalvat.text = sumPrie.toString()
                            mCustomAdapterBasket.notifyDataSetChanged()

                            if (mData.isEmpty()) {
                                layout_basket_null.visibility = View.VISIBLE
                            }

                        }
                    }
                    }


            recyclerViewBasket.apply {
                layoutManager =
                    GridLayoutManager(this@BasketActivity, 1, LinearLayoutManager.VERTICAL, false)
                adapter = mCustomAdapterBasket
                mCustomAdapterBasket.notifyDataSetChanged()
            }
        } else {
            layout_basket_null.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun onCilck() {
        imgBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
        }

        buySalehub.setOnClickListener {

            for (i in mData.indices)

                mBasketRealmManager.updatePersenVat(
                    mData[i].id, edt_discount_bas.text.toString(), edt_vat.text.toString()
                ,total_prie.text.toString(),sumprie_totalvat.text.toString())
            val myIntent = Intent(this, Billingaddress::class.java)
            val options =
                ActivityOptions.makeCustomAnimation(this, R.anim.trans_left_in, R.anim.trans_left_out)
            this.startActivity(myIntent, options.toBundle())
        }
        prie = CaculetorDiscount(sumPrie, 0.0).toDouble()

        imgBack.setImageResource(R.drawable.chevron)
        tvTitle.text =getString(R.string.Basket)
        total_prie.text = sumPrie.toString()
        sumprie_totalvat.text = sumPrie.toString()
       imgicon.visibility = View.GONE


        edt_discount_bas.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (s.toString().isNotEmpty()) {
                    prie = CaculetorDiscount(sumPrie, s.toString().toDouble()).toDouble()

                    total_prie.text =
                        CaculetorDiscount(sumPrie, s.toString().toDouble())

                    sumprie_totalvat.text =
                        CaculetorDiscount(sumPrie, s.toString().toDouble())

                } else {
                    prie = CaculetorDiscount(sumPrie, 0.0.toString().toDouble()).toDouble()

                    total_prie.text = CaculetorDiscount(sumPrie, 0.toDouble())

                    if (edt_vat.text.toString().isEmpty()) {
                        sumprie_totalvat.text = CaculetorVat(sumPrie, 0.0)
                    } else {
                        sumprie_totalvat.text =
                            CaculetorVat(sumPrie, edt_vat.text.toString().toDouble())
                    }


                }
            }
        })


        edt_vat.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (s.toString().isNotEmpty()) {
                    sumprie_totalvat.text = CaculetorVat(prie, s.toString().toDouble())

                } else {

                    sumprie_totalvat.text = CaculetorVat(prie, 0.0)

                }
            }
        })

    }


    fun CaculetorDiscount(price: Double, discount: Double): String {
        val sumTotalPrice: Double
        val vat: Double = price * discount / 100
        sumTotalPrice = price - vat
        val df2 = DecimalFormat("0")
        return df2.format(sumTotalPrice)
    }

    fun CaculetorVat(price: Double, discount: Double): String {
        val sumTotalPrice: Double
        val vat: Double = price * discount / 100
        sumTotalPrice = price + vat
        val df2 = DecimalFormat("0")
        return df2.format(sumTotalPrice)
    }





    fun onSetApiBasket() {
        mPreferences = Preferences(this)

        inser_basket.setOnClickListener {
            mDialogPresenter.DialogInserBasket(this) {

                val map: HashMap<String, String> = HashMap()
                map["name"] = it
                map["price"] = sumprie_totalvat.text.toString()
                map["net"] = mData[0].sumPrieProducts.toString()
                map["vat"] = edt_vat.text.toString()

                for (i in mData.indices) {
                    map["products[${i}][product_id] "] = mData[i].idproduct
                    map["products[${i}][amount]"] = mData[i].sumProducts.toString()
                    map["products[${i}][percent]"] = mData[i].discount.toString()

                    mBasketPrester.BasketRx(
                        map, mPreferences
                        , this::onSubScriptBasketSuccess
                        , this::onSubScriptBasketError
                    )
                    mBasketRealmManager.delectall()
                }
            }

        }
    }

    fun onSubScriptBasketSuccess(responseBasket: ResponseBasket) {
        val intent = Intent(this, ProductCategoryActivity::class.java)
        startActivity(intent)
        finish()

    }

    fun onSubScriptBasketError(messageError: String) {
        mDialogPresenter.DialogBar(this, messageError)
    }

}
