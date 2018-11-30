package com.buyint.mergerbot.UIs.verification.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v13.app.ActivityCompat
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.match.activity.QuickMatchActivity
import com.buyint.mergerbot.UIs.user.fragment.SetUserIconFragment
import com.buyint.mergerbot.Utility.GlideCircleTransform
import com.buyint.mergerbot.Utility.PermissionUtils
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.database.saveLoginResponse
import com.buyint.mergerbot.dto.*
import com.buyint.mergerbot.enums.AuthenticationType
import com.buyint.mergerbot.interfaces.IAccountVerificationGetMessage
import com.buyint.mergerbot.interfaces.IUserIconUpdate
import com.buyint.mergerbot.presenter.BusinessIdentityVerificationPresenter
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_verication_business_identity.*
import kotlinx.android.synthetic.main.toolbar_white.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by huheming on 2018/8/2
 */
class BusinessIdentityVerificationActivity : BaseActivity(), View.OnClickListener, IUserIconUpdate, IAccountVerificationGetMessage {

    private var file: File? = null
    private var lawyerAndUser = false
    private var aimer = true
    private val PHOTO_CODE = 10
    private val PICTURE_CODE = 20
    private val CROP_PICTURE = 30
    private var finalPic: File? = null
    private var cropImageUri: Uri? = null
    private var uri: Uri? = null
    private var presenter: BusinessIdentityVerificationPresenter? = null
    val request = AccountVerificationGetMessageRequest()
    private var username: CodeNameBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = BusinessIdentityVerificationPresenter(this, this)

        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verication_business_identity)

        toolbar_white_back.setOnClickListener {
            onBackPressed()
        }
        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_title.text = getString(R.string.commercial_identity)

        activity_verification_business_identity_bottom_tv.paint.flags = Paint.UNDERLINE_TEXT_FLAG

        //申请认证
        activity_verification_business_identity_button.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                if (TextUtils.isEmpty(activity_verification_business_identity_territory.text.toString().trim())) {
                    return
                }
                if (lawyerAndUser && !aimer) {
                    return
                }
                showLoadingFragment(R.id.activity_verification_business_identity_fl)
                requestVerification()
            }
        })

        activity_verification_business_identity_button.setOnClickListener {
            activity_verification_business_identity_button.selectBackground()
        }

        activity_verification_business_identity_iv_rl.setOnClickListener {
            val suif = SetUserIconFragment(object : SetUserIconFragment.SetUserIconFragmentListener {
                override fun photoClick() {
                    //检查应用必备权限
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        val strings = PermissionUtils.hasPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        if (strings.isNotEmpty()) {
                            ActivityCompat.requestPermissions(this@BusinessIdentityVerificationActivity, strings, PHOTO_CODE)
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
                        val strings = PermissionUtils.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        if (strings.isNotEmpty()) {
                            ActivityCompat.requestPermissions(this@BusinessIdentityVerificationActivity, strings, PICTURE_CODE)
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

        activity_verification_business_identity_username.setOnClickListener(this)
        activity_verification_business_identity_position.setOnClickListener(this)
        activity_verification_business_identity_company.setOnClickListener(this)
        activity_verification_business_identity_email.setOnClickListener(this)
        activity_verification_business_identity_website.setOnClickListener(this)
        activity_verification_business_identity_territory.setOnClickListener(this)

        //商业律师认证
        activity_verication_business_identity_ll1.setOnClickListener {
            if (lawyerAndUser) {
                activity_verication_business_identity_iv1.setImageResource(R.mipmap.rentage_kuang)
            } else {
                activity_verication_business_identity_iv1.setImageResource(R.mipmap.rentage_gou)
            }
            lawyerAndUser = !lawyerAndUser
        }

        //AIMER自动认证
        activity_verication_business_identity_ll2.setOnClickListener {
            if (aimer) {
                activity_verication_business_identity_iv2.setImageResource(R.mipmap.rentage_kuang)
            } else {
                activity_verication_business_identity_iv2.setImageResource(R.mipmap.rentage_gou)
            }
            aimer = !aimer
        }

        username = CodeNameBean()
        val response = getLoginResponse(this)
        if (!TextUtils.isEmpty(response!!.model.userName)) {
            username!!.name = response.model.userName
            activity_verification_business_identity_username.text = response.model.userName
        }
        if (response.model.userWorkMessage != null) {
            if (!TextUtils.isEmpty(response.model.userWorkMessage.companyName)) {
                activity_verification_business_identity_company.text = response.model.userWorkMessage.companyName
            }
            if (!TextUtils.isEmpty(response.model.userWorkMessage.position)) {
                activity_verification_business_identity_position.text = response.model.userWorkMessage.position
            }
            if (!TextUtils.isEmpty(response.model.userWorkMessage.workEmail)) {
                activity_verification_business_identity_email.text = response.model.userWorkMessage.workEmail
            }
            if (!TextUtils.isEmpty(response.model.userWorkMessage.companyWebSite)) {
                activity_verification_business_identity_website.text = response.model.userWorkMessage.companyWebSite
            }
        }
    }

    override fun onClick(v: View?) {
        val intent = Intent(this, QuickMatchActivity::class.java)
        when (v) {
            activity_verification_business_identity_username -> {
                intent.putExtra(getString(R.string.TYPE), 13)
                intent.putExtra(getString(R.string.data1), username)
                intent.putExtra(getString(R.string.data2), activity_verification_business_identity_company.text.toString().trim())
                intent.putExtra(getString(R.string.TITLE), getString(R.string.please_write_your_name))
                startActivityForResult(intent, 20)
            }
            activity_verification_business_identity_position -> {
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_your_position))
                startActivityForResult(intent, 22)
            }
            activity_verification_business_identity_territory -> {
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_your_territory))
                startActivityForResult(intent, 23)
            }
            activity_verification_business_identity_company -> {
                intent.putExtra(getString(R.string.TYPE), 14)
                intent.putExtra(getString(R.string.data1), username)
                intent.putExtra(getString(R.string.data2), activity_verification_business_identity_company.text.toString().trim())
                intent.putExtra(getString(R.string.TITLE), getString(R.string.please_write_company_name))
                startActivityForResult(intent, 21)
            }
            activity_verification_business_identity_email -> {
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_your_email))
                startActivityForResult(intent, 25)
            }
            activity_verification_business_identity_website -> {
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_your_web))
                startActivityForResult(intent, 26)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 20 && resultCode == 1001) {
            val data1 = data!!.getSerializableExtra(getString(R.string.data1)) as CodeNameBean
            username = data1
            activity_verification_business_identity_username.text = data1.name
        } else if (requestCode == 21 && resultCode == 1001) {
            val data2 = data!!.getStringExtra(getString(R.string.data2))
            activity_verification_business_identity_company.text = data2
        } else if (requestCode == 22 && resultCode == 1001) {
            val stringExtra = data!!.getStringExtra(getString(R.string.DATA))
            activity_verification_business_identity_position.text = stringExtra
        } else if (requestCode == 23 && resultCode == 1001) {
            val stringExtra = data!!.getStringExtra(getString(R.string.DATA))
            activity_verification_business_identity_territory.text = stringExtra
        } else if (requestCode == 25 && resultCode == 1001) {
            val stringExtra = data!!.getStringExtra(getString(R.string.DATA))
            activity_verification_business_identity_email.text = stringExtra
        } else if (requestCode == 26 && resultCode == 1001) {
            val stringExtra = data!!.getStringExtra(getString(R.string.DATA))
            activity_verification_business_identity_website.text = stringExtra
        } else if (requestCode == 10000 && resultCode == Activity.RESULT_OK) {
            //进行裁剪
            startPhotoZoom(data!!.data)
        } else if (requestCode == 10001 && resultCode == Activity.RESULT_OK) {
            //进行裁剪
            startPhotoZoom(uri!!)
        } else if (requestCode == CROP_PICTURE && resultCode == Activity.RESULT_OK) {
            presenter!!.userIconUpdate(finalPic!!)
        } else if (requestCode == 1001 && resultCode == 1001) {
            finish()
        }
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

    private fun startPhotoZoom(uri: Uri) {
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

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    override fun userIconUpdateSuccess(response: StringResponse) {
        if (!TextUtils.isEmpty(response.data)) {
            Glide.with(this).load(response.data).transform(GlideCircleTransform(this)).placeholder(R.mipmap.default_user).error(R.mipmap.default_user).into(activity_verification_business_identity_iv)
            val loginResponse = getLoginResponse(this)
            loginResponse!!.model.avatars = response.data
            saveLoginResponse(this, loginResponse)
        }
    }

    override fun userIconUpdateFail(throwable: Throwable) {
        showThrowable(throwable)
    }

    override fun accountVerificationGetMessageSuccess(response: AccountVerificationGetMessageResponse) {
        removeLoadingFragment()
        if (!TextUtils.isEmpty(response.errorMsg)) {
            showToast(response.errorMsg)
            return
        }

        val amiAccessBean = AmiAccessBean()
        amiAccessBean.companyEmail = activity_verification_business_identity_email.text.toString().trim()
        amiAccessBean.companyName = activity_verification_business_identity_company.text.toString().trim()
        amiAccessBean.companySite = activity_verification_business_identity_website.text.toString().trim()
        amiAccessBean.domain = activity_verification_business_identity_territory.text.toString().trim()
        amiAccessBean.position = activity_verification_business_identity_position.text.toString().trim()
        amiAccessBean.realName = activity_verification_business_identity_username.text.toString().trim()

        if (!TextUtils.isEmpty(response.data!!.id) && !response.data!!.authenticationResult.equals("FAIL_NO_USER")) {
            val intent = Intent(this@BusinessIdentityVerificationActivity, VerificationVerificaionCodeActivity::class.java)
            intent.putExtra(getString(R.string.DATA), response.data!!.id)
            intent.putExtra(getString(R.string.TYPE), request)
            intent.putExtra("", lawyerAndUser)
            intent.putExtra("amiAccessBean", amiAccessBean)
            startActivityForResult(intent, 1001)
        } else {
            if (lawyerAndUser) {
                //上传地理位置获取律师列表
                val intent = Intent(this@BusinessIdentityVerificationActivity, InviteLawyerOneActivity::class.java)
                intent.putExtra(getString(R.string.DATA), activity_verification_business_identity_territory.text.toString().trim())
                intent.putExtra("amiAccessBean", amiAccessBean)
                startActivityForResult(intent, 1001)
            }
        }
    }

    override fun accountVerificationGetMessageFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    fun requestVerification() {
        request.realName = activity_verification_business_identity_username.text.toString().trim()
        request.position = activity_verification_business_identity_position.text.toString().trim()
        request.companyName = activity_verification_business_identity_company.text.toString().trim()
        request.companyEmail = activity_verification_business_identity_email.text.toString().trim()
        request.companySite = activity_verification_business_identity_website.text.toString().trim()
        request.authenticationType = ArrayList()
        if (lawyerAndUser) {
            request.authenticationType!!.add(AuthenticationType.USER_SUGGESTED.name)
        }
        if (aimer) {
            request.authenticationType!!.add(AuthenticationType.AUTH_BY_AIMER.name)
        }
        presenter!!.accountVerificationGetMessage(request)
    }

}