package com.example.samplemvp.view.basket

import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.BodyBasker
import com.example.samplemvp.model.DataorderSearch
import com.example.samplemvp.model.RespoOnDataSearch
import com.example.samplemvp.model.ResponseBasket
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class BasketPrester {
    var mDisposable: Disposable? = null

    fun BasketRx(
        map: HashMap<String , String> = HashMap(),
        preferences: Preferences,
        dataResponse: (ResponseBasket) -> Unit,
        messageError: (String) -> Unit
    ) {

        mDisposable = DataModule.getUserApiInstance()!!.doInserBasker(preferences.getToken(),map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseBasket>() {
                override fun onComplete() {

                }

                override fun onNext(responseBasket: ResponseBasket) {
                    dataResponse.invoke(responseBasket)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }



}