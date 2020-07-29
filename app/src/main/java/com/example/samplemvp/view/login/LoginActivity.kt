package com.example.samplemvp.view.login

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.example.samplemvp.R
import com.example.samplemvp.dialog.DialogPresenter
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ResponseLogin
import com.example.samplemvp.view.main.ProductCategoryActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    var mLoginPresenter = LoginPresenter()
    var mDialogPresenter = DialogPresenter()
    val mPreferences: Preferences = Preferences(this)

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (checkIslogin(mPreferences.getToken())) {

            val myIntent = Intent(this, ProductCategoryActivity::class.java)
            val options =
                ActivityOptions.makeCustomAnimation(
                    this,
                    R.anim.trans_left_in,
                    R.anim.trans_left_out
                )
            this.startActivity(myIntent, options.toBundle())
        }

        onClickListener()
        onTextWatcher(etUsername)
        onTextWatcher(etPassword)

    }

    private fun onClickListener() {
        btLogin.setOnClickListener {
            mDialogPresenter.DialogBar(this, getString(R.string.Please_enter_your_username_ndpassword))
        }
    }
    fun onTextWatcher(view: EditText){
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                onCheckTextChange(s)
            }
        })
    }

    private fun onCheckTextChange(s: CharSequence) {
        when {
            (s.toString().isEmpty()) -> {
                btLogin.setOnClickListener {
                    mDialogPresenter.DialogBar(this,getString(R.string.Please_enter_your_username_ndpassword))
                }
            }
            s.toString().isNotEmpty()&&etUsername.text.toString().isEmpty()&&etPassword.text.toString().isNotEmpty() -> {
                btLogin.setOnClickListener {
                    mDialogPresenter.DialogBar(this, getString(R.string.Please_enter_username))
                }
            }
            s.toString().isNotEmpty()&&etUsername.text.toString().isNotEmpty()&&etPassword.text.toString().isEmpty() -> {
                btLogin.setOnClickListener {
                    mDialogPresenter.DialogBar(this, getString(R.string.Please_enter_password))
                }
            }
            s.toString().isNotEmpty()&&etUsername.text.toString().isNotEmpty()&&etPassword.text.toString().isNotEmpty() -> {
                btLogin.setOnClickListener {
                    frem_progress.visibility = View.VISIBLE
                    mLoginPresenter.LoginRx(
                        etUsername.text.toString(), etPassword.text.toString()
                        , this::onSubScriptLoginSuccess
                        , this::onSubScriptLoginError
                    )
                }
            }
        }
    }

    fun checkIslogin(token:String):Boolean{
        return token.isNotEmpty()
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun onSubScriptLoginSuccess(responseLogin: ResponseLogin) {
        mPreferences.saveToken(responseLogin.data.token)
        frem_progress.visibility = View.GONE

            val myIntent = Intent(this, ProductCategoryActivity::class.java)
            val options =
                ActivityOptions.makeCustomAnimation(
                    this,
                    R.anim.trans_left_in,
                    R.anim.trans_left_out
                )
            this.startActivity(myIntent, options.toBundle())
    }

    fun onSubScriptLoginError(messageError: String) {
        frem_progress.visibility = View.GONE
        mDialogPresenter.DialogBar(this, getString(R.string.username_or_password_NotPass))
    }


}
