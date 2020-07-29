package com.example.samplemvp.view.buyorder

import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.Repobuyorder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class BuyorderPersenter {
    var mDisposable: Disposable? = null

    fun BuyorderPersenterRx(
        map: HashMap<String , String> = HashMap(),
        preferences: Preferences,
        dataResponse: (Repobuyorder) -> Unit,
        messageError: (String) -> Unit
    ) {

        mDisposable = DataModule.getUserApiInstance()!!.doInserorder(preferences.getToken(),map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Repobuyorder>() {
                override fun onComplete() {

                }

                override fun onNext(repobuyorder: Repobuyorder) {
                    dataResponse.invoke(repobuyorder)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }

}