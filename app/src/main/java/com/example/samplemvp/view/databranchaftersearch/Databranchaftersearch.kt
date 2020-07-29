package com.example.samplemvp.view.databranchaftersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.samplemvp.R
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.Databranchafter
import com.example.samplemvp.model.Repodatabranchaftersearch
import kotlinx.android.synthetic.main.activity_buy_order.*
import kotlinx.android.synthetic.main.activity_databranchaftersearch.*
import kotlinx.android.synthetic.main.custom_toolbar_view.*
import kotlinx.android.synthetic.main.item_manulist_bar.*

class Databranchaftersearch : AppCompatActivity() {
    val mDatabranchaftPersenter = DatabranchaftPersenter()
    lateinit var mPreferences: Preferences
    val mRepodatabranchaftersearch = ArrayList<Databranchafter>()
    val mbranchaftersearch = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_databranchaftersearch)
        initView()
        onsetapi()
    }

    private fun onsetapi() {
//        edt_searchBar.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if (s.toString().isNotEmpty()) {
//                    onSetShowList()
//                } else {
//                    data_visible.visibility = View.GONE
//                }
//            }
//
//        })
    }

    private fun onSubScriptDatabranchaftPersenterSuccess(repodatabranchaftersearch: Repodatabranchaftersearch) {
        mRepodatabranchaftersearch.addAll(repodatabranchaftersearch.data)
        for (i in mRepodatabranchaftersearch.indices) {
            mbranchaftersearch.add(mRepodatabranchaftersearch[i].name)
        }
        val adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mbranchaftersearch)
        edt_searchBar.setAdapter(adapter)


        edt_searchBar.onItemClickListener = AdapterView.OnItemClickListener { _
                                              , _
                                              , position
                                              , _ ->
                onSetShowList(mRepodatabranchaftersearch[position].id)
            }
    }


    private fun onSetShowList(id: Int) {
        mPreferences = Preferences(this)
        mDatabranchaftPersenter.DatabranchaftPersenterRx(
            id, mPreferences
            , this::onSubScriptDatabranchaftPersenterSuccess
            , this::onSubScriptDatabranchaftPersenterError
        )
    }



    private fun onSubScriptDatabranchaftPersenterError(message: String) {

    }

    private fun initView() {
        tvTitle.text = "ที่อยู่ในการออกบิล"
        imgBack.visibility = View.GONE
        imgicon.visibility = View.GONE
    }
}
