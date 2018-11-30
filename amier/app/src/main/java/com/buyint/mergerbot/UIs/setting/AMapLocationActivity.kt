package com.buyint.mergerbot.UIs.setting

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.TextUtils
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.*
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import kotlinx.android.synthetic.main.activity_amap_location.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/16
 */
class AMapLocationActivity : BaseActivity(), AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener {

    //坐标转位置
    override fun onRegeocodeSearched(result: RegeocodeResult?, code: Int) {
        if (code == 1000) {
            activity_amap_location_place.text = SpannableStringBuilder(result?.regeocodeAddress?.formatAddress)
        }
    }

    //位置转坐标
    override fun onGeocodeSearched(result: GeocodeResult?, code: Int) {
    }

    override fun onCameraChangeFinish(p0: CameraPosition?) {
        val target = p0!!.target
        if (geocoderSearch == null) {
            geocoderSearch = GeocodeSearch(this)
            geocoderSearch!!.setOnGeocodeSearchListener(this)
        }
        geocoderSearch!!.getFromLocationAsyn(RegeocodeQuery(LatLonPoint(target.latitude, target.longitude), 10F, GeocodeSearch.AMAP))
    }

    override fun onCameraChange(p0: CameraPosition?) {
    }

    private var geocoderSearch: GeocodeSearch? = null
    private var aMap: AMap? = null
    private var locationClient: AMapLocationClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amap_location)

        toorbar_title.text = getString(R.string.work_place)
        toolbar_back.setOnClickListener { onBackPressed() }
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            if (!TextUtils.isEmpty(activity_amap_location_place.text.toString().trim())) {
                var intent = Intent()
                intent.putExtra(getString(R.string.DATA), activity_amap_location_place.text.toString().trim())
                setResult(1001, intent)
                finish()
            }
        }

        //地图初始化
        activity_amap_location_map.onCreate(savedInstanceState)
        //地图图层获取
        aMap = activity_amap_location_map.map

        //定位初始化
        locationClient = AMapLocationClient(applicationContext)
        locationClient!!.setLocationListener({
            //获取定位结果返回
            if (it != null) {
                if (it.errorCode == 0) {
                    //定位成功

                    activity_amap_location_place.text = SpannableStringBuilder(it.address)
                    aMapGotoLocation(it.latitude, it.longitude)

                    locationClient!!.stopLocation()
                    locationClient!!.onDestroy()

                } else {
                    //定位失败
                    locationClient!!.stopLocation()
                    locationClient!!.onDestroy()
                }
            }
        })

        var mLocationOption = AMapLocationClientOption()
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption.isOnceLocation = true
        mLocationOption.isOnceLocationLatest = true
        mLocationOption.isNeedAddress = true

        //启动定位
        locationClient!!.setLocationOption(mLocationOption)
        locationClient!!.startLocation()

        aMap!!.setOnCameraChangeListener(this)

    }

    fun aMapGotoLocation(latitude: Double, longitude: Double) {
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(CameraPosition(LatLng(latitude, longitude), 18f, 30f, 0f))
        aMap!!.animateCamera(cameraUpdate)
    }

    override fun onDestroy() {
        super.onDestroy()
        activity_amap_location_map.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        activity_amap_location_map.onResume()
    }

    override fun onPause() {
        super.onPause()
        activity_amap_location_map.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        activity_amap_location_map.onSaveInstanceState(outState)
    }
}