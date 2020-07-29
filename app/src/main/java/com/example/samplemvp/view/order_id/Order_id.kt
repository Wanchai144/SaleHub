package com.example.samplemvp.view.order_id

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvp.R
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.Responseorderslist
import com.example.samplemvp.view.adapter.Adapterorder_id
import com.example.samplemvp.view.main.ProductCategoryActivity
import kotlinx.android.synthetic.main.activity_order_id.*
import kotlinx.android.synthetic.main.ic_toolbarsuc.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Order_id : AppCompatActivity() {
    lateinit var mPreferences: Preferences
    var mOrderIdPersenter = OrderIdPersenter()
    lateinit var mAdapterorder_id: Adapterorder_id
    var mOrderproductlist: MutableList<Responseorderslist.Dataorder.Product> = ArrayList()
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_id)
        initView()
        setapiView()
        onsetAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun initView() {
        tvTitle.text = "สั่งซื้อเสร๊จสิ้น"
        Tv_clear.setOnClickListener {
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
    }

    private fun setapiView() {
        val id: Int = intent.getIntExtra("id", 0)
        mPreferences = Preferences(this)
        mOrderIdPersenter.OrderIdPersenterRx(
            id, mPreferences
            , this::onSubScriptpersenterorder_idSuccess
            , this::onSubScriptpersenterorder_idError
        )
    }

    private fun onSubScriptpersenterorder_idSuccess(responseorderslist: Responseorderslist) {
        mOrderproductlist.addAll(responseorderslist.data.product)
        mAdapterorder_id.notifyDataSetChanged()
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
    }

    private fun onSubScriptpersenterorder_idError(message: String) {

    }


    private fun onsetAdapter() {
        mAdapterorder_id = Adapterorder_id(this, mOrderproductlist) {}
        recyclerViewbuyorderid.apply {
            layoutManager = LinearLayoutManager(this@Order_id)
            adapter = mAdapterorder_id
            mAdapterorder_id.notifyDataSetChanged()
        }
    }

}
