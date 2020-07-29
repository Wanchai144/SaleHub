package com.example.samplemvp.view.login

import android.annotation.SuppressLint
import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.BodyLogin
import com.example.samplemvp.model.ResponseLogin
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

@SuppressLint("Registered")
@Suppress("PLUGIN_WARNING")
class LoginPresenter  {
    var mDisposable: Disposable? = null

    fun LoginRx(
        user: String, pass: String,
        dataResponse: (ResponseLogin) -> Unit,
        messageError: (String) -> Unit
        ) {

        mDisposable = DataModule.getUserApiInstance()!!.doLogin(BodyLogin(user, pass))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseLogin>() {
                override fun onComplete() {

                }

                override fun onNext(responselogin: ResponseLogin) {
                    dataResponse.invoke(responselogin)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }
}