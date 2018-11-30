package com.buyint.mergerbot.UIs.verification.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.*
import com.buyint.mergerbot.helper.HttpHelper
import kotlinx.android.synthetic.main.activity_access_invite.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * created by licheng  on date 2018/8/16
 */
class InviteActivity : BaseActivity() {

    private var amiAccessBean: AmiAccessBean? = null
    private var layerbean: LayerListBean? = null
    private var inviteLawyerBean: InviteLawyerBean? = null
    private var type = 0

    private var layerlist = ArrayList<layersList>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleWhiteAndTextBlank()
        setContentView(R.layout.activity_access_invite)
        toolbar_white_title.text = getString(R.string.access_send_invite)
        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }

        type = intent.getIntExtra(getString(R.string.TYPE), 0)

        when (type) {
            0 -> {
                amiAccessBean = intent.getSerializableExtra("amiAccessBean") as AmiAccessBean?
                layerbean = intent.getSerializableExtra("layerbean") as LayerListBean?
                tv_email.text = "TO:  ${layerbean?.email}"
            }
            1 -> {
                amiAccessBean = intent.getSerializableExtra("amiAccessBean") as AmiAccessBean?
                inviteLawyerBean = intent.getSerializableExtra(getString(R.string.DATA)) as InviteLawyerBean
                tv_email.text = "TO:  ${inviteLawyerBean?.email}"
            }
            2 -> {
                val email = intent.getStringExtra(getString(R.string.EMAIL))
                tv_email.text = "TO:  $email"
                toolbar_white_title.text = getString(R.string.send_email)
                edit_content.hint = getString(R.string.please_fill_in_the_email)
            }
        }

        btn_send.setOnClickListener {
            showLoadingFragment(R.id.activity_business_identity_verification_invite_lawyer_fl)

            when (type) {
                0 -> {
                    val layerSendRequest = LayerSendRequest()
                    layerSendRequest.emailContent = edit_content.text.toString().trim()
                    layerSendRequest.lawyerAuthenticationMessage = amiAccessBean
                    val bean = layersList()
                    bean.layerId = layerbean?.id
                    bean.layerName = layerbean?.name
                    layerlist.add(bean)
                    layerSendRequest.layers = layerlist

                    HttpHelper.getSendLawyerList(layerSendRequest).subscribe({
                        val intent = Intent(this@InviteActivity, InviteSendSuccessActivity::class.java)
                        startActivity(intent)
                        removeLoadingFragment()
                    }, {
                        removeLoadingFragment()
                        showThrowable(it)
                    })
                }
                1 -> {
                    val request = SendMessageToCreateLawyerRequest()
                    request.emailContent = edit_content.text.toString().trim()
                    request.invitelayers = inviteLawyerBean
                    request.lawyerAuthenticationMessage = amiAccessBean
                    HttpHelper.sendMessageToCreateLawyer(request).subscribe({
                        val intent = Intent(this@InviteActivity, InviteSendSuccessActivity::class.java)
                        startActivity(intent)
                        removeLoadingFragment()
                    }, {
                        removeLoadingFragment()
                        showThrowable(it)
                    })
                }
                2 -> {

                }
            }
        }
    }
}
