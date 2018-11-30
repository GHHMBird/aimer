package com.buyint.mergerbot.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.ImageView
import com.buyint.mergerbot.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * Created by huheming on 2018/9/11
 * 传入text，textcolor，textsize，把text设置成image的控件
 */
class TextImageView : ImageView {

    private var color: Int? = 0
    private var text: String? = null
    private var size: Float? = 0f
    private lateinit var paint: Paint
    private var changeTextLength = 0
    private var dis: Disposable? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    fun init(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TextImageView, defStyleAttr, 0)
        color = a.getColor(R.styleable.TextImageView_cftiv_color, Color.BLACK)
        text = a.getString(R.styleable.TextImageView_cftiv_text)
        size = a.getDimension(R.styleable.TextImageView_cftiv_text_size, 0f)
        a.recycle()

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color!!
        paint.isDither = true
        paint.isFilterBitmap = true
        paint.textSize = size!!
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.LEFT
        setColorFilter(color!!)

        Observable.interval(400, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Long> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
                dis = d
            }

            override fun onNext(t: Long) {
                if (changeTextLength >= text!!.length) {
                    changeTextLength = 0
                } else {
                    changeTextLength++
                }
                invalidate()
            }

            override fun onError(e: Throwable) {
            }
        })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST) {
            val rect = Rect()
            paint.getTextBounds(text, 0, text!!.length, rect)
            width = rect.width() + paddingLeft + paddingRight
        }

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        if (heightMode == MeasureSpec.AT_MOST) {
            val rect = Rect()
            paint.getTextBounds(text, 0, text!!.length, rect)
            height = rect.height() + paddingTop + paddingBottom
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val x = paddingLeft
        val y = height / 2 + (paint.fontMetricsInt.bottom - paint.fontMetricsInt.top) / 2 - paint.fontMetricsInt.bottom

        val transPaint = Paint(paint)
        transPaint.alpha = 100

        canvas!!.drawText(text, 0, text!!.length, x.toFloat(), y.toFloat(), transPaint)
        canvas.drawText(text, 0, changeTextLength, x.toFloat(), y.toFloat(), paint)
    }

    fun destory() {
        dis?.dispose()
    }
}