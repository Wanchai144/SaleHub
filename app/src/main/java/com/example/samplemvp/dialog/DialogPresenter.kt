package com.example.samplemvp.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.samplemvp.R
import kotlinx.android.synthetic.main.custom_dialog_delete_basket.*
import kotlinx.android.synthetic.main.custom_dialog_delete_basket.view.ok
import kotlinx.android.synthetic.main.custom_dialog_inser_basket.view.*
import kotlinx.android.synthetic.main.dialog.view.*
import kotlinx.android.synthetic.main.dialog.view.tvtext
import kotlinx.android.synthetic.main.dialog_profile.*
import kotlinx.android.synthetic.main.dialog_profile.view.*
import kotlinx.android.synthetic.main.dialogbar_editprofile.view.*


class DialogPresenter {

    @SuppressLint("SetTextI18n")
    fun DialogBar(context: Context, titleDialog: String) {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mDialogView.btok.setOnClickListener {
            mAlertDialog.dismiss()

        }
        mDialogView.tvtext.text = titleDialog

    }

    @SuppressLint("SetTextI18n")
    fun DialogSuccessUpdateImg(context: Context, titleDialog: String) {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialogbar_editprofile, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mDialogView.btn_ok.setOnClickListener {
            mAlertDialog.dismiss()

        }
        mDialogView.tv_text.text = titleDialog

    }

    fun Dialogdeiete(context: Context, id: Int, callBack: (Int) -> Unit) {
        val mDialogView =
            LayoutInflater.from(context).inflate(R.layout.custom_dialog_delete_basket, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mDialogView.ok.setOnClickListener {
            callBack.invoke(id)
            mAlertDialog.dismiss()
        }
        mAlertDialog.cancal.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }


    fun DialogdeleteDraforder(context: Context, callBack: (Int) -> Unit) {
        val mDialogView =
            LayoutInflater.from(context).inflate(R.layout.delectdraforder, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mDialogView.ok.setOnClickListener {
            callBack.invoke(1234)
            mAlertDialog.dismiss()
        }
        mAlertDialog.cancal.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }



    fun DialogInserBasket(context: Context, callBack: (String) -> Unit) {
        val mDialogView =
            LayoutInflater.from(context).inflate(R.layout.custom_dialog_inser_basket, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        mDialogView.ok.setOnClickListener {
            callBack.invoke(mDialogView.edt_name_Product_order.text.toString())
            mAlertDialog.dismiss()
        }

        mAlertDialog.cancal.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }


    fun DialogProfile(context: Context,  callBack: (String) -> Unit) {
        val mDialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_profile, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mAlertDialog.window?.setGravity(Gravity.BOTTOM)
        mDialogView.Tv_Camera.setOnClickListener {
            callBack.invoke("1")
            mAlertDialog.dismiss()
        }
        mDialogView.Tv_Photo.setOnClickListener {
            callBack.invoke("2")
            mAlertDialog.dismiss()
        }
        mAlertDialog.Tv_canclen.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}