package com.example.samplemvp.view.productdetails

import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ResponseDetailProducts
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ProductdetailsPresenter {
    var mDisposable: Disposable? = null

    fun ProductdetailsRx(
        id: Int,
        preferences: Preferences,
        dataResponse: (ResponseDetailProducts) -> Unit,
        messageError: (String) -> Unit
    ) {
        mDisposable = DataModule.getUserApiInstance()!!.doDetailProducts(preferences.getToken(),id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseDetailProducts>() {
                override fun onComplete() {
                }

                override fun onNext(responsDetailProducts: ResponseDetailProducts) {
                    dataResponse.invoke(responsDetailProducts)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }
}