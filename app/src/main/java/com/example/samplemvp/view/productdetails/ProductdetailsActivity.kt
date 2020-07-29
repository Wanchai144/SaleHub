package com.example.samplemvp.view.productdetails

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.samplemvp.R
import com.example.samplemvp.dialog.DialogPresenter
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ResponseDetailProducts
import com.example.samplemvp.realm.BasketRealmManager
import com.example.samplemvp.view.adapter.CustomAdapterImageList
import com.example.samplemvp.view.basket.BasketActivity
import com.varunest.sparkbutton.SparkButton
import com.viewpagerindicator.CirclePageIndicator
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_productdetails.*
import kotlinx.android.synthetic.main.custom_toolbar_view.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "IMPLICIT_CAST_TO_ANY")
class ProductdetailsActivity : AppCompatActivity() {

    var mListDataOrderList: MutableList<ResponseDetailProducts.Data.ImageProduct> = ArrayList()
    var mProductdetailsPresenter = ProductdetailsPresenter()
    lateinit var mPreferences: Preferences
    lateinit var mCustomAdapterImageList: CustomAdapterImageList
    var mDialogPresenter = DialogPresenter()
    val mBasketRealmManager = BasketRealmManager()
    val mData = mBasketRealmManager.findall()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productdetails)
        Realm.init(this)
        onSetApi()
        onClick()
        initLisyImage()
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
    private fun onClick() {
        layBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out)
        }
        imgBack.setImageResource(R.drawable.chevron)

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
        if (mData.isNotEmpty()) {
            tv_status_mdata.visibility = View.VISIBLE
            tv_size_mdata.text = mBasketRealmManager.sumProduct().toString()
        }

        tvTitle.text = getString(R.string.deteils_product)
    }

    private fun onSetApi() {
        mPreferences = Preferences(this)
        val id: Int = intent.getIntExtra("id", 0)
        frem_progress.visibility = View.VISIBLE
        mProductdetailsPresenter.ProductdetailsRx(
            id, mPreferences
            , this::onSubScriptLoginSuccess
            , this::onSubScriptLoginError
        )
    }

    fun checkIsNull(param: String): Boolean {
        return !(param.isEmpty() || param == "0")
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun onSubScriptLoginSuccess(responseDetailProducts: ResponseDetailProducts) {
        frem_progress.visibility = View.GONE

        mListDataOrderList.addAll(responseDetailProducts.data.imageProduct)

        mCustomAdapterImageList.notifyDataSetChanged()

        add_c.setOnClickListener {

            val sumProduct = editext_add.text.toString()

            if (checkIsNull(sumProduct)) {
                mBasketRealmManager.Productcode(responseDetailProducts.data.productCode)?.id?.let {

                    val sumcontProduct =
                        mBasketRealmManager.Productcode(responseDetailProducts.data.productCode)!!.sumProducts.toString()
                            .toInt()
                    val Sum = sumcontProduct + sumProduct.toInt()
                    val sumPrieProducts = responseDetailProducts.data.price * Sum

                    mBasketRealmManager.updateMain(
                        mBasketRealmManager.Productcode(responseDetailProducts.data.productCode)!!.id,
                        Sum, sumPrieProducts
                    )
                    Ainima()
                    initAnimation()
                    ImageView.visibility = View.VISIBLE
                    Toast.makeText(this, "update", Toast.LENGTH_SHORT).show()
                } ?: run {

                    val sumPrieProducts = responseDetailProducts.data.price * sumProduct.toInt()
                    mBasketRealmManager.insert(
                        responseDetailProducts.data.id.toString(),
                        responseDetailProducts.data.productCode,
                        responseDetailProducts.data.price,
                        sumProduct.toInt(),
                        responseDetailProducts.data.image,
                        sumPrieProducts,
                        responseDetailProducts.data.name
                    )
                    Toast.makeText(this, "inser", Toast.LENGTH_SHORT).show()
                    Ainima()
                    initAnimation()
                    ImageView.visibility = View.VISIBLE
                }
            } else {
                mDialogPresenter.DialogBar(this, getString(R.string.Please_add_more_products))
            }
        }

        if (mListDataOrderList.size == 1) {
            left.visibility = View.GONE
            right.visibility = View.GONE
        }

        tv_name.text = responseDetailProducts.data.name
        tv_productCode.text = responseDetailProducts.data.productCode
        tv_deteil.text = responseDetailProducts.data.detail
        tv_prie.text = responseDetailProducts.data.price.toString()

    }

    fun onSubScriptLoginError(messageError: String) {
        frem_progress.visibility = View.GONE
    }

    private fun initLisyImage() {
        mPager = findViewById(R.id.pager)
        mCustomAdapterImageList = CustomAdapterImageList(this, this.mListDataOrderList!!)
        mPager!!.adapter = mCustomAdapterImageList
        val indicator = findViewById<CirclePageIndicator>(R.id.indicator).also {
            it.setViewPager(mPager)
        }
        val density = resources.displayMetrics.density
        indicator.radius = 5 * density
        NUM_PAGES = mListDataOrderList!!.size
        right.setOnClickListener {
            mPager!!.currentItem++
        }
        left.setOnClickListener {
            mPager!!.currentItem--
        }

        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                currentPage = position

                when (currentPage) {
                    0 -> {
                        left.visibility = View.GONE
                        right.visibility = View.VISIBLE
                    }
                    mListDataOrderList.size - 1 -> {
                        right.visibility = View.GONE
                        left.visibility = View.VISIBLE
                    }
                    else -> {
                        right.visibility = View.VISIBLE
                        left.visibility = View.VISIBLE
                    }
                }
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(pos: Int) {
            }
        })
    }

    companion object {
        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }

    fun Ainima() {
        val animY: ObjectAnimator = ObjectAnimator.ofFloat(ImageView, View.TRANSLATION_Y, 0f,-1200f)
        val animX: ObjectAnimator = ObjectAnimator.ofFloat(ImageView, View.TRANSLATION_X, 0f,310f)
        val animSet = AnimatorSet()
        animSet.playTogether(animY, animX)
        animSet.duration = 1500
        animSet.start()
    }

    private fun initAnimation() {
        ImageView.postDelayed({
            ImageView.visibility = View.GONE
            tv_status_mdata.visibility = View.VISIBLE
            tv_size_mdata.text = mBasketRealmManager.sumProduct().toString()
            alertTitle.visibility = View.VISIBLE
            closalertTitle()
            playTwitterAnimation()
        }, 1500)
    }

    fun closalertTitle() {

        alertTitle.postDelayed({
            alertTitle.visibility = View.GONE
        }, 1500)

    }

    private fun playTwitterAnimation() {
        (findViewById<View>(R.id.layBackInvisible) as SparkButton).playAnimation()
    }
}