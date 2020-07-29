package com.example.samplemvp.view.subcategory

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvp.R
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.Responsesubcategory
import com.example.samplemvp.realm.BasketRealmManager
import com.example.samplemvp.view.adapter.CustomAdapterSubcategory
import com.example.samplemvp.view.basket.BasketActivity
import com.example.samplemvp.view.productdetails.ProductdetailsActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_subcategory.*
import kotlinx.android.synthetic.main.custom_toolbar_view.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class SubcategoryActivity : AppCompatActivity() {

    var mSubcategoryPresenter = SubcategoryPresenter()
    lateinit var mPreferences: Preferences
    var mProductlist = ArrayList<Responsesubcategory.Data>()
    lateinit var mCustomAdapterSubcategory: CustomAdapterSubcategory
    val mBasketRealmManager = BasketRealmManager()
    val mData = mBasketRealmManager.findall()
    var img: String = ""

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subcategory)
        Realm.init(this)
        onSetApi()
        onSetData()
        onClickListener()

        if (mData.isNotEmpty()) {
            tv_status_mdata.visibility = View.VISIBLE
            tv_size_mdata.text = mBasketRealmManager.sumProduct().toString()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mData.isNotEmpty()) {
            tv_status_mdata.visibility = View.VISIBLE
            tv_size_mdata.text = mBasketRealmManager.sumProduct().toString()
        } else {
            tv_status_mdata.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun onSetData() {

        mCustomAdapterSubcategory =
            CustomAdapterSubcategory(this, mProductlist) { id, name, potition ->

                if (id == 1234) {
                    mBasketRealmManager.Productcode(mProductlist[potition].productCode)?.id?.let {

                        val sumcontProduct =
                            mBasketRealmManager.Productcode(mProductlist[potition].productCode)!!.sumProducts.toString()
                                .toInt()
                        val Sum = sumcontProduct + 1
                        val sumPrieProducts = mProductlist[potition].price * Sum

                        mBasketRealmManager.updateMain(
                            mBasketRealmManager.Productcode(mProductlist[potition].productCode)!!.id,
                            Sum, sumPrieProducts
                        )
                        tv_size_mdata.text = mBasketRealmManager.sumProduct().toString()

                    } ?: run {

                        val sumPrieProducts = mProductlist[potition].price * 1
                        mBasketRealmManager.insert(
                            mProductlist[potition].id.toString(),
                            mProductlist[potition].productCode,
                            mProductlist[potition].price,
                            1,
                            mProductlist[potition].image,
                            sumPrieProducts,
                            mProductlist[potition].name
                        )

                        tv_size_mdata.text = mBasketRealmManager.sumProduct().toString()
                        tv_status_mdata.visibility = View.VISIBLE

                    }

                } else {
                    val myIntent = Intent(this, ProductdetailsActivity::class.java)
                    myIntent.putExtra("id", id)
                    myIntent.putExtra("name", name)
                    val options =
                        ActivityOptions.makeCustomAnimation(
                            this,
                            R.anim.trans_left_in,
                            R.anim.trans_left_out
                        )
                    this.startActivity(myIntent, options.toBundle())
                }


            }

        recyclerViewProduct.apply {
            layoutManager =
                GridLayoutManager(this@SubcategoryActivity, 2, LinearLayoutManager.VERTICAL, false)
            adapter = mCustomAdapterSubcategory
            mCustomAdapterSubcategory.notifyDataSetChanged()
        }
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun onClickListener() {
        imgBack.setImageResource(R.drawable.chevron)
        imgBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
        }
        layBackInvisible.setOnClickListener {
            val myIntent = Intent(this, BasketActivity::class.java)
            val options =
                ActivityOptions.makeCustomAnimation(
                    this,
                    R.anim.trans_left_in,
                    R.anim.trans_left_out
                )
            this.startActivity(myIntent, options.toBundle())
            imgBack.setOnClickListener {
                finish()
                overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
            }
        }
        val name: String = intent.getStringExtra("name")
        tvTitle.text = name
    }

    private fun onSetApi() {
        val categoryId: Int = intent.getIntExtra("categoryId", 0)
        val seriesId: Int = intent.getIntExtra("seriesId", 0)
        mPreferences = Preferences(this)
        frem_progress.visibility = View.VISIBLE
        mSubcategoryPresenter.SubcategoryRx(
            categoryId, seriesId, mPreferences
            , this::onSubScriptLoginSuccess
            , this::onSubScriptLoginError
        )

    }

    fun onSubScriptLoginSuccess(responsesubcategory: Responsesubcategory) {
        mProductlist.addAll(responsesubcategory.data)
        frem_progress.visibility = View.GONE
        if (mProductlist.isNotEmpty()) {
            visible.visibility = View.GONE
        } else {
            visible.visibility = View.VISIBLE
        }
        mCustomAdapterSubcategory.notifyDataSetChanged()
    }

    fun onSubScriptLoginError(messageError: String) {
        frem_progress.visibility = View.GONE
    }

}
