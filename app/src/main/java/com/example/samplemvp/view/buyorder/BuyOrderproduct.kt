package com.example.samplemvp.view.buyorder

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.samplemvp.R
import com.example.samplemvp.dialog.DialogPresenter
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.Databuyorder
import com.example.samplemvp.model.Repobuyorder
import com.example.samplemvp.model.Responseorderslist
import com.example.samplemvp.realm.BasketRealmManager
import com.example.samplemvp.view.adapter.AdapterShowdataRealm
import com.example.samplemvp.view.order_id.Order_id
import com.google.android.gms.location.*
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_buy_orderproduct.*
import kotlinx.android.synthetic.main.custom_toolbar_view.*
import kotlinx.android.synthetic.main.item_manulist_bar.*
import java.text.DecimalFormat

private const val PERMISSION_REQUEST = 10

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NAME_SHADOWING")
class BuyOrderproduct : AppCompatActivity() {
    val mDialogPresenter = DialogPresenter()
    var mBuyorderPersenter = BuyorderPersenter()
    lateinit var mPreferences: Preferences
    val mBasketRealmManager: BasketRealmManager = BasketRealmManager()
    val mData = mBasketRealmManager.findall()
    lateinit var mAdapterShowdataRealm: AdapterShowdataRealm
    var sumPrie: Double = mBasketRealmManager.sumprie()
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var mLat = "0"
    var mLon  = "0"
    var mAddress = "address"
    private var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        setContentView(R.layout.activity_buy_orderproduct)
        Realm.init(this)
        setadapter()
        onclickswith()
        Tv_showdata.visibility = View.VISIBLE
        setApiInsert()
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            mLat = mLastLocation.latitude.toString()
            mLon = mLastLocation.longitude.toString()
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        mLat = location.latitude.toString()
                        mLon = location.longitude.toString()

                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }



    private fun onclickswith() {
        switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showdata.visibility = View.VISIBLE
                Tv_showdata.visibility = View.GONE
            } else {
                showdata.visibility = View.GONE
                Tv_showdata.visibility = View.VISIBLE
            }
        }

        sumPrie = mBasketRealmManager.sumprie()
        Tv_SumPerSen.text = sumPrie.toString()
        for (i in mData.indices) {
            Tv_persenAV.text = mData[i].edtpersen
            Vat_persen.text = mData[i].edtvat


            if (mData[i].tv_sumpersens == "0" || mData[i].edtvat == "0"){
                val mEdtvat = mData[i].edtvat.toDouble()
                val mTv_sumpersens = mData[i].tv_sumpersens.toDouble()
                TV_vats.text = CaculetorVat(mTv_sumpersens, mEdtvat)
            }else{
                    TV_vats.text = CaculetorVat(0.0, 0.0)
            }

            if (mData[i].edtvat.isNotEmpty()){
                val mEdtvat = mData[i].edtvat.toDouble()
                val mTv_sumpersens = mData[i].tv_sumpersens.toDouble()
                TV_vats.text = CaculetorVat(mTv_sumpersens, mEdtvat)

            }else{

                TV_vats.text = CaculetorVat(0.0, 0.0)

            }

            TV_NumPersens.text = mData[i].tv_sumpersens
            Tv_SumVat.text = mData[i].tv_sumvat
            imgicon.visibility = View.GONE
            tvTitle.text = getString(R.string.prie_add_product)
        }
    }

    private fun setadapter() {
        mAdapterShowdataRealm = AdapterShowdataRealm(this, mData) {

        }
        recyclerViewbuyorder.apply {
            layoutManager = LinearLayoutManager(this@BuyOrderproduct)
            adapter = mAdapterShowdataRealm
            mAdapterShowdataRealm.notifyDataSetChanged()

        }

    }

    fun CaculetorVat(price: Double, discount: Double): String {
        val vat: Double = price * discount / 100
        val df2 = DecimalFormat("0")
        return df2.format(vat)
    }

    @SuppressLint("SetTextI18n")
    private fun setApiInsert() {
        mPreferences = Preferences(this)
        val firstname: String? = intent.getStringExtra("firstname")
        val address: String? = intent.getStringExtra("TV_address")
        val id: Int? = intent.getIntExtra("id", 0)
        val location: String? = intent.getStringExtra("Tv_location")
        val Road: String? = intent.getStringExtra("Tv_Road")
        val alley: String? = intent.getStringExtra("Tv_alley")
        val District: String? = intent.getStringExtra("Tv_District")
        val Aumper: String? = intent.getStringExtra("Tv_Aumper")
        val province: String? = intent.getStringExtra("Tv_province")
        val Postalcode: String? = intent.getStringExtra("Tv_Postalcode")
        val phone: String? = intent.getStringExtra("Tv_phone")

        Tv_firstname.text = firstname
        TV_name.text = firstname
        TV_addressUser?.text =
            "$address $location $Road $alley $District $Aumper $province $Postalcode $phone"
        TV_addressGone?.text =
            "$address $location $Road $alley $District $Aumper $province $Postalcode $phone"

        btn_Insert.setOnClickListener {

        val map: HashMap<String, String> = HashMap()
        map["vat"] = Vat_persen.text.toString()
        map["customer_id"] = id.toString()
        map["branch_id"] = "1"
        map["get_address"] = address.toString()
        map["address"] = TV_addressUser.text.toString()
        map["district"] = District.toString()
        map["amphure"] = Aumper.toString()
        map["province"] = province.toString()
        map["postcode"] = Postalcode.toString()
        map["address_bill"] = TV_addressGone.text.toString()
        map["name"] = firstname.toString()
        map["firstname"] = firstname.toString()
        map["telephone"] = phone.toString()
        map["lat"] = mLat
        map["lng"] = mLon
        map["discount"] = Tv_persenAV.text.toString()
        for (i in mData.indices) {
            map["products[${i}][product_id] "] = mData[i].idproduct
            map["products[${i}][amount]"] = mData[i].sumProducts.toString()
            map["products[${i}][percent]"] = mData[i].discount.toString()
        }

            mBuyorderPersenter.BuyorderPersenterRx(
                map, mPreferences
                , this::onSubScriptBuyorderPersenterSuccess
                , this::onSubScriptBuyorderPersenterError
            )

        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun onSubScriptBuyorderPersenterSuccess(repobuyorder: Repobuyorder) {
        mBasketRealmManager.delectall()
        val myIntent = Intent(this, Order_id::class.java)
        myIntent.putExtra("id", repobuyorder.data.id)
        val options =
            ActivityOptions.makeCustomAnimation(
                this,
                R.anim.trans_left_in,
                R.anim.trans_left_out
            )
        this.startActivity(myIntent, options.toBundle())
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }


    private fun onSubScriptBuyorderPersenterError(messageError: String) {
        mDialogPresenter.DialogBar(this,messageError)
    }
}
