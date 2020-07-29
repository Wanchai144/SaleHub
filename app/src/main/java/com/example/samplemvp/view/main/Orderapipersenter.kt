package com.example.samplemvp.view.main

import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.Reponseapi
import com.example.samplemvp.model.ResponseCategories
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class Orderapipersenter {
    var mDisposable: Disposable? = null

    fun OrderapipersenterRx(
        preferences: Preferences,
        dataResponse: (Reponseapi) -> Unit,
        messageError: (String) -> Unit
    ) {
        mDisposable = DataModule.getUserApiInstance()!!.getorder(preferences.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Reponseapi>() {
                override fun onComplete() {
                }

                override fun onNext(responseapi: Reponseapi) {
                    dataResponse.invoke(responseapi)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }
}