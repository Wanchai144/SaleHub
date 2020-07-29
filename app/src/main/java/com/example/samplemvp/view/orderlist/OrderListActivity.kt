package com.example.samplemvp.view.orderlist

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvp.R
import com.example.samplemvp.dialog.DialogPresenter
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.*
import com.example.samplemvp.realm.BasketRealmManager
import com.example.samplemvp.view.adapter.Adapterorderlist
import com.example.samplemvp.view.basket.BasketActivity
import com.example.samplemvp.view.main.ProductCategoryActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.custom_toolbar_view.*
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class OrderListActivity : AppCompatActivity() {
    lateinit var mPreferences: Preferences
    lateinit var mAdapterorderlist: Adapterorderlist
    var mDialogPresenter = DialogPresenter()
    var mOrderpresenter = Orderpresenter()
    var mOrderproductlist: MutableList<drafdetailModel.Drafdetail.Product> = ArrayList()
    val mBasketRealmManager = BasketRealmManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        Realm.init(this)
        initView()
        setapi()
        setadapter()
        setApiDelect()


    }

    private fun initView() {
        imgicon.visibility = View.GONE
//        ic_toorbarView.layBack.setOnClickListener {
//            drawer_layout.openDrawer(GravityCompat.START)
//        }

    }

    private fun setApiDelect() {
        val seriesId: Int = intent.getIntExtra("seriesId", 0)
        btn_delect.setOnClickListener {
            mPreferences = Preferences(this)
            mDialogPresenter.DialogdeleteDraforder(this) {
                mOrderpresenter.DelectPersenterRx(
                    seriesId, mPreferences
                    , this::onSucessDelectPersenter
                    , this::onErrorDelectPersenter
                )
            }

        }
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun onSucessDelectPersenter(delectProfileModel: DelectProfileModel) {
       val myIntent = Intent(this, ProductCategoryActivity::class.java)

            val options =
                ActivityOptions.makeCustomAnimation(
                    this,
                    R.anim.trans_left_in,
                    R.anim.trans_left_out
                )
            this!!.startActivity(myIntent, options.toBundle())
            finish()

    }

    private fun onErrorDelectPersenter(message: String) {
        Log.i("mesegererror", message)

    }

    private fun setapi() {
        val seriesId: Int = intent.getIntExtra("seriesId", 0)
        val name: String = intent.getStringExtra("name")
        tvTitle.text = name
        mPreferences = Preferences(this)
        mOrderpresenter.OrderpresenterRx(
            seriesId, mPreferences
            , this::onSubScriptLoginSuccess
            , this::onSubScriptLoginError
        )
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun onSubScriptLoginSuccess(drafdetailModel: drafdetailModel) {
        visible_notnull.visibility = View.VISIBLE
        mOrderproductlist.addAll(drafdetailModel.data.product)
        Tv_Net.text = drafdetailModel.data.net.toString()
        TV_vatOrder.text = drafdetailModel.data.vat.toString()
        TV_discourtorder.text = drafdetailModel.data.discount.toString()
        Tv_SumPerSenOrder.text = drafdetailModel.data.price.toString()
        TV_NumPersensOrder.text = drafdetailModel.data.priceDiscount.toString()
        TV_vatsOrder.text = drafdetailModel.data.priceVat.toString()
        btn_InBasket.setOnClickListener {
            for (i in mOrderproductlist.indices) {

                val myIntent = Intent(this, BasketActivity::class.java)
                val options =
                    ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.trans_left_in,
                        R.anim.trans_left_out
                    )
                this.startActivity(myIntent, options.toBundle())

                    mBasketRealmManager.insertBasket(
                        mOrderproductlist[i].productId.toString()
                        , mOrderproductlist[i].productImage
                        , mOrderproductlist[i].productName
                        , mOrderproductlist[i].productPrice
                        , mOrderproductlist[i].productAmount
                        , mOrderproductlist[i].productPriceTotal
                        , mOrderproductlist[i].productPercent

                    )

            }
            }
        mAdapterorderlist.notifyDataSetChanged()
    }

    fun onSubScriptLoginError(messageError: String) {
        mDialogPresenter.DialogBar(this, messageError)
    }

    private fun setadapter() {
        mAdapterorderlist = Adapterorderlist(this, mOrderproductlist) {
        }
        recyclerVieworderlist.apply {
            layoutManager = LinearLayoutManager(this@OrderListActivity)
            adapter = mAdapterorderlist
            mAdapterorderlist.notifyDataSetChanged()
        }
    }

}


