package com.buyint.mergerbot.UIs.user.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.match.activity.QuickMatchActivity
import com.buyint.mergerbot.UIs.user.adapter.ArchivesFragmentPersonAdapter
import com.buyint.mergerbot.base.BaseFragment
import com.buyint.mergerbot.dto.UpdatePrivacySettingModel
import com.buyint.mergerbot.enums.*

/**
 * Created by huheming on 2018/7/11
 */
class ArchivesFragment() : BaseFragment(), View.OnClickListener {
    @SuppressLint("ValidFragment")
    constructor(model: UpdatePrivacySettingModel?) : this() {
        this.model = model
    }

    fun getModel(): UpdatePrivacySettingModel? {
        return model
    }

    private var model: UpdatePrivacySettingModel? = null
    private var adapter: ArchivesFragmentPersonAdapter? = null

    private var view_permission_rl1: RelativeLayout? = null
    private var view_permission_rl2: RelativeLayout? = null
    private var view_permission_rl3: RelativeLayout? = null
    private var name_rl1: RelativeLayout? = null
    private var name_rl2: RelativeLayout? = null
    private var name_rl3: RelativeLayout? = null
    private var share_operation_rl1: RelativeLayout? = null
    private var share_operation_rl2: RelativeLayout? = null
    private var share_operation_rl3: RelativeLayout? = null
    private var contact_information_rl1: RelativeLayout? = null
    private var contact_information_rl2: RelativeLayout? = null
    private var contact_information_rl3: RelativeLayout? = null
    private var contact_information_rl4: RelativeLayout? = null
    private var easy_to_contact_time_rl1: RelativeLayout? = null
    private var easy_to_contact_time_rl2: RelativeLayout? = null
    private var easy_to_contact_time_rl3: RelativeLayout? = null
    private var shielded_business_rl1: RelativeLayout? = null
    private var shielded_business_rl2: RelativeLayout? = null
    private var business_file_show_part_rl1: RelativeLayout? = null
    private var business_file_show_part_rl2: RelativeLayout? = null
    private var business_file_show_part_rl3: RelativeLayout? = null
    private var business_file_show_part_rl4: RelativeLayout? = null
    private var business_file_show_part_rl5: RelativeLayout? = null
    private var business_file_show_part_rl6: RelativeLayout? = null
    private var business_file_show_part_rl7: RelativeLayout? = null
    private var business_file_show_part_rl8: RelativeLayout? = null
    private var view_permission_iv1: ImageView? = null
    private var view_permission_iv2: ImageView? = null
    private var view_permission_iv3: ImageView? = null
    private var name_iv1: ImageView? = null
    private var name_iv2: ImageView? = null
    private var name_iv3: ImageView? = null
    private var share_operation_iv1: ImageView? = null
    private var share_operation_iv2: ImageView? = null
    private var share_operation_iv3: ImageView? = null
    private var contact_information_iv1: ImageView? = null
    private var contact_information_iv2: ImageView? = null
    private var contact_information_iv3: ImageView? = null
    private var contact_information_iv4: ImageView? = null
    private var easy_to_contact_time_iv1: ImageView? = null
    private var easy_to_contact_time_iv2: ImageView? = null
    private var easy_to_contact_time_iv3: ImageView? = null
    private var shielded_business_iv1: ImageView? = null
    private var shielded_business_iv2: ImageView? = null
    private var business_file_show_part_iv1: ImageView? = null
    private var business_file_show_part_iv2: ImageView? = null
    private var business_file_show_part_iv3: ImageView? = null
    private var business_file_show_part_iv4: ImageView? = null
    private var business_file_show_part_iv5: ImageView? = null
    private var business_file_show_part_iv6: ImageView? = null
    private var business_file_show_part_iv7: ImageView? = null
    private var business_file_show_part_iv8: ImageView? = null

    private var tvAddPeopleList: TextView? = null
    private var recyclerview: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_archives, null, false)

        tvAddPeopleList = inflate.findViewById(R.id.fragment_archives_add_people_list) as TextView
        recyclerview = inflate.findViewById(R.id.fragment_archives_recycler) as RecyclerView
        recyclerview!!.layoutManager = LinearLayoutManager(activity)
        recyclerview!!.isNestedScrollingEnabled = false

        view_permission_rl1 = inflate.findViewById(R.id.fragment_archives_view_permission_rl1) as RelativeLayout
        view_permission_rl2 = inflate.findViewById(R.id.fragment_archives_view_permission_rl2) as RelativeLayout
        view_permission_rl3 = inflate.findViewById(R.id.fragment_archives_view_permission_rl3) as RelativeLayout
        name_rl1 = inflate.findViewById(R.id.fragment_archives_name_rl1) as RelativeLayout
        name_rl2 = inflate.findViewById(R.id.fragment_archives_name_rl2) as RelativeLayout
        name_rl3 = inflate.findViewById(R.id.fragment_archives_name_rl3) as RelativeLayout
        share_operation_rl1 = inflate.findViewById(R.id.fragment_archives_share_operation_rl1) as RelativeLayout
        share_operation_rl2 = inflate.findViewById(R.id.fragment_archives_share_operation_rl2) as RelativeLayout
        share_operation_rl3 = inflate.findViewById(R.id.fragment_archives_share_operation_rl3) as RelativeLayout
        contact_information_rl1 = inflate.findViewById(R.id.fragment_archives_contact_information_rl1) as RelativeLayout
        contact_information_rl2 = inflate.findViewById(R.id.fragment_archives_contact_information_rl2) as RelativeLayout
        contact_information_rl3 = inflate.findViewById(R.id.fragment_archives_contact_information_rl3) as RelativeLayout
        contact_information_rl4 = inflate.findViewById(R.id.fragment_archives_contact_information_rl4) as RelativeLayout
        easy_to_contact_time_rl1 = inflate.findViewById(R.id.fragment_archives_easy_to_contact_time_rl1) as RelativeLayout
        easy_to_contact_time_rl2 = inflate.findViewById(R.id.fragment_archives_easy_to_contact_time_rl2) as RelativeLayout
        easy_to_contact_time_rl3 = inflate.findViewById(R.id.fragment_archives_easy_to_contact_time_rl3) as RelativeLayout
        shielded_business_rl1 = inflate.findViewById(R.id.fragment_archives_shielded_business_rl1) as RelativeLayout
        shielded_business_rl2 = inflate.findViewById(R.id.fragment_archives_shielded_business_rl2) as RelativeLayout
        business_file_show_part_rl1 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_rl1) as RelativeLayout
        business_file_show_part_rl2 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_rl2) as RelativeLayout
        business_file_show_part_rl3 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_rl3) as RelativeLayout
        business_file_show_part_rl4 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_rl4) as RelativeLayout
        business_file_show_part_rl5 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_rl5) as RelativeLayout
        business_file_show_part_rl6 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_rl6) as RelativeLayout
        business_file_show_part_rl7 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_rl7) as RelativeLayout
        business_file_show_part_rl8 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_rl8) as RelativeLayout

        view_permission_iv1 = inflate.findViewById(R.id.fragment_archives_view_permission_iv1) as ImageView
        view_permission_iv2 = inflate.findViewById(R.id.fragment_archives_view_permission_iv2) as ImageView
        view_permission_iv3 = inflate.findViewById(R.id.fragment_archives_view_permission_iv3) as ImageView
        name_iv1 = inflate.findViewById(R.id.fragment_archives_name_iv1) as ImageView
        name_iv2 = inflate.findViewById(R.id.fragment_archives_name_iv2) as ImageView
        name_iv3 = inflate.findViewById(R.id.fragment_archives_name_iv3) as ImageView
        share_operation_iv1 = inflate.findViewById(R.id.fragment_archives_share_operation_iv1) as ImageView
        share_operation_iv2 = inflate.findViewById(R.id.fragment_archives_share_operation_iv2) as ImageView
        share_operation_iv3 = inflate.findViewById(R.id.fragment_archives_share_operation_iv3) as ImageView
        contact_information_iv1 = inflate.findViewById(R.id.fragment_archives_contact_information_iv1) as ImageView
        contact_information_iv2 = inflate.findViewById(R.id.fragment_archives_contact_information_iv2) as ImageView
        contact_information_iv3 = inflate.findViewById(R.id.fragment_archives_contact_information_iv3) as ImageView
        contact_information_iv4 = inflate.findViewById(R.id.fragment_archives_contact_information_iv4) as ImageView
        easy_to_contact_time_iv1 = inflate.findViewById(R.id.fragment_archives_easy_to_contact_time_iv1) as ImageView
        easy_to_contact_time_iv2 = inflate.findViewById(R.id.fragment_archives_easy_to_contact_time_iv2) as ImageView
        easy_to_contact_time_iv3 = inflate.findViewById(R.id.fragment_archives_easy_to_contact_time_iv3) as ImageView
        shielded_business_iv1 = inflate.findViewById(R.id.fragment_archives_shielded_business_iv1) as ImageView
        shielded_business_iv2 = inflate.findViewById(R.id.fragment_archives_shielded_business_iv2) as ImageView
        business_file_show_part_iv1 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_iv1) as ImageView
        business_file_show_part_iv2 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_iv2) as ImageView
        business_file_show_part_iv3 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_iv3) as ImageView
        business_file_show_part_iv4 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_iv4) as ImageView
        business_file_show_part_iv5 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_iv5) as ImageView
        business_file_show_part_iv6 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_iv6) as ImageView
        business_file_show_part_iv7 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_iv7) as ImageView
        business_file_show_part_iv8 = inflate.findViewById(R.id.fragment_archives_business_file_show_part_iv8) as ImageView

        view_permission_rl1!!.setOnClickListener(this)
        view_permission_rl2!!.setOnClickListener(this)
        view_permission_rl3!!.setOnClickListener(this)
        name_rl1!!.setOnClickListener(this)
        name_rl2!!.setOnClickListener(this)
        name_rl3!!.setOnClickListener(this)
        share_operation_rl1!!.setOnClickListener(this)
        share_operation_rl2!!.setOnClickListener(this)
        share_operation_rl3!!.setOnClickListener(this)
        contact_information_rl1!!.setOnClickListener(this)
        contact_information_rl2!!.setOnClickListener(this)
        contact_information_rl3!!.setOnClickListener(this)
        contact_information_rl4!!.setOnClickListener(this)
        easy_to_contact_time_rl1!!.setOnClickListener(this)
        easy_to_contact_time_rl2!!.setOnClickListener(this)
        easy_to_contact_time_rl3!!.setOnClickListener(this)
        shielded_business_rl1!!.setOnClickListener(this)
        shielded_business_rl2!!.setOnClickListener(this)
        business_file_show_part_rl1!!.setOnClickListener(this)
        business_file_show_part_rl2!!.setOnClickListener(this)
        business_file_show_part_rl3!!.setOnClickListener(this)
        business_file_show_part_rl4!!.setOnClickListener(this)
        business_file_show_part_rl5!!.setOnClickListener(this)
        business_file_show_part_rl6!!.setOnClickListener(this)
        business_file_show_part_rl7!!.setOnClickListener(this)
        business_file_show_part_rl8!!.setOnClickListener(this)
        tvAddPeopleList!!.setOnClickListener(this)

        initData()

        return inflate
    }

    private fun initData() {
        if (model == null) {
            model = UpdatePrivacySettingModel()
            model!!.commercialRecord = ArrayList()
            model!!.shielding = ArrayList()
            model!!.contactTime = ContactTime.ANYTIME.name
            model!!.contactWay = ContactWay.AIMER.name
            model!!.showNameType = ShowNameType.REALNAME.name
            model!!.changeMyFile = "1"
            model!!.viewCommercialPermission = ViewCommercialPermission.HIDDEN.name
            model!!.commercialRecord.add(ShowPart.RESUME.name)
            model!!.commercialRecord.add(ShowPart.LINGUISTIC_COMPETENCE.name)
            model!!.commercialRecord.add(ShowPart.INDUSTRY.name)
            model!!.commercialRecord.add(ShowPart.ACHIEVEMENTS.name)
            model!!.commercialRecord.add(ShowPart.TRANSACTION.name)
            model!!.commercialRecord.add(ShowPart.COMMERCIAL_ACTIVITY.name)
            model!!.commercialRecord.add(ShowPart.EDUCATION_EXPERIENCE.name)
            model!!.commercialRecord.add(ShowPart.OVERHEAD_INFORMATION.name)
        }
        if (model!!.commercialRecord == null) {
            model!!.commercialRecord = ArrayList()
            model!!.commercialRecord.add(ShowPart.RESUME.name)
            model!!.commercialRecord.add(ShowPart.LINGUISTIC_COMPETENCE.name)
            model!!.commercialRecord.add(ShowPart.INDUSTRY.name)
            model!!.commercialRecord.add(ShowPart.ACHIEVEMENTS.name)
            model!!.commercialRecord.add(ShowPart.TRANSACTION.name)
            model!!.commercialRecord.add(ShowPart.COMMERCIAL_ACTIVITY.name)
            model!!.commercialRecord.add(ShowPart.EDUCATION_EXPERIENCE.name)
            model!!.commercialRecord.add(ShowPart.OVERHEAD_INFORMATION.name)
        }
        if (model!!.shielding == null) {
            model!!.shielding = ArrayList()
        }
        if (TextUtils.isEmpty(model!!.contactTime)) {
            model!!.contactTime = ContactTime.ANYTIME.name
        }
        if (TextUtils.isEmpty(model!!.contactWay)) {
            model!!.contactWay = ContactWay.AIMER.name
        }
        if (TextUtils.isEmpty(model!!.showNameType)) {
            model!!.showNameType = ShowNameType.REALNAME.name
        }
        if (TextUtils.isEmpty(model!!.changeMyFile)) {
            model!!.changeMyFile = "1"
        }
        if (TextUtils.isEmpty(model!!.viewCommercialPermission)) {
            model!!.viewCommercialPermission = ViewCommercialPermission.HIDDEN.name
        }
        adapter = ArchivesFragmentPersonAdapter(activity!!, model!!.shielding)
        recyclerview!!.adapter = adapter
        when (model!!.viewCommercialPermission) {
            ViewCommercialPermission.HIDDEN.name -> {
                view_permission_iv1!!.setImageResource(R.drawable.ring_blue)
            }
            ViewCommercialPermission.PUBLIC.name -> {
                view_permission_iv2!!.setImageResource(R.drawable.ring_blue)
            }
            ViewCommercialPermission.VERIFICATION.name -> {
                view_permission_iv3!!.setImageResource(R.drawable.ring_blue)
            }
        }
        when (model!!.showNameType) {
            ShowNameType.REALNAME.name -> {
                name_iv1!!.setImageResource(R.drawable.ring_blue)
            }
            ShowNameType.NAMDE.name -> {
                name_iv2!!.setImageResource(R.drawable.ring_blue)
            }
            ShowNameType.NICKNAME.name -> {
                name_iv3!!.setImageResource(R.drawable.ring_blue)
            }
        }
        when (model!!.changeMyFile) {
            "1" -> {
                share_operation_iv1!!.setImageResource(R.drawable.ring_blue)
            }
            "2" -> {
                share_operation_iv2!!.setImageResource(R.drawable.ring_blue)
            }
           "3" -> {
               share_operation_iv3!!.setImageResource(R.drawable.ring_blue)
            }
        }
        when (model!!.contactWay) {
            ContactWay.AIMER.name -> {
                contact_information_iv1!!.setImageResource(R.drawable.ring_blue)
            }
            ContactWay.EMAIL.name -> {
                contact_information_iv2!!.setImageResource(R.drawable.ring_blue)
            }
            ContactWay.PHONE.name -> {
                contact_information_iv3!!.setImageResource(R.drawable.ring_blue)
            }
            ContactWay.ANYWAY.name -> {
                contact_information_iv4!!.setImageResource(R.drawable.ring_blue)
            }
        }
        when (model!!.contactTime) {
            ContactTime.ANYTIME.name -> {
                easy_to_contact_time_iv1!!.setImageResource(R.drawable.ring_blue)
            }
            ContactTime.CONVENIENTTIME.name -> {
                easy_to_contact_time_iv2!!.setImageResource(R.drawable.ring_blue)
            }
            ContactTime.CUSTOM.name -> {
                easy_to_contact_time_iv3!!.setImageResource(R.drawable.ring_blue)
            }
        }
        if (model!!.shielding == null || model!!.shielding.size == 0) {
            shielded_business_iv1!!.setImageResource(R.drawable.ring_blue)
            recyclerview!!.visibility = View.GONE
            tvAddPeopleList!!.visibility = View.GONE
        } else {
            shielded_business_iv2!!.setImageResource(R.drawable.ring_blue)
            recyclerview!!.visibility = View.VISIBLE
            tvAddPeopleList!!.visibility = View.VISIBLE
        }
        if (model!!.commercialRecord.contains(ShowPart.RESUME.name)) {//个人概述
            business_file_show_part_iv1!!.setImageResource(R.drawable.ring_blue)
        }
        if (model!!.commercialRecord.contains(ShowPart.LINGUISTIC_COMPETENCE.name)) {//语言能力
            business_file_show_part_iv2!!.setImageResource(R.drawable.ring_blue)
        }
        if (model!!.commercialRecord.contains(ShowPart.INDUSTRY.name)) {//聚焦领域
            business_file_show_part_iv3!!.setImageResource(R.drawable.ring_blue)
        }
        if (model!!.commercialRecord.contains(ShowPart.ACHIEVEMENTS.name)) {//业绩成就
            business_file_show_part_iv4!!.setImageResource(R.drawable.ring_blue)
        }
        if (model!!.commercialRecord.contains(ShowPart.TRANSACTION.name)) {//历史交易
            business_file_show_part_iv5!!.setImageResource(R.drawable.ring_blue)
        }
        if (model!!.commercialRecord.contains(ShowPart.COMMERCIAL_ACTIVITY.name)) {//商务活动
            business_file_show_part_iv6!!.setImageResource(R.drawable.ring_blue)
        }
        if (model!!.commercialRecord.contains(ShowPart.EDUCATION_EXPERIENCE.name)) {//教育经历
            business_file_show_part_iv7!!.setImageResource(R.drawable.ring_blue)
        }
        if (model!!.commercialRecord.contains(ShowPart.OVERHEAD_INFORMATION.name)) {//附加信息
            business_file_show_part_iv8!!.setImageResource(R.drawable.ring_blue)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            view_permission_rl1 -> {
                view_permission_iv1!!.setImageResource(R.drawable.ring_blue)
                view_permission_iv2!!.setImageResource(R.drawable.ring_gray)
                view_permission_iv3!!.setImageResource(R.drawable.ring_gray)
                model!!.viewCommercialPermission = ViewCommercialPermission.HIDDEN.name
            }
            view_permission_rl2 -> {
                view_permission_iv1!!.setImageResource(R.drawable.ring_gray)
                view_permission_iv2!!.setImageResource(R.drawable.ring_blue)
                view_permission_iv3!!.setImageResource(R.drawable.ring_gray)
                model!!.viewCommercialPermission = ViewCommercialPermission.PUBLIC.name
            }
            view_permission_rl3 -> {
                view_permission_iv1!!.setImageResource(R.drawable.ring_gray)
                view_permission_iv2!!.setImageResource(R.drawable.ring_gray)
                view_permission_iv3!!.setImageResource(R.drawable.ring_blue)
                model!!.viewCommercialPermission = ViewCommercialPermission.VERIFICATION.name
            }
            name_rl1 -> {
                name_iv1!!.setImageResource(R.drawable.ring_blue)
                name_iv2!!.setImageResource(R.drawable.ring_gray)
                name_iv3!!.setImageResource(R.drawable.ring_gray)
                model!!.showNameType = ShowNameType.REALNAME.name
            }
            name_rl2 -> {
                name_iv1!!.setImageResource(R.drawable.ring_gray)
                name_iv2!!.setImageResource(R.drawable.ring_blue)
                name_iv3!!.setImageResource(R.drawable.ring_gray)
                model!!.showNameType = ShowNameType.NAMDE.name
            }
            name_rl3 -> {
                name_iv1!!.setImageResource(R.drawable.ring_gray)
                name_iv2!!.setImageResource(R.drawable.ring_gray)
                name_iv3!!.setImageResource(R.drawable.ring_blue)
                model!!.showNameType = ShowNameType.NICKNAME.name
            }
            share_operation_rl1 -> {
                share_operation_iv1!!.setImageResource(R.drawable.ring_blue)
                share_operation_iv2!!.setImageResource(R.drawable.ring_gray)
                share_operation_iv3!!.setImageResource(R.drawable.ring_gray)
                model!!.changeMyFile = "1"
            }
            share_operation_rl2 -> {
                share_operation_iv1!!.setImageResource(R.drawable.ring_gray)
                share_operation_iv2!!.setImageResource(R.drawable.ring_blue)
                share_operation_iv3!!.setImageResource(R.drawable.ring_gray)
                model!!.changeMyFile = "1"
            }
            share_operation_rl3 -> {
                share_operation_iv1!!.setImageResource(R.drawable.ring_gray)
                share_operation_iv2!!.setImageResource(R.drawable.ring_gray)
                share_operation_iv3!!.setImageResource(R.drawable.ring_blue)
                model!!.changeMyFile = "1"
            }
            contact_information_rl1 -> {
                contact_information_iv1!!.setImageResource(R.drawable.ring_blue)
                contact_information_iv2!!.setImageResource(R.drawable.ring_gray)
                contact_information_iv3!!.setImageResource(R.drawable.ring_gray)
                contact_information_iv4!!.setImageResource(R.drawable.ring_gray)
                model!!.contactWay = ContactWay.AIMER.name
            }
            contact_information_rl2 -> {
                contact_information_iv1!!.setImageResource(R.drawable.ring_gray)
                contact_information_iv2!!.setImageResource(R.drawable.ring_blue)
                contact_information_iv3!!.setImageResource(R.drawable.ring_gray)
                contact_information_iv4!!.setImageResource(R.drawable.ring_gray)
                model!!.contactWay = ContactWay.EMAIL.name
            }
            contact_information_rl3 -> {
                contact_information_iv1!!.setImageResource(R.drawable.ring_gray)
                contact_information_iv2!!.setImageResource(R.drawable.ring_gray)
                contact_information_iv3!!.setImageResource(R.drawable.ring_blue)
                contact_information_iv4!!.setImageResource(R.drawable.ring_gray)
                model!!.contactWay = ContactWay.PHONE.name
            }
            contact_information_rl4 -> {
                contact_information_iv1!!.setImageResource(R.drawable.ring_gray)
                contact_information_iv2!!.setImageResource(R.drawable.ring_gray)
                contact_information_iv3!!.setImageResource(R.drawable.ring_gray)
                contact_information_iv4!!.setImageResource(R.drawable.ring_blue)
                model!!.contactWay = ContactWay.ANYWAY.name
            }
            easy_to_contact_time_rl1 -> {
                easy_to_contact_time_iv1!!.setImageResource(R.drawable.ring_blue)
                easy_to_contact_time_iv2!!.setImageResource(R.drawable.ring_gray)
                easy_to_contact_time_iv3!!.setImageResource(R.drawable.ring_gray)
                model!!.contactTime = ContactTime.ANYTIME.name
            }
            easy_to_contact_time_rl2 -> {
                easy_to_contact_time_iv1!!.setImageResource(R.drawable.ring_gray)
                easy_to_contact_time_iv2!!.setImageResource(R.drawable.ring_blue)
                easy_to_contact_time_iv3!!.setImageResource(R.drawable.ring_gray)
                model!!.contactTime = ContactTime.CONVENIENTTIME.name
            }
            easy_to_contact_time_rl3 -> {
                easy_to_contact_time_iv1!!.setImageResource(R.drawable.ring_gray)
                easy_to_contact_time_iv2!!.setImageResource(R.drawable.ring_gray)
                easy_to_contact_time_iv3!!.setImageResource(R.drawable.ring_blue)
                model!!.contactTime = ContactTime.CUSTOM.name
            }
            shielded_business_rl1 -> {
                shielded_business_iv1!!.setImageResource(R.drawable.ring_blue)
                shielded_business_iv2!!.setImageResource(R.drawable.ring_gray)
                recyclerview!!.visibility = View.GONE
                tvAddPeopleList!!.visibility = View.GONE
                model!!.shielding.clear()
            }
            shielded_business_rl2 -> {
                shielded_business_iv1!!.setImageResource(R.drawable.ring_gray)
                shielded_business_iv2!!.setImageResource(R.drawable.ring_blue)
                recyclerview!!.visibility = View.VISIBLE
                tvAddPeopleList!!.visibility = View.VISIBLE
            }
            business_file_show_part_rl1 -> {
                if (model!!.commercialRecord.contains(ShowPart.RESUME.name)) {//个人概述
                    model!!.commercialRecord.remove(ShowPart.RESUME.name)
                    business_file_show_part_iv1!!.setImageResource(R.drawable.ring_gray)
                } else {
                    model!!.commercialRecord.add(ShowPart.RESUME.name)
                    business_file_show_part_iv1!!.setImageResource(R.drawable.ring_blue)
                }
            }
            business_file_show_part_rl2 -> {
                if (model!!.commercialRecord.contains(ShowPart.LINGUISTIC_COMPETENCE.name)) {//语言能力
                    model!!.commercialRecord.remove(ShowPart.LINGUISTIC_COMPETENCE.name)
                    business_file_show_part_iv2!!.setImageResource(R.drawable.ring_gray)
                } else {
                    model!!.commercialRecord.add(ShowPart.LINGUISTIC_COMPETENCE.name)
                    business_file_show_part_iv2!!.setImageResource(R.drawable.ring_blue)
                }
            }
            business_file_show_part_rl3 -> {
                if (model!!.commercialRecord.contains(ShowPart.INDUSTRY.name)) {//聚焦领域
                    model!!.commercialRecord.remove(ShowPart.INDUSTRY.name)
                    business_file_show_part_iv3!!.setImageResource(R.drawable.ring_gray)
                } else {
                    model!!.commercialRecord.add(ShowPart.INDUSTRY.name)
                    business_file_show_part_iv3!!.setImageResource(R.drawable.ring_blue)
                }
            }
            business_file_show_part_rl4 -> {
                if (model!!.commercialRecord.contains(ShowPart.ACHIEVEMENTS.name)) {//业绩成就
                    model!!.commercialRecord.remove(ShowPart.ACHIEVEMENTS.name)
                    business_file_show_part_iv4!!.setImageResource(R.drawable.ring_gray)
                } else {
                    model!!.commercialRecord.add(ShowPart.ACHIEVEMENTS.name)
                    business_file_show_part_iv4!!.setImageResource(R.drawable.ring_blue)
                }
            }
            business_file_show_part_rl5 -> {
                if (model!!.commercialRecord.contains(ShowPart.TRANSACTION.name)) {//历史交易
                    model!!.commercialRecord.remove(ShowPart.TRANSACTION.name)
                    business_file_show_part_iv5!!.setImageResource(R.drawable.ring_gray)
                } else {
                    model!!.commercialRecord.add(ShowPart.TRANSACTION.name)
                    business_file_show_part_iv5!!.setImageResource(R.drawable.ring_blue)
                }
            }
            business_file_show_part_rl6 -> {
                if (model!!.commercialRecord.contains(ShowPart.COMMERCIAL_ACTIVITY.name)) {//商务活动
                    model!!.commercialRecord.remove(ShowPart.COMMERCIAL_ACTIVITY.name)
                    business_file_show_part_iv6!!.setImageResource(R.drawable.ring_gray)
                } else {
                    model!!.commercialRecord.add(ShowPart.COMMERCIAL_ACTIVITY.name)
                    business_file_show_part_iv6!!.setImageResource(R.drawable.ring_blue)
                }
            }
            business_file_show_part_rl7 -> {
                if (model!!.commercialRecord.contains(ShowPart.EDUCATION_EXPERIENCE.name)) {//教育经历
                    model!!.commercialRecord.remove(ShowPart.EDUCATION_EXPERIENCE.name)
                    business_file_show_part_iv7!!.setImageResource(R.drawable.ring_gray)
                } else {
                    model!!.commercialRecord.add(ShowPart.EDUCATION_EXPERIENCE.name)
                    business_file_show_part_iv7!!.setImageResource(R.drawable.ring_blue)
                }
            }
            business_file_show_part_rl8 -> {
                if (model!!.commercialRecord.contains(ShowPart.OVERHEAD_INFORMATION.name)) {//附加信息
                    model!!.commercialRecord.remove(ShowPart.OVERHEAD_INFORMATION.name)
                    business_file_show_part_iv8!!.setImageResource(R.drawable.ring_gray)
                } else {
                    model!!.commercialRecord.add(ShowPart.OVERHEAD_INFORMATION.name)
                    business_file_show_part_iv8!!.setImageResource(R.drawable.ring_blue)
                }
            }
            tvAddPeopleList -> {
                val intent = Intent(activity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.TITLE), getString(R.string.i_do_not_want_this_guy_match_me))
                startActivityForResult(intent, 1000)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == 1001) {
            adapter!!.addPeople(data!!.getStringExtra(getString(R.string.DATA)))
        }
    }
}