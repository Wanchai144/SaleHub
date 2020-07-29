package com.example.samplemvp.view.productlist

import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ResponsSeriesList
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ProductlistPresenter {

    var mDisposable: Disposable? = null

    fun ProductlistRx(
        id: String,
        preferences: Preferences,
        dataResponse: (ResponsSeriesList) -> Unit,
        messageError: (String) -> Unit
    ) {
        mDisposable = DataModule.getUserApiInstance()!!.doProductlist(preferences.getToken(),id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponsSeriesList>() {
                override fun onComplete() {
                }

                override fun onNext(responsSeriesList: ResponsSeriesList) {
                    dataResponse.invoke(responsSeriesList)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }
}