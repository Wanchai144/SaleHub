package com.example.samplemvp.view.profile

import android.os.AsyncTask
import com.example.samplemvp.connect.DataModule
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ProfileModel
import com.example.samplemvp.model.Reponseapi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import org.json.JSONObject
import java.io.File
import java.io.IOException

class ProfilePersenter {
    var mDisposable: Disposable? = null

    fun ProfilePersenterRx(
        preferences: Preferences,
        dataResponse: (ProfileModel) -> Unit,
        messageError: (String) -> Unit
    ) {
        mDisposable = DataModule.getUserApiInstance()!!.getprofile(preferences.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ProfileModel>() {
                override fun onComplete() {
                }

                override fun onNext(profileModel: ProfileModel) {
                    dataResponse.invoke(profileModel)
                }

                override fun onError(e: Throwable) {
                    messageError.invoke(e.message!!)
                }
            })
    }


    fun doUpdateProfiles(preferences: Preferences,
                         filePath: String,
                         url: String,
                         dataResponse: (String) -> Unit ,
                         messageError: (String) -> Unit) {
        class UploadPic : AsyncTask<String, Int, String>() {
            override fun doInBackground(vararg strings: String): String? {
                val ok = OkHttpClient()
                val sourceFileGallery = File(filePath)

                val builder = Request.Builder()
                val body = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("image", sourceFileGallery.name,
                        RequestBody.create(MediaType.parse("image/png"), sourceFileGallery))
                    .build()
                val request = builder
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "multipart/form-data")
                    .addHeader("Authorization", preferences.getToken())
                    .addHeader("Accept", "application/json")
                    .build()
                val response: Response
                return try {
                    response = ok.newCall(request).execute()
                    if (response.isSuccessful) {
                        response.body()!!.string()
                    } else {
                        null
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    e.cause.toString()
                }
            }

            override fun onPreExecute() {
                super.onPreExecute()

            }

            override fun onPostExecute(s: String?) {
                super.onPostExecute(s)

                s?.let {
                    try {
                        val jObjError = JSONObject(s)
                        dataResponse.invoke(jObjError.getString("message"))
                    } catch (e: Exception) {
                        messageError.invoke("500 Internal Server Error")
                    }

                } ?: kotlin.run {
                    messageError.invoke(s!!)
                }
            }

            override fun onProgressUpdate(vararg values: Int?) {}
        }
        UploadPic().execute()
    }
}