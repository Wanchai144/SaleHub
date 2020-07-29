package com.example.samplemvp.view.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvp.R
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.DataGetid
import com.example.samplemvp.model.Reponseapi
import com.example.samplemvp.view.adapter.AdapterorderitemList
import com.example.samplemvp.view.listorder.OrderList
import com.example.samplemvp.view.productdetails.ProductdetailsActivity
import kotlinx.android.synthetic.main.fragment_listoder.*
import kotlinx.android.synthetic.main.fragment_listoder.view.*


class ListoderFragment : Fragment() {
    companion object {
        fun newInstance(loadPage: String? = ""): ListoderFragment {
            val args = Bundle()
            args.putString("keyParam", loadPage)
            val fragment = ListoderFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listoder, container, false)
        setAdapter(view)
        return view.rootView
    }

    lateinit var mAdapterorderitemList: AdapterorderitemList
    var mReponseapi = ArrayList<DataGetid>()
    lateinit var mPreferences: Preferences
    var mOrderapipersenter = Orderapipersenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInit()
        setapiorder()
    }

    private fun setapiorder() {
        mPreferences = Preferences(context!!)
        mOrderapipersenter.OrderapipersenterRx(
            mPreferences
            , this::onSubScriptOrderapipersenterSuccess
            , this::onSubScriptOrderapipersenterError
        )
    }

    private fun onSubScriptOrderapipersenterSuccess(responseapi: Reponseapi) {
        mReponseapi.addAll(responseapi.data)
        mAdapterorderitemList.notifyDataSetChanged()

    }


    private fun onSubScriptOrderapipersenterError(mesage: String) {

    }

    private fun onInit() {

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun setAdapter(view: View) {
        mAdapterorderitemList = AdapterorderitemList(context!!, mReponseapi){
            val myIntent = Intent(context, OrderList::class.java)
            myIntent.putExtra("id", it)
            val options =
                ActivityOptions.makeCustomAnimation(
                    context,
                    R.anim.trans_left_in,
                    R.anim.trans_left_out
                )
            this.startActivity(myIntent, options.toBundle())
        }
        view.recyclerViewId_item.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapterorderitemList
            mAdapterorderitemList.notifyDataSetChanged()
        }
    }


}
