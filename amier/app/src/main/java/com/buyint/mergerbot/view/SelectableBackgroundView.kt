package com.buyint.mergerbot.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.buyint.mergerbot.R
import java.util.*

class SelectableBackgroundView : FrameLayout, View.OnClickListener {

    override fun onClick(v: View?) {
        selectBackground()
    }


    //从内向外的动画viewgroup
    private var selectedColor: Drawable? = null
    private var size: Float = 0.toFloat()
    private var rootWidth: Int = 0
    private var rootHeight: Int = 0
    private var delta: Int = 0
    private var backgroundView: View? = null
    private var isInitialized = false
    private var isCheck = false
    private var first = true
    private var animatorSet: AnimatorSet? = null
    private var normalColor: Drawable? = null
    private var viewForTint: HashSet<View>? = null
    private var defTintColor: Int = 0
    private var selTintColor: Int = 0
    private var duration: Int = 0
    private var listener: SelectableBackgroundViewListener? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SelectableBackgroundView, defStyle, 0)
        selectedColor = a.getDrawable(R.styleable.SelectableBackgroundView_sb_selectedColor)
        normalColor = a.getDrawable(R.styleable.SelectableBackgroundView_sb_normalColor)
        size = a.getDimension(R.styleable.SelectableBackgroundView_sb_size, 0f)
        defTintColor = a.getColor(R.styleable.SelectableBackgroundView_sb_normalTint, resources.getColor(R.color.colorBlack))
        selTintColor = a.getColor(R.styleable.SelectableBackgroundView_sb_selectedTint, resources.getColor(R.color.white))
        duration = a.getInt(R.styleable.SelectableBackgroundView_sb_duration, 250)
        isCheck = a.getBoolean(R.styleable.SelectableBackgroundView_sb_isCheck, false)

        delta = size.toInt() / 2
        a.recycle()

        backgroundView = View(context)
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        val params2 = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        params.width = 0
        params.height = 0
        params.gravity = Gravity.CENTER
        backgroundView?.layoutParams = params
        params2.gravity = Gravity.CENTER
        addView(backgroundView, params2)
        setOnClickListener(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rootWidth = width
        rootHeight = height
        normalColor?.setBounds(0, delta, rootWidth, rootHeight - delta)
        normalColor?.draw(canvas)
        if (!isInitialized) {
            isInitialized = true
            viewForTint = findViewForTint(this)
            deselectBackground()
        }
        if (isCheck && first) {
            selectBackground()
            first = false
        }
    }

    fun selectBackground() {
        backgroundView?.background = selectedColor
        val heightAnimation = ValueAnimator.ofInt(0, rootHeight)
        heightAnimation.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = backgroundView?.layoutParams
            layoutParams?.height = value
            backgroundView?.layoutParams = layoutParams
        }

        val widthAnimation = ValueAnimator.ofInt(0, rootWidth)
        widthAnimation.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = backgroundView?.layoutParams
            layoutParams?.width = value
            backgroundView?.layoutParams = layoutParams
        }

        animatorSet = AnimatorSet()
        animatorSet?.duration = duration.toLong()
        animatorSet?.playTogether(heightAnimation, widthAnimation)
        animatorSet?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                changeTint(selTintColor)
                if (!isCheck) {
                    listener?.finished()
                }
                if (isCheck) {
                    isCheck = false
                }
            }
        })
        animatorSet?.start()
    }

    private fun changeTint(color: Int) {
        val items = viewForTint
        items ?: return
        for (view in items) {
            if (view is TextView) {
                view.setTextColor(color)
            } else if (view is ImageView) {
                view.imageTintList = ColorStateList.valueOf(color)
            }
        }
    }

    private fun findViewForTint(viewGroup: ViewGroup): HashSet<View> {
        val array = HashSet<View>()

        val count = viewGroup.childCount
        for (i in 0 until count) {
            val view = viewGroup.getChildAt(i)
            when (view) {
                is ViewGroup -> array.addAll(findViewForTint(view))
                is TextView -> array.add(view)
                is ImageView -> array.add(view)
            }
        }

        return array

    }

    fun deselectBackground() {
        if (animatorSet != null) {
            animatorSet?.cancel()
        }
        val layoutParams = backgroundView?.layoutParams
        layoutParams?.width = 0
        layoutParams?.height = 0
        backgroundView?.layoutParams = layoutParams
        changeTint(defTintColor)
    }

    fun choose() {
        first = true
        isCheck = true
        invalidate()
    }

    companion object {
        val TAG = SelectableBackgroundView::class.java.simpleName!!
    }

    interface SelectableBackgroundViewListener {
        fun finished()
    }

    fun setListener(listener: SelectableBackgroundViewListener) {
        this.listener = listener
    }
}