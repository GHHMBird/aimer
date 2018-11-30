package com.buyint.mergerbot.Utility

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * created by licheng  on date 2018/8/16
 */
object FilePathUtils {

    //    当SD卡存在或者SD卡不可被移除的时候
    fun getCachePath(applicationContext: Context): String {
        val cacheDir: File?
        if (android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED || !Environment.isExternalStorageRemovable())
            cacheDir = applicationContext.externalCacheDir
        else
            cacheDir = applicationContext.cacheDir
        if (!cacheDir!!.exists())
            cacheDir.mkdirs()
        return cacheDir.absolutePath
    }

}
