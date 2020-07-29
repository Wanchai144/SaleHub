package com.example.samplemvp.view.listbook

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvp.R
import com.example.samplemvp.dialog.DialogPresenter
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ResponseListBook
import com.example.samplemvp.view.adapter.CustomAdapterListBook
import com.example.samplemvp.view.main.ProductCategoryPresenter
import com.example.samplemvp.view.orderlist.OrderListActivity
import com.example.samplemvp.view.subcategory.SubcategoryActivity
import kotlinx.android.synthetic.main.fragment_list_book.*
import kotlinx.android.synthetic.main.fragment_list_book.frem_progress


class ListBookFragment : Fragment() {
    lateinit var mPreferences: Preferences
    var mProductCategoryPresemntre =
        ProductCategoryPresenter()
    var mDataListBook = ArrayList<ResponseListBook.DataListBook>()
    var mDialogPresenter = DialogPresenter()
    lateinit var mCustomAdapterListBook: CustomAdapterListBook

    companion object {
        fun newInstance(loadPage: String? = ""): ListBookFragment {
            val args = Bundle()
            args.putString("keyParam", loadPage)
            val fragment = ListBookFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_book, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onSetDataProduct()
        onSetApi()
    }

    private fun onSetApi() {
        mPreferences = Preferences(context!!)
        frem_progress.visibility = View.VISIBLE
        mProductCategoryPresemntre.ListBookFragmentRx(
            mPreferences
            , this::onSubScriptLoginSuccess
            , this::onSubScriptLoginError
        )
    }

    fun onSubScriptLoginSuccess(responseListBook: ResponseListBook) {
        mDataListBook.addAll(responseListBook.data)
        frem_progress.visibility = View.GONE
        mCustomAdapterListBook.notifyDataSetChanged()
    }

    fun onSubScriptLoginError(messageError: String) {
        mDialogPresenter.DialogBar(context!!,messageError)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun onSetDataProduct() {
        mCustomAdapterListBook = CustomAdapterListBook(
            context!!, mDataListBook
        ) { id,name ->
            val myIntent = Intent(context, OrderListActivity::class.java)
            myIntent.putExtra("seriesId",id)
            myIntent.putExtra("name",name)
            val options = ActivityOptions.makeCustomAnimation(context, R.anim.trans_left_in, R.anim.trans_left_out)
            this.startActivity(myIntent, options.toBundle())
        }

        recyclerview_listbook.apply {
            layoutManager = GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false)
            adapter = mCustomAdapterListBook
            mCustomAdapterListBook.notifyDataSetChanged()
        }
    }
}
