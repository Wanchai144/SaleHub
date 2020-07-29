package com.example.samplemvp.view.productlist

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvp.R
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ResponsSeriesList
import com.example.samplemvp.realm.BasketRealmManager
import com.example.samplemvp.view.adapter.CustomAdapterPeoductList
import com.example.samplemvp.view.basket.BasketActivity
import com.example.samplemvp.view.subcategory.SubcategoryActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_productlist.*
import kotlinx.android.synthetic.main.custom_toolbar_view.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductlistActivity : AppCompatActivity() {

    var mProductlistPresenter = ProductlistPresenter()
    lateinit var mPreferences: Preferences
    var mProductlist = ArrayList<ResponsSeriesList.Data>()
    lateinit  var mCustomAdapterProductlist : CustomAdapterPeoductList
    val mBasketRealmManager = BasketRealmManager()
    val mData = mBasketRealmManager.findall()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productlist)
        Realm.init(this)
        onSetApi()
        onSetDataProductList()
        onClickListener()

        if (mData.isNotEmpty()){
            tv_status_mdata.visibility = View.VISIBLE
            tv_size_mdata.text = mBasketRealmManager.sumProduct().toString()
        }
    }


    override fun onResume() {
        super.onResume()
        if (mData.isNotEmpty()){
            tv_status_mdata.visibility = View.VISIBLE
            tv_size_mdata.text = mBasketRealmManager.sumProduct().toString()
        }else{
            tv_status_mdata.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun onSetDataProductList() {
        mCustomAdapterProductlist = CustomAdapterPeoductList(this, mProductlist
        ) { id ,name,categoryId->

            val myIntent = Intent(this, SubcategoryActivity::class.java)
            myIntent.putExtra("categoryId",categoryId)
            myIntent.putExtra("seriesId",id)
            myIntent.putExtra("name",name)
            val options =
                ActivityOptions.makeCustomAnimation(this, R.anim.trans_left_in, R.anim.trans_left_out)
            this.startActivity(myIntent, options.toBundle())
        }
        recyclerViewProductList.apply {
            layoutManager =  GridLayoutManager(this@ProductlistActivity, 1, LinearLayoutManager.VERTICAL, false)
            adapter = mCustomAdapterProductlist
            mCustomAdapterProductlist.notifyDataSetChanged()
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun onClickListener() {
        imgBack.setImageResource(R.drawable.chevron)
        imgBack.setOnClickListener{
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
        val name:String = intent.getStringExtra("name")
        tvTitle.text=name
    }

    private fun onSetApi() {
        val id:String = intent.getStringExtra("id")
        mPreferences = Preferences(this)
        frem_progress.visibility = View.VISIBLE
        mProductlistPresenter.ProductlistRx(id,
            mPreferences
            , this::onSubScriptLoginSuccess
            , this::onSubScriptLoginError
        )
    }

    fun onSubScriptLoginSuccess(responsSeriesList: ResponsSeriesList) {
        frem_progress.visibility = View.GONE
        mProductlist.addAll(responsSeriesList.data)
        mCustomAdapterProductlist.notifyDataSetChanged()
    }

    fun onSubScriptLoginError(messageError: String) {
        frem_progress.visibility = View.GONE

    }

}
