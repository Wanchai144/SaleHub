package com.example.samplemvp.view.subcategory

import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.Responsesubcategory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SubcategoryPresenter {
    var mDisposable: Disposable? = null

    fun SubcategoryRx(
        categoryId: Int,
        seriesId: Int,
        preferences: Preferences,
        dataResponse: (Responsesubcategory) -> Unit,
        messageError: (String) -> Unit
    ) {
        mDisposable = DataModule.getUserApiInstance()!!
            .doProductlists(
                preferences.getToken()
                , categoryId, seriesId
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Responsesubcategory>() {
                override fun onComplete() {
                }

                override fun onNext(responsesubcategory: Responsesubcategory) {
                    dataResponse.invoke(responsesubcategory)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }
}