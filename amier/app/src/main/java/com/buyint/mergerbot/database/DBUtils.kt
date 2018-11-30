package com.buyint.mergerbot.database

import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.main.adapter.QM3QABean
import com.buyint.mergerbot.dto.LoginResponse
import com.buyint.mergerbot.dto.SourceTargetGetPeopleRelationship
import com.snappydb.DB
import com.snappydb.DBFactory
import com.snappydb.SnappydbException

/**
 * Created by huheming on 2018/7/17
 */
fun dbGetLong(context: Context, key: String): Long {
    var db: DB? = null
    return try {
        db = DBFactory.open(context)
        val long = db!!.getLong(key)
        long
    } catch (e: SnappydbException) {
        e.printStackTrace()
        0
    } finally {
        db?.close()
    }
}

fun putLong(context: Context, key: String, long: Long) {
    var db: DB? = null
    try {
        db = DBFactory.open(context)
        db!!.putLong(key, long)
    } catch (e: SnappydbException) {
        e.printStackTrace()
    } finally {
        db?.close()
    }
}

fun putQM3QABeans(context: Context, key: String, arr: Array<QM3QABean>) {
    var db: DB? = null
    try {
        db = DBFactory.open(context)
        db!!.put(key, arr)
    } catch (e: SnappydbException) {
        e.printStackTrace()
    } finally {
        db?.close()
    }
}

fun getQM3QABeans(context: Context, key: String): Array<QM3QABean>? {
    var db: DB? = null
    return try {
        db = DBFactory.open(context)
        val beans = db!!.getObjectArray(key, QM3QABean::class.java)
        beans
    } catch (e: SnappydbException) {
        e.printStackTrace()
        null
    } finally {
        db?.close()
    }
}

fun saveLoginResponse(context: Context, response: LoginResponse) {
    var db: DB? = null
    try {
        db = DBFactory.open(context)
        db!!.put(context.getString(R.string.buyint) + LoginResponse::class.simpleName, response)
    } catch (e: SnappydbException) {
        e.printStackTrace()
    } finally {
        db?.close()
    }
}

fun saveBean(context: Context, response: SourceTargetGetPeopleRelationship) {
    var db: DB? = null
    try {
        db = DBFactory.open(context)
        db!!.put(context.getString(R.string.buyint) + SourceTargetGetPeopleRelationship::class.simpleName, response)
    } catch (e: SnappydbException) {
        e.printStackTrace()
    } finally {
        db?.close()
    }
}

fun getLoginResponse(context: Context): LoginResponse? {
    var db: DB? = null
    return try {
        db = DBFactory.open(context)
        val bean = db!!.getObject(context.getString(R.string.buyint) + LoginResponse::class.simpleName, LoginResponse::class.java)
        bean
    } catch (e: SnappydbException) {
        e.printStackTrace()
        null
    } finally {
        db?.close()
    }
}

fun getBean(context: Context): SourceTargetGetPeopleRelationship? {
    var db: DB? = null
    return try {
        db = DBFactory.open(context)
        val bean = db!!.getObject(context.getString(R.string.buyint) + SourceTargetGetPeopleRelationship::class.simpleName, SourceTargetGetPeopleRelationship::class.java)
        bean
    } catch (e: SnappydbException) {
        e.printStackTrace()
        null
    } finally {
        db?.close()
    }
}

fun <T> getObject(context: Context): T? {
    var db: DB? = null
    return try {
        db = DBFactory.open(context)
        val bean = db!!.getObject(context.getString(R.string.buyint) + T::class.simpleName, T::class.java) as T
        bean
    } catch (e: SnappydbException) {
        e.printStackTrace()
        null
    } finally {
        db?.close()
    }
}


fun <T> saveObject(context: Context, response: T) {
    var db: DB? = null
    try {
        db = DBFactory.open(context)
        db!!.put(context.getString(R.string.buyint) + T::class.simpleName, response)
    } catch (e: SnappydbException) {
        e.printStackTrace()
    } finally {
        db?.close()
    }
}

fun clearLoginResponse(context: Context) {
    var db: DB? = null
    try {
        db = DBFactory.open(context)
        db!!.put(context.getString(R.string.buyint) + LoginResponse::class.simpleName, LoginResponse())
    } catch (e: SnappydbException) {
        e.printStackTrace()
    } finally {
        db?.close()
    }
}