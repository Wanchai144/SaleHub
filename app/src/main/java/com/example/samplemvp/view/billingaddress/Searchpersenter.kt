package com.example.samplemvp.view.billingaddress
import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.RespoOnDataSearch
import com.example.samplemvp.model.Respoonsearch
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class Searchpersenter {
    var mDisposable: Disposable? = null

    fun SearchpersenterRx(
        keyword:String,
        preferences: Preferences,
        dataResponse: (Respoonsearch) -> Unit,
        messageError: (String) -> Unit
    ) {

        mDisposable = DataModule.getUserApiInstance()!!.onSearchOrto(preferences.getToken(),keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Respoonsearch>() {
                override fun onComplete() {

                }

                override fun onNext(respoonsearch: Respoonsearch) {
                    dataResponse.invoke(respoonsearch)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }

    fun DataorderSearchRx(
        id:Int,
        preferences: Preferences,
        dataResponse: (RespoOnDataSearch) -> Unit,
        messageError: (String) -> Unit
    ) {

        mDisposable = DataModule.getUserApiInstance()!!.onSearchData(preferences.getToken(),id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<RespoOnDataSearch>() {
                override fun onComplete() {

                }

                override fun onNext(respoondatasearch: RespoOnDataSearch) {
                    dataResponse.invoke(respoondatasearch)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }
}