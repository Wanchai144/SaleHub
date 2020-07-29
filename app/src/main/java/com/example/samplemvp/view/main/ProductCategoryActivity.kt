package com.example.samplemvp.view.main

import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.samplemvp.R
import com.example.samplemvp.dialog.DialogPresenter
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ResponseDataManu
import com.example.samplemvp.model.ResponseListBook
import com.example.samplemvp.realm.BasketRealmManager
import com.example.samplemvp.view.adapter.CustomAdapterManuList
import com.example.samplemvp.view.basket.BasketActivity
import com.example.samplemvp.view.listbook.ListBookFragment
import com.example.samplemvp.view.profile.ProfileFragment
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar_view.*
import kotlinx.android.synthetic.main.custom_toolbar_view.tv_status_mdata
import kotlinx.android.synthetic.main.custom_toolbar_view.view.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductCategoryActivity : AppCompatActivity() {
    lateinit var mPreferences: Preferences
    var mProductCategoryPresemntre = ProductCategoryPresenter()
    var mDialogPresenter = DialogPresenter()
    var mDataListBook = ArrayList<ResponseListBook.DataListBook>()
    var mDataManuList = ArrayList<ResponseDataManu>()
    lateinit var mCustomAdapterManuList: CustomAdapterManuList
    val mBasketRealmManager : BasketRealmManager = BasketRealmManager()
    val mData = mBasketRealmManager.findall()



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(this)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                ProductCategoryFragment.newInstance()
            ).commitNow()
        }

        setapi()
        initView()
        onClickListener()

    }

    private fun setapi() {
        mPreferences = Preferences(this)
        mProductCategoryPresemntre.ListBookFragmentRx(
            mPreferences
            , this::onSubScriptLoginSuccess
            , this::onSubScriptLoginError
        )
    }
    fun onSubScriptLoginSuccess(responseListBook: ResponseListBook) {
        mDataListBook.addAll(responseListBook.data)
        val mDataRespo = responseListBook.sumOrder.toString()

        for (i in Datamenu.mListMenuNav.indices) {
            mDataManuList.add(ResponseDataManu(Datamenu.mListMenuNav[i], Datamenu.mListIconMenu[i]))
        }

        mCustomAdapterManuList = CustomAdapterManuList(this, mDataManuList,mDataRespo) {
            when (it) {
                0 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container,
                        ProductCategoryFragment.newInstance()
                    ).commitNow()
                    ic_custom_toolbar_view.tvTitle.text= getString(R.string.Product_category)
                    drawer_layout.closeDrawers()
                }
                1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container,
                        ListBookFragment.newInstance()
                    ).commitNow()
                    ic_custom_toolbar_view.tvTitle.text=getString(R.string.Recorded_list)
                    drawer_layout.closeDrawers()
                }
                2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container,
                        ListoderFragment.newInstance()).commitNow()
                    imgicon.visibility = View.GONE
                    ic_custom_toolbar_view.tvTitle.text=getString(R.string.Product_order_list)
                    drawer_layout.closeDrawers()

                }
                3 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container,
                        ProfileFragment.newInstance()
                    ).commitNow()
                    imgicon.visibility = View.GONE
                    ic_custom_toolbar_view.tvTitle.text=getString(R.string.profile)
                    drawer_layout.closeDrawers()
                }
            }
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mCustomAdapterManuList
            mCustomAdapterManuList.notifyDataSetChanged()
        }
    }

    fun onSubScriptLoginError(messageError: String) {
        mDialogPresenter.DialogBar(this,messageError)
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
    private fun onClickListener() {
        layBack.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
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
        }

        Glide.with(this)
            .load("https://www.img.in.th/images/095cd20732b5fef8bf7193d78cad360f.jpg")
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(img_Logo)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initView() {
        tvTitle.text = getString(R.string.Product_category)
        try {
            val pInfo: PackageInfo = this.packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            Tvshowidversion.text= version
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }

    }
}
