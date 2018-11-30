package com.buyint.mergerbot.UIs.verification.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.verification.adapter.BusinessIdentityVerificationInviteLawyerAdapter
import com.buyint.mergerbot.UIs.verification.mvp.LayerlistContract
import com.buyint.mergerbot.UIs.verification.mvp.LayerlistPresent
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.AmiAccessBean
import com.buyint.mergerbot.dto.LayerListBean
import com.buyint.mergerbot.dto.LayerlistRequest
import com.buyint.mergerbot.stroage.PreferencesKeeper
import kotlinx.android.synthetic.main.activity_business_identity_verification_invite_lawyer.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/8/7
 */
class BusinessIdentityVerificationInviteLawyerActivity : BaseActivity(), LayerlistContract.View {

    private val adapter_lawyer: BusinessIdentityVerificationInviteLawyerAdapter   by lazy {
        BusinessIdentityVerificationInviteLawyerAdapter(this@BusinessIdentityVerificationInviteLawyerActivity) { position ->

            val intent = Intent(this@BusinessIdentityVerificationInviteLawyerActivity, InviteActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 0)
            intent.putExtra("amiAccessBean", amiAccessBean)
            intent.putExtra("layerbean", list_layer[position])
            startActivity(intent)
        }
    }
    private var amiAccessBean: AmiAccessBean? = null
    private lateinit var mPresent: LayerlistContract.Present
    private var list_layer = ArrayList<LayerListBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_identity_verification_invite_lawyer)
        LayerlistPresent(this)

        amiAccessBean = intent.getSerializableExtra("amiAccessBean") as AmiAccessBean?
        var layerRequest = LayerlistRequest()

        layerRequest.address = PreferencesKeeper.getString(this@BusinessIdentityVerificationInviteLawyerActivity, "city_name")
        layerRequest.domain = amiAccessBean?.domain
        mPresent.getLayerlist(layerRequest);

        toolbar_white_title.text = getString(R.string.access_please)
        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_right_tv.setOnClickListener { }

        val territory = intent.getStringExtra(getString(R.string.NAME))

        recycler_lawyer.apply {

            layoutManager = LinearLayoutManager(this@BusinessIdentityVerificationInviteLawyerActivity)
            adapter = adapter_lawyer
        }


    }

    override fun getLayerlistSuccess(list: List<LayerListBean>) {
        list_layer.clear()
        list_layer.addAll(list)

        adapter_lawyer.list.clear()
        adapter_lawyer.list.addAll(list)
        adapter_lawyer.notifyDataSetChanged()
    }

    override fun getLayerlistFailed() {
    }

    override fun setPresent(present: LayerlistContract.Present) {
        mPresent = present
    }
}