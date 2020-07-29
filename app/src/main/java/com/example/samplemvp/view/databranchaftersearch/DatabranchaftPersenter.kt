package com.example.samplemvp.view.databranchaftersearch

import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.Repodatabranchaftersearch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DatabranchaftPersenter {
    var mDisposable: Disposable? = null

    fun DatabranchaftPersenterRx(
        id:Int,
        preferences: Preferences,
        dataResponse: (Repodatabranchaftersearch) -> Unit,
        messageError: (String) -> Unit
    ) {

        mDisposable = DataModule.getUserApiInstance()!!.onDatabranchaftersearch(preferences.getToken(),id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Repodatabranchaftersearch>() {
                override fun onComplete() {

                }

                override fun onNext(repodatabranchaftersearch: Repodatabranchaftersearch) {
                    dataResponse.invoke(repodatabranchaftersearch)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }
}