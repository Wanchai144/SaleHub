package com.example.samplemvp.view.orderlist

import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.DelectProfileModel
import com.example.samplemvp.model.Responseorderslist
import com.example.samplemvp.model.drafdetailModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class Orderpresenter {
    var mDisposable: Disposable? = null

    fun OrderpresenterRx(
        seriesId: Int,
        preferences: Preferences,
        dataResponse: (drafdetailModel) -> Unit,
        messageError: (String) -> Unit
    ) {
        mDisposable = DataModule.getUserApiInstance()!!.getorderlist(preferences.getToken(),seriesId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<drafdetailModel>() {
                override fun onComplete() {
                }

                override fun onNext(drafdetailModel: drafdetailModel) {
                    dataResponse.invoke(drafdetailModel)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }


    fun DelectPersenterRx(
        id:Int,
        preferences: Preferences,
        dataResponse: (DelectProfileModel) -> Unit,
        messageError: (String) -> Unit
    ) {

        mDisposable = DataModule.getUserApiInstance()!!.delectdraforder(preferences.getToken(),id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<DelectProfileModel>() {
                override fun onComplete() {

                }

                override fun onNext(delectProfileModel: DelectProfileModel) {
                    dataResponse.invoke(delectProfileModel)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }
}