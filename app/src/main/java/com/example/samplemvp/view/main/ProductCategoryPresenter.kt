package com.example.samplemvp.view.main


import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ResponseCategories
import com.example.samplemvp.model.ResponseListBook
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ProductCategoryPresenter{

    var mDisposable: Disposable? = null

    fun ProductCategoreRx(
        preferences: Preferences,
        dataResponse: (ResponseCategories) -> Unit,
        messageError: (String) -> Unit
    ) {
        mDisposable = DataModule.getUserApiInstance()!!.getCategories(preferences.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseCategories>() {
                override fun onComplete() {
                }

                override fun onNext(responseCategories: ResponseCategories) {
                    dataResponse.invoke(responseCategories)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }

    fun ListBookFragmentRx(
        preferences: Preferences,
        dataResponse: (ResponseListBook) -> Unit,
        messageError: (String) -> Unit
    ) {
        mDisposable = DataModule.getUserApiInstance()!!.getListBook(preferences.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseListBook>() {
                override fun onComplete() {
                }

                override fun onNext(responseListBook: ResponseListBook) {
                    dataResponse.invoke(responseListBook)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }
}