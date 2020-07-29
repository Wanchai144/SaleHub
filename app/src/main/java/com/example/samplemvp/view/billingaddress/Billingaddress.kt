package com.example.samplemvp.view.billingaddress

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.samplemvp.R
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.DataSearchs
import com.example.samplemvp.model.DataorderSearch
import com.example.samplemvp.model.RespoOnDataSearch
import com.example.samplemvp.model.Respoonsearch
import com.example.samplemvp.view.buyorder.BuyOrderproduct
import com.example.samplemvp.view.databranchaftersearch.Databranchaftersearch
import kotlinx.android.synthetic.main.activity_buy_order.*
import kotlinx.android.synthetic.main.custom_toolbar_view.*
import kotlinx.android.synthetic.main.item_manulist_bar.*
import java.util.ArrayList

@Suppress("PLUGIN_WARNING")
class Billingaddress : AppCompatActivity() {
    var mListResultSearch = ArrayList<String>()
    var mDataListSearchCustomer = ArrayList<DataSearchs>()
    var mSearchpersenter = Searchpersenter()
    lateinit var mPreferences: Preferences
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_order)
        initview()
        onsetapi()
        onclick()
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun onclick() {
        btn_Next.setOnClickListener {
            for (i in mDataListSearchCustomer.indices) {
                val myIntent = Intent(this, BuyOrderproduct::class.java)
                myIntent.putExtra("TV_address", TV_address.text.toString())
                myIntent.putExtra("Tv_location", Tv_location.text.toString())
                myIntent.putExtra("Tv_Road", Tv_Road.text.toString())
                myIntent.putExtra("Tv_alley", Tv_alley.text.toString())
                myIntent.putExtra("Tv_District", Tv_District.text.toString())
                myIntent.putExtra("Tv_Aumper", Tv_Aumper.text.toString())
                myIntent.putExtra("Tv_province", Tv_province.text.toString())
                myIntent.putExtra("Tv_Postalcode", Tv_Postalcode.text.toString())
                myIntent.putExtra("Tv_phone", Tv_phone.text.toString())
                myIntent.putExtra("id",mDataListSearchCustomer[i].id)
                myIntent.putExtra("firstname", mDataListSearchCustomer[i].firstname)
                val options =
                    ActivityOptions.makeCustomAnimation(
                        this,
                        R.anim.trans_left_in,
                        R.anim.trans_left_out
                    )
                this.startActivity(myIntent, options.toBundle())
            }
        }
        my_intentBranch.setOnClickListener {
            val myIntent = Intent(this, Databranchaftersearch::class.java)
            val options =
                ActivityOptions.makeCustomAnimation(
                    this,
                    R.anim.trans_left_in,
                    R.anim.trans_left_out
                )
            this.startActivity(myIntent, options.toBundle())
        }
    }

    private fun onsetapi() {
        editSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    onConectSearch(s.toString())
                } else {
                    data_visible.visibility = View.GONE
                }
            }

        })
    }

    fun onConectSearch(keyword: String) {
        mPreferences = Preferences(this)
        mSearchpersenter.SearchpersenterRx(
            keyword, mPreferences
            , this::onSubScriptListSearchCustomerSuccess
            , this::onSubScriptListSearchCustomerError
        )

    }

    fun onSubScriptListSearchCustomerSuccess(respoonsearch: Respoonsearch) {
        mDataListSearchCustomer.clear()
        mListResultSearch.clear()
        mDataListSearchCustomer.addAll(respoonsearch.data)
        for (i in mDataListSearchCustomer.indices) {
            mListResultSearch.add(mDataListSearchCustomer[i].firstname)
        }

        val adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListResultSearch)
        editSearch.setAdapter(adapter)
        editSearch.onItemClickListener =
            AdapterView.OnItemClickListener { parent
                                              , _
                                              , position
                                              , _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                val firstname = mDataListSearchCustomer[position].firstname
                if (selectedItem == firstname) {
                    data_visible.visibility = View.VISIBLE
                } else {
                    data_visible.visibility = View.GONE
                }
                onSetShowList(mDataListSearchCustomer[position].id)
            }
    }

    fun onSubScriptListSearchCustomerError(messageError: String) {

    }

    fun onSetShowList(id: Int) {
        mPreferences = Preferences(this)
        mSearchpersenter.DataorderSearchRx(
            id, mPreferences,
            this::onSubScriptListSearchOrderListSuccess
            , this::onSubScriptListSearchOrderListError
        )
    }


    fun onSubScriptListSearchOrderListSuccess(respoondatasearch: RespoOnDataSearch) {
        TV_address?.text = respoondatasearch.data.address
        Tv_location?.text = respoondatasearch.data.moo
        Tv_Road?.text = respoondatasearch.data.road
        Tv_alley?.text = respoondatasearch.data.soi
        Tv_District?.text = respoondatasearch.data.district
        Tv_Aumper?.text = respoondatasearch.data.amphure
        Tv_province?.text = respoondatasearch.data.province
        Tv_Postalcode?.text = respoondatasearch.data.postcode
        Tv_phone?.text = respoondatasearch.data.telephone

    }

    fun onSubScriptListSearchOrderListError(messageError: String) {

    }


    private fun initview() {
        imgBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
        }
        imgBack.setImageResource(R.drawable.chevron)
        tvTitle.text = "ที่อยู่ในการออกบิล"
        imgicon.visibility = View.GONE
    }


}

