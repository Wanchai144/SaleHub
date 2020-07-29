package com.example.samplemvp.view.listorder

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvp.R
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.Responseorderslist
import com.example.samplemvp.view.adapter.AdapterOrderGetId
import kotlinx.android.synthetic.main.activity_order_list2.*
import kotlinx.android.synthetic.main.custom_toolbar_view.*



class OrderList : AppCompatActivity() {
    lateinit var mPreferences: Preferences
    val mOrderListPresenter = OrderListPresenter()
    lateinit var mAdapterOrderGetId: AdapterOrderGetId
    val mArrayOrderListGetid: MutableList<Responseorderslist.Dataorder.Product> = ArrayList()
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list2)
        setAdapter()
        onSetApi()
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun initView() {

        imgBack.setImageResource(R.drawable.back)
        imgBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
        }
        imgicon.visibility = View.GONE
        tvTitle.text = "รายการสั่งซื้อสินค้า"

    }

    private fun onSetApi() {
        val id: Int = intent.getIntExtra("id", 0)
        mPreferences = Preferences(this)
        mOrderListPresenter.OrderListPresenterRx(
            id, mPreferences
            , this::onSubScriptOrderListPresenterSuccess
            , this::onSubScriptOrderListPresenterError
        )
    }


    private fun onSubScriptOrderListPresenterSuccess(responseorderslist: Responseorderslist) {
        mArrayOrderListGetid.addAll(responseorderslist.data.product)
        Tv_Code.text = responseorderslist.data.name
        TV_vats.text =responseorderslist.data.priceVat.toString()
        Tv_SumVat.text =responseorderslist.data.net.toString()
        Tv_SumP.text = responseorderslist.data.price.toString()
        Tv_persenTy.text = responseorderslist.data.discount.toString()
        Vat_persenTy.text =responseorderslist.data.vat.toString()
        Tv_firstname.text = responseorderslist.data.nameBill
        TV_addressUser.text =responseorderslist.data.bill
        TV_name.text = responseorderslist.data.customerFirstName
        TV_addressGone.text = responseorderslist.data.customerAddress
        Tv_test.text =responseorderslist.data.branch
        TV_discourt.text =responseorderslist.data.priceDiscount.toString()
        mAdapterOrderGetId.notifyDataSetChanged()
    }

    private fun onSubScriptOrderListPresenterError(message: String) {

    }

    private fun setAdapter() {
        mAdapterOrderGetId = AdapterOrderGetId(this,mArrayOrderListGetid){
        }
        recyclerViewbuyordergetid.apply {
            layoutManager = LinearLayoutManager(this@OrderList)
            adapter = mAdapterOrderGetId
            mAdapterOrderGetId.notifyDataSetChanged()
        }
    }
}
