package com.example.samplemvp.view.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.samplemvp.R
import com.example.samplemvp.dialog.DialogPresenter
import com.example.samplemvp.dialog.Preferences
import com.example.samplemvp.model.ProfileModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : Fragment() {
    companion object {
        fun newInstance(loadPage: String? = ""): ProfileFragment {
            val args = Bundle()
            args.putString("keyParam", loadPage)
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initView(view)
        setapiEditImage(view)
        return view.rootView

    }

    val mDialogPresenter = DialogPresenter()
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    lateinit var mPreferences: Preferences
    val mProfilePersenter = ProfilePersenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setapi()

    }


    fun Uri.getPathString(): String {
        var path: String = ""
        context!!.contentResolver.query(
            this, arrayOf(MediaStore.Images.Media.DATA),
            null, null, null
        )?.apply {
            val columnIndex = getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            moveToFirst()
            path = getString(columnIndex)
            close()
        }
        return path
    }


    private fun setapiEditImage(view: View) {
        view.btn_Insert.setOnClickListener {
            mPreferences = Preferences(context!!)
            mProfilePersenter.doUpdateProfiles(
                mPreferences, image_uri!!.getPathString(),
                "http://demo.salehub.wewillapp.support/api/editimageprofile",
                this::onSubScriptUpdateImgFragmantSuccess
                , this::onSubScriptUpdateImgFragmantError
            )
        }
    }

    private fun onSubScriptUpdateImgFragmantSuccess(responseUpdateProfile: String) {
        context?.let {
            mDialogPresenter.DialogSuccessUpdateImg(it, responseUpdateProfile)
            btn_Insert.visibility = View.GONE

        }
    }

    private fun onSubScriptUpdateImgFragmantError(message: String) {
        context?.let { mDialogPresenter.DialogBar(it, message) }
        btn_Insert.visibility = View.GONE
    }

    private fun setapi() {
        mPreferences = Preferences(context!!)
        mProfilePersenter.ProfilePersenterRx(
            mPreferences
            , this::onProfilePersenterSuccess
            , this::onProfilePersenterError
        )
    }


    @SuppressLint("SetTextI18n")
    private fun onProfilePersenterSuccess(profileModel: ProfileModel) {
        Glide.with(context).load(profileModel.data.image)
            .into(ImageViewProfile)
        Tv_NameProfile.text = profileModel.data.firstName
        Tv_lastProfile.text = profileModel.data.lastName
        Tv_EmailProfile.text = profileModel.data.email
        Tv_AddressProfile.text = profileModel.data.address

    }

    private fun onProfilePersenterError(message: String) {

    }

    private fun initView(view: View) {
        view.ImageViewProfile.setOnClickListener {
            context?.let { contexts ->
                mDialogPresenter.DialogProfile(contexts) {
                    if (it == "1") {
                        inCamera()
                    } else {
                        setImageUrl()
                    }
                }
            }
        }
    }

    private fun setImageUrl() {
        val intent = Intent()
        intent.type = "image/'"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }


    private fun inCamera() {
        //หากระบบปฏิบัติการเป็น Marshmallow หรือสูงกว่าเราจำเป็นต้องขออนุญาตรันไทม์
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(context!!, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED
            ) {
                //ไม่ได้รับอนุญาต
                val permission = arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                //แสดงป๊อปอัพเพื่อขออนุญาต
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                //ได้รับอนุญาตแล้ว
                openCamera()
            }
        } else {
            //ระบบปฏิบัติการคือ <marshmallow
            openCamera()
        }
    }

    private fun openCamera() {
        val resolver = activity!!.contentResolver
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //เรียกว่าเมื่อผู้ใช้กด ALLOW หรือ DENY จากป๊อปอัพคำขออนุญาต
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //ได้รับอนุญาตจากป๊อปอัพ
                    openCamera()
                } else {
                    //ปฏิเสธการอนุญาตจากป๊อปอัป
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //เรียกเมื่อภาพถูกถ่ายจากเจตนาของกล้อง
        if (resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE_CODE) {
            ImageViewProfile.setImageURI(image_uri)
            btn_Insert.visibility = View.VISIBLE

        }else if (resultCode == RESULT_OK && requestCode == 1){
            image_uri = data?.data
            ImageViewProfile.setImageURI(image_uri)
            btn_Insert.visibility = View.VISIBLE
        }
    }
}
