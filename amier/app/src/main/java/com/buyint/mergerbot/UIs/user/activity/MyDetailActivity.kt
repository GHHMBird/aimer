package com.buyint.mergerbot.UIs.user.activity

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.fragment.SetUserIconFragment
import com.buyint.mergerbot.Utility.PermissionUtils
import com.buyint.mergerbot.Utility.ViewHelper
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.bus.Bus
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.database.saveLoginResponse
import com.buyint.mergerbot.dto.LoginResponse
import com.buyint.mergerbot.dto.StringResponse
import com.buyint.mergerbot.enums.ShowNameType
import com.buyint.mergerbot.interfaces.IUserIconUpdate
import com.buyint.mergerbot.presenter.MyDetailPresenter
import kotlinx.android.synthetic.main.activity_detail_my.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by huheming on 2018/6/27
 */
class MyDetailActivity : BaseActivity(), IUserIconUpdate {

    private var file: File? = null
    private var response: LoginResponse? = null
    private val PHOTO_CODE = 10
    private val PICTURE_CODE = 20
    private val CROP_PICTURE = 30
    private var finalPic: File? = null
    private var cropImageUri: Uri? = null
    private var uri: Uri? = null

    private var presenter: MyDetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = MyDetailPresenter(this)

        setMyTitleColor()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_my)

        toorbar_title.text = getString(R.string.my_business_file)
        toolbar_back.setOnClickListener { finish() }
        val drawable = resources.getDrawable(R.mipmap.three_cicle_white)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(4f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            ViewHelper.showBusinessFile(this@MyDetailActivity, toolbar_right_rl) { startActivity(Intent(this@MyDetailActivity, BusinessFileActivity::class.java)) }
        }

        activity_detail_my_detail.setOnClickListener { startActivityForResult(Intent(this, MyDetailWriteActivity::class.java), 10085) }
        activity_detail_my_desc.setOnClickListener { startActivityForResult(Intent(this, MyDetailWriteActivity::class.java), 10085) }
        activity_detail_my_personal_overview.setOnClickListener { startActivityForResult(Intent(this, MyDetailWriteActivity::class.java), 10085) }

        activity_detail_my_performance_achievement.setOnClickListener { startActivity(Intent(this, ProjectPerformanceActivity::class.java)) }
        activity_detail_my_business_activity.setOnClickListener { startActivity(Intent(this, BusinessActivityActivity::class.java)) }
        activity_detail_my_extra_information.setOnClickListener { startActivity(Intent(this, ExtraInformationActivity::class.java)) }

        initData()

        activity_detail_my_user_icon.setOnClickListener {
            val suif = SetUserIconFragment(object : SetUserIconFragment.SetUserIconFragmentListener {
                override fun photoClick() {
                    //检查应用必备权限
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val strings = PermissionUtils.hasPermission(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        if (strings.isNotEmpty()) {
                            android.support.v13.app.ActivityCompat.requestPermissions(this@MyDetailActivity, strings, PHOTO_CODE)
                        } else {
                            getPhoto()
                        }
                    } else {
                        getPhoto()
                    }
                }

                override fun pictureClick() {
                    //检查应用必备权限
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val strings = PermissionUtils.hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        if (strings.isNotEmpty()) {
                            android.support.v13.app.ActivityCompat.requestPermissions(this@MyDetailActivity, strings, PICTURE_CODE)
                        } else {
                            getPicture()
                        }
                    } else {
                        getPicture()
                    }
                }

            })
            suif.show(supportFragmentManager, "SetUserIconFragment")
        }
    }

    private fun initData() {
        response = getLoginResponse(this)

        Glide.with(this).load(response!!.model.avatars).asBitmap().centerCrop().error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(object : BitmapImageViewTarget(activity_detail_my_user_icon) {
            override fun setResource(resource: Bitmap) {
                val rbd = RoundedBitmapDrawableFactory.create(resources, resource)
                rbd.isCircular = true
                activity_detail_my_user_icon.setImageDrawable(rbd)
            }
        })

        if (response!!.model.userPrivacySetting == null) {
            if (TextUtils.isEmpty(response!!.model.englishName)) {
                activity_detail_my_name.text = getString(R.string.unknown_user)
            } else {
                activity_detail_my_name.text = response!!.model.englishName
            }
        } else {
            when (response!!.model.userPrivacySetting.showNameType) {
                ShowNameType.REALNAME.name -> {
                    if (TextUtils.isEmpty(response!!.model.userName)) {
                        activity_detail_my_name.text = getString(R.string.unknown_user)
                    } else {
                        activity_detail_my_name.text = response!!.model.userName
                    }
                }
                ShowNameType.NICKNAME.name -> {
                    if (TextUtils.isEmpty(response!!.model.englishName)) {
                        activity_detail_my_name.text = getString(R.string.unknown_user)
                    } else {
                        activity_detail_my_name.text = response!!.model.englishName
                    }
                }
                ShowNameType.NAMDE.name -> {
                    if (TextUtils.isEmpty(response!!.model.userName)) {
                        activity_detail_my_name.text = getString(R.string.unknown_user)
                    } else {
                        activity_detail_my_name.text = getString(R.string.sir_or_miss_2_start) + response!!.model.userName.substring(0, 1) + getString(R.string.sir_or_miss_2_end)
                    }
                }
            }
        }

        if (response!!.model.userWorkMessage == null) {
            activity_business_file_company.text = getString(R.string.undisclosed_company) + "|" + getString(R.string.undisclosed_position)
        } else {
            var companyName = response!!.model.userWorkMessage.companyName
            if (TextUtils.isEmpty(companyName)) {
                companyName = getString(R.string.undisclosed_company)
            }
            var position = response!!.model.userWorkMessage.position
            if (TextUtils.isEmpty(position)) {
                position = getString(R.string.undisclosed_position)
            }
            activity_business_file_company.text = companyName + "|" + position
        }

        if (TextUtils.isEmpty(response!!.model.description)) {
            activity_business_file_introduction.text = getString(R.string.guess)
        } else {
            activity_business_file_introduction.text = response!!.model.description
        }

    }

    fun startPhotoZoom(uri: Uri) {
        finalPic = File(externalCacheDir, "crop_image.jpg")
        try {
            if (finalPic!!.exists()) {
                finalPic!!.delete()
            }
            finalPic!!.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        cropImageUri = Uri.fromFile(finalPic)
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)

        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)

        intent.putExtra("outputX", 200)
        intent.putExtra("outputY", 200)

        intent.putExtra("return-data", false)
        intent.putExtra("circleCrop", true)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImageUri)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true) // no face detection
        startActivityForResult(intent, CROP_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 10000 && resultCode == Activity.RESULT_OK) {
            //进行裁剪
            startPhotoZoom(data!!.data)
        } else if (requestCode == 10001 && resultCode == Activity.RESULT_OK) {
            //进行裁剪
            startPhotoZoom(uri!!)
        } else if (requestCode == 10085 && resultCode == 10086) {
            Bus.getDefault().post(getString(R.string.USER_ICON_CHANGE))
            setResult(1024)
            finish()
        } else if (requestCode == CROP_PICTURE && resultCode == Activity.RESULT_OK) {
            presenter!!.userIconUpdate(finalPic!!)
        }
    }

    override fun userIconUpdateSuccess(stringResponse: StringResponse) {
        response!!.model.avatars = stringResponse.data
        saveLoginResponse(this, response!!)
        Bus.getDefault().post(getString(R.string.USER_ICON_CHANGE))
        initData()
    }

    override fun userIconUpdateFail(throwable: Throwable) {
        showThrowable(throwable)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PHOTO_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            } else {
                showToast(getString(R.string.permission_denied))
            }
        } else if (requestCode == PICTURE_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPicture()
            } else {
                showToast(getString(R.string.permission_denied))
            }
        }
    }

    private fun getPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 10000)
    }

    private fun getPhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val version = Build.VERSION.SDK_INT
        val sdf = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        val filename = sdf.format(Date())
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            file = File(Environment.getExternalStorageDirectory(), filename + ".jpg")
            if (version < 24) {
                uri = Uri.fromFile(file)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            } else {
                val contentValues = ContentValues(1)
                contentValues.put(MediaStore.Images.Media.DATA, file!!.absolutePath)
                uri = application.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
        }
        startActivityForResult(intent, 10001)
    }
}