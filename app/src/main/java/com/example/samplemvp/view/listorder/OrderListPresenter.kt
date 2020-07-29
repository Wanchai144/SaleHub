package com.example.samplemvp.view.listorder

import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.Responseorderslist
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class OrderListPresenter {
    var mDisposable: Disposable? = null

    fun OrderListPresenterRx(
        id: Int,
        preferences: Preferences,
        dataResponse: (Responseorderslist) -> Unit,
        messageError: (String) -> Unit
    ) {
        mDisposable = DataModule.getUserApiInstance()!!.getOrderList(preferences.getToken(),id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Responseorderslist>() {
                override fun onComplete() {
                }

                override fun onNext(responseorderslist: Responseorderslist) {
                    dataResponse.invoke(responseorderslist)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }
}