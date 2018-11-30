package com.buyint.mergerbot.UIs.contact

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.contact.mvp.ContactAddFirstContract
import com.buyint.mergerbot.UIs.contact.mvp.ContactAddFirstPresent
import com.buyint.mergerbot.UIs.match.activity.QuickMatchActivity
import com.buyint.mergerbot.UIs.setting.TextActivity
import com.buyint.mergerbot.UIs.user.fragment.SetUserIconFragment
import com.buyint.mergerbot.Utility.GlideCircleTransform
import com.buyint.mergerbot.base.AppApplication
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.CodeNameBean
import com.buyint.mergerbot.dto.ContactAddRequest
import com.buyint.mergerbot.dto.ContactPersonInfoBean
import com.buyint.mergerbot.dto.MatchRecordListModel
import com.buyint.mergerbot.enums.RelationType
import com.buyint.mergerbot.helper.HttpHelper
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_contact_add_first.*
import kotlinx.android.synthetic.main.layout_contact_title.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * created by licheng  on date 2018/8/7
 */
class ContactAddFirstActivity : BaseActivity(), ContactAddFirstContract.View {

    private lateinit var mPreset: ContactAddFirstContract.Present
    private lateinit var userName: CodeNameBean
    private var IsUp: Boolean = false
    private lateinit var personBean: ContactPersonInfoBean
    private var pic_url: String? = null
    private var matchModel: MatchRecordListModel? = null
    private var agree = true
    private var states = 1//1表示原生添加，2表示跳过来修改
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleWhiteAndTextBlank()
        setContentView(R.layout.activity_contact_add_first)
        tv_contact_title.text = getString(R.string.contact_add_first)
        ContactAddFirstPresent(this)
        hideKeyBoard()
        userName = CodeNameBean()
        personBean = ContactPersonInfoBean()
        if (null != intent.getSerializableExtra("model")) {
            matchModel = intent.getSerializableExtra("model") as MatchRecordListModel
        }

        activity_contact_add_first_policy.setOnClickListener {
            if (agree) {
                activity_contact_add_first_policy_iv.setImageResource(R.drawable.ring_gray)
            } else {
                activity_contact_add_first_policy_iv.setImageResource(R.mipmap.blue_success_2)
            }
            agree = !agree
        }
        activity_contact_add_first_policy_tv.setOnClickListener {
            val intent = Intent(this@ContactAddFirstActivity, TextActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 1)
            startActivity(intent)
        }

        if (null != matchModel) {
            states = 2
            tv_contact_title.text = getString(R.string.contact_put_title)
            IsUp = true
            ll_more.visibility = View.VISIBLE
            iv_contact_more.setImageResource(R.mipmap.icon_contact_up)
            if (!TextUtils.isEmpty(matchModel!!.userName)) {
                tv_contact_name.text = matchModel!!.userName
            }
            if (!TextUtils.isEmpty(matchModel!!.position)) {
                edit_job.setText(matchModel!!.position)
            }
            if (!TextUtils.isEmpty(matchModel!!.companyName)) {
                tv_contact_company.text = matchModel!!.companyName
            }
            personBean.personId = matchModel!!.personId
            mPreset.getInfo(matchModel!!.personId!!)
        }

        AppApplication.getAppApplication().addActivity(this@ContactAddFirstActivity)
        btn_contact_next.setOnClickListener {

            val name = tv_contact_name.text.toString().trim()
            val job = edit_job.text.toString().trim()
            val company = tv_contact_company.text.toString().trim()
            val mail = edit_mail.text.toString()

            val company_site = edit_company_site.text.toString().trim()
            val company_adress = edit_company_adress.text.toString().trim()
            val company_mail = edit_company_mail.text.toString().trim()
            val company_cellphone = edit_company_cellphone.text.toString().trim()
            val company_phone = edit_company_phone.text.toString().trim()

            if (!agree) {
                showToast(getString(R.string.comon_fill_info))
            } else if (TextUtils.isEmpty(name)) {
                showToast(getString(R.string.comon_fill_info))
            } else {
                personBean.name = name
                personBean.position = job
                personBean.companyName = company
                personBean.avatar = pic_url
                personBean.email = mail
                personBean.companyEmail = company_mail
                personBean.companySite = company_site
                personBean.companyAddress = company_adress
                personBean.phoneNumber = company_cellphone
                personBean.fixedPhoneNumber = company_phone

                if (null != mPersonInfo && null != mPersonInfo?.personInfo?.relationType) {

                    when {
                        mPersonInfo?.personInfo?.relationType.equals(RelationType.WORK_MATE.name) -> {
                            personBean.relationType = RelationType.WORK_MATE.name
                            val intent = Intent(this@ContactAddFirstActivity, ContactMateActivity::class.java)
                            intent.putExtra("personBean", personBean)
                            intent.putExtra("states", states)
                            intent.putExtra("mPersonInfo", mPersonInfo)
                            startActivity(intent)
                        }
                        mPersonInfo?.personInfo?.relationType.equals(RelationType.CUSTOMER.name) -> {
                            personBean.relationType = RelationType.CUSTOMER.name
                            val intent = Intent(this@ContactAddFirstActivity, ContactCusBusinActivity::class.java)
                            intent.putExtra("personBean", personBean)
                            intent.putExtra("states", states)
                            intent.putExtra("type", RelationType.CUSTOMER.name)
                            intent.putExtra("mPersonInfo", mPersonInfo)
                            startActivity(intent)
                        }
                        mPersonInfo?.personInfo?.relationType.equals(RelationType.BUSINESS_FRIEND.name) -> {
                            personBean.relationType = RelationType.BUSINESS_FRIEND.name
                            val intent = Intent(this@ContactAddFirstActivity, ContactCusBusinActivity::class.java)
                            intent.putExtra("personBean", personBean)
                            intent.putExtra("states", states)
                            intent.putExtra("type", RelationType.BUSINESS_FRIEND.name)
                            intent.putExtra("mPersonInfo", mPersonInfo)
                            startActivity(intent)
                        }
                        mPersonInfo?.personInfo?.relationType.equals(RelationType.OTHER.name) -> {
                            personBean.relationType = RelationType.OTHER.name
                            val intent = Intent(this@ContactAddFirstActivity, ContactOtherActivity::class.java)
                            intent.putExtra("personBean", personBean)
                            intent.putExtra("states", states)
                            intent.putExtra("mPersonInfo", mPersonInfo)
                            startActivity(intent)
                        }
                    }
                } else {
                    val intent = Intent(this@ContactAddFirstActivity, ContactAddSecondActivity::class.java)
                    intent.putExtra("personBean", personBean)
                    intent.putExtra("states", states)
                    intent.putExtra("mPersonInfo", mPersonInfo)
                    startActivity(intent)
                }
            }
        }

        iv_contact_back.setOnClickListener { finish() }

        iv_contact_more.setOnClickListener {
            if (IsUp) {
                IsUp = false
                ll_more.visibility = View.GONE
                iv_contact_more.setImageResource(R.mipmap.icon_contact_down)
            } else {
                IsUp = true
                ll_more.visibility = View.VISIBLE
                iv_contact_more.setImageResource(R.mipmap.icon_contact_up)
            }
        }

        ll_name.setOnClickListener {
            val intent = Intent(this@ContactAddFirstActivity, QuickMatchActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 13)
            intent.putExtra(getString(R.string.data1), userName)
            intent.putExtra(getString(R.string.data2), tv_contact_company.text.toString().trim())
            intent.putExtra(getString(R.string.TITLE), getString(R.string.please_write_your_name))
            startActivityForResult(intent, 10001)
        }

        ll_company.setOnClickListener {
            val intent = Intent(this@ContactAddFirstActivity, QuickMatchActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 14)
            intent.putExtra(getString(R.string.data1), userName)
            intent.putExtra(getString(R.string.data2), tv_contact_company.text.toString().trim())
            intent.putExtra(getString(R.string.TITLE), getString(R.string.please_write_company_name))
            startActivityForResult(intent, 10002)
        }

        //头像上传
        iv_pic.setOnClickListener {

            val rxPermissions = RxPermissions(this@ContactAddFirstActivity)
            val suif = SetUserIconFragment(object : SetUserIconFragment.SetUserIconFragmentListener {
                override fun photoClick() {
                    //检查应用必备权限
                    rxPermissions.request(Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe { granted ->
                                if (granted) {
                                    getPhoto()
                                } else {
                                    showToast(getString(R.string.permission_denied))
                                }
                            }
                }

                override fun pictureClick() {
                    //检查应用必备权限
                    rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe { granted ->
                                if (granted) {
                                    getPicture()

                                } else {
                                    showToast(getString(R.string.permission_denied))
                                }
                            }
                }
            })
            suif.show(supportFragmentManager, "SetUserIconFragment")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10001 && resultCode == 1001) {
            val data1 = data!!.getSerializableExtra(getString(R.string.data1)) as CodeNameBean
            userName = data1
            tv_contact_name.text = data1.name
        }
        if (requestCode == 10002 && resultCode == 1001) {
            val data2 = data!!.getStringExtra(getString(R.string.data2))
            tv_contact_company.text = data2
        }

        if (requestCode == 10000 && resultCode == Activity.RESULT_OK) {
            //进行裁剪
            startPhotoZoom(data!!.data)
        } else if (requestCode == 10001 && resultCode == Activity.RESULT_OK) {
            //进行裁剪
            startPhotoZoom(uri!!)
        } else if (requestCode == CROP_PICTURE && resultCode == Activity.RESULT_OK) {
            showLoadingFragment(R.id.activity_contact_add_first_fl)

            val create = RequestBody.create(MediaType.parse("multipart/form-data"), finalPic)
            val createFormData = MultipartBody.Part.createFormData("file", finalPic!!.name, create)
            HttpHelper.userIconUpdate(createFormData).subscribe({ url ->
                pic_url = url.data
                Glide.with(this@ContactAddFirstActivity).load(pic_url).transform(GlideCircleTransform(this@ContactAddFirstActivity)).placeholder(R.mipmap.default_user).into(iv_pic)
                removeLoadingFragment()
            }, {
                removeLoadingFragment()
            })
        }
    }

    private var file: File? = null
    private var uri: Uri? = null
    private val CROP_PICTURE = 30
    private var finalPic: File? = null
    private var cropImageUri: Uri? = null
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

    private fun getPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 10000)
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

    private fun handleInfo() {
        if (!TextUtils.isEmpty(mPersonInfo!!.personInfo.companySite)) {
            edit_company_site.setText(mPersonInfo!!.personInfo.companySite)
        }
        if (!TextUtils.isEmpty(mPersonInfo!!.personInfo.companyAddress)) {
            edit_company_adress.setText(mPersonInfo!!.personInfo.companyAddress)
        }
        if (!TextUtils.isEmpty(mPersonInfo!!.personInfo.companyEmail)) {
            edit_company_mail.setText(mPersonInfo!!.personInfo.companyEmail)
        }
        if (!TextUtils.isEmpty(mPersonInfo!!.personInfo.phoneNumber)) {
            edit_company_cellphone.setText(mPersonInfo!!.personInfo.phoneNumber)
        }
        if (!TextUtils.isEmpty(mPersonInfo!!.personInfo.fixedPhoneNumber)) {
            edit_company_phone.setText(mPersonInfo!!.personInfo.fixedPhoneNumber)
        }
    }

    //=====================net=============================
    private var mPersonInfo: ContactAddRequest? = null

    override fun getInfoSuccess(contactAddRequest: ContactAddRequest) {
        mPersonInfo = contactAddRequest
        handleInfo()
    }

    override fun getInfoFailed() {
        mPersonInfo = ContactAddRequest()
    }

    override fun setPresent(present: ContactAddFirstContract.Present) {
        mPreset = present
    }

}
