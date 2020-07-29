package com.example.samplemvp.view.main

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvp.R
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.DataOrderList
import com.example.samplemvp.model.ResponseCategories
import com.example.samplemvp.view.adapter.CustomAdapterCategory
import com.example.samplemvp.view.productlist.ProductlistActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_product_category.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductCategoryFragment : Fragment() {

    var mProductCategory = ArrayList<DataOrderList>()
    var mProductCategoryPresemntre = ProductCategoryPresenter()
    lateinit var mCustomAdapterCategory: CustomAdapterCategory
    lateinit var mPreferences: Preferences


    companion object {
        fun newInstance(loadPage: String? = ""): ProductCategoryFragment {
            val args = Bundle()
            args.putString("keyParam", loadPage)
            val fragment = ProductCategoryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_category, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onSetDataProduct()
        onSetApi()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun onSetDataProduct() {
        mCustomAdapterCategory = CustomAdapterCategory(
            context!!, mProductCategory
        ) { id ,name->

            val myIntent = Intent(context, ProductlistActivity::class.java)
            myIntent.putExtra("id", id)
            myIntent.putExtra("name", name)
            val options =
                ActivityOptions.makeCustomAnimation(
                    context,
                    R.anim.trans_left_in,
                    R.anim.trans_left_out
                )
            context!!.startActivity(myIntent, options.toBundle())
        }

        recyclerviewcategory.apply {
            layoutManager = GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false)
            adapter = mCustomAdapterCategory
            mCustomAdapterCategory.notifyDataSetChanged()
        }



    }

    private fun onSetApi() {
        mPreferences = Preferences(context!!)
        frem_progress.visibility = View.VISIBLE
        mProductCategoryPresemntre.ProductCategoreRx(
            mPreferences
            , this::onSubScriptLoginSuccess
            , this::onSubScriptLoginError
        )

    }

    fun onSubScriptLoginSuccess(responseCategories: ResponseCategories) {
        frem_progress.visibility = View.GONE
        mProductCategory.addAll(responseCategories.data)
        mCustomAdapterCategory.notifyDataSetChanged()
    }

    fun onSubScriptLoginError(messageError: String) {
        frem_progress.visibility = View.GONE
    }

}
