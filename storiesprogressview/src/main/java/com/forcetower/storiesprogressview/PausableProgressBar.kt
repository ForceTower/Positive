package com.forcetower.storiesprogressview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.ScaleAnimation
import android.view.animation.Transformation
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat

class PausableProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_PROGRESS_DURATION = 2000L
    }

    var duration = DEFAULT_PROGRESS_DURATION
    var callback: Callback? = null
    private var animation: PausableScaleAnimation? = null

    private val frontProgressView: View
    private val maxProgressView: View
    private val backProgress: View

    init {
        // Inflation
        LayoutInflater.from(context).inflate(R.layout.pausable_progress, this)

        // Default colors
        val defBackground = ContextCompat.getColor(context, R.color.progress_secondary)
        val defForeground = ContextCompat.getColor(context, R.color.progress_primary)
        val defMaxColor = ContextCompat.getColor(context, R.color.white)
        val defHeight = resources.getDimension(R.dimen.progress_bar_height)

        // Params extraction
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PausableProgressBar)
        val background = typedArray.getColor(R.styleable.PausableProgressBar_background, defBackground)
        val foreground = typedArray.getColor(R.styleable.PausableProgressBar_foreground, defForeground)
        val maxColor = typedArray.getColor(R.styleable.PausableProgressBar_maxColor, defMaxColor)
        val height = typedArray.getDimension(R.styleable.PausableProgressBar_height, defHeight).toInt()
        typedArray.recycle()

        // View referencing
        frontProgressView = findViewById(R.id.front_progress)
        maxProgressView = findViewById(R.id.max_progress)
        backProgress = findViewById(R.id.back_progress)

        // Coloring and sizing
        backProgress.apply {
            setBackgroundColor(background)
            layoutParams.height = height
        }
        frontProgressView.apply {
            setBackgroundColor(foreground)
            layoutParams.height = height
        }
        maxProgressView.apply {
            setBackgroundColor(maxColor)
            layoutParams.height = height
        }
    }

    fun setMax() {
        finishProgress(true)
    }

    fun setMin() {
        finishProgress(false)
    }

    fun setMinWithoutCallback() {
        maxProgressView.setBackgroundResource(R.color.progress_secondary)
        maxProgressView.visibility = View.VISIBLE
        animation?.setAnimationListener(null)
        animation?.cancel()
    }

    fun setMaxWithoutCallback() {
        maxProgressView.setBackgroundResource(R.color.progress_max_active)
        maxProgressView.visibility = View.VISIBLE
        animation?.setAnimationListener(null)
        animation?.cancel()
    }

    private fun finishProgress(isMax: Boolean) {
        if (isMax) maxProgressView.setBackgroundResource(R.color.progress_max_active)
        maxProgressView.visibility = if (isMax) View.VISIBLE else View.GONE
        animation?.setAnimationListener(null)
        animation?.cancel()
        callback?.onFinishProgress()
    }

    fun startProgress() {
        maxProgressView.visibility = View.GONE

        animation = PausableScaleAnimation(0f, 1f, 1f, 1f, Animation.ABSOLUTE, 0f, Animation.RELATIVE_TO_SELF, 0f)
        animation?.duration = duration
        animation?.interpolator = LinearInterpolator()
        animation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                frontProgressView.visibility = View.VISIBLE
                callback?.onStartProgress()
            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                callback?.onFinishProgress()
            }
        })
        animation?.fillAfter = true
        frontProgressView.startAnimation(animation)
    }

    fun pauseProgress() {
        animation?.pause()
    }

    fun resumeProgress() {
        animation?.resume()
    }

    fun clear() {
        animation?.setAnimationListener(null)
        animation?.cancel()
        animation = null
    }


    inner class PausableScaleAnimation(
        fromX: Float,
        toX: Float,
        fromY: Float,
        toY: Float,
        pivotXType: Int,
        pivotXValue: Float,
        pivotYType: Int,
        pivotYValue: Float
    ) : ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue) {
        private var mElapsedAtPause: Long = 0
        private var mPaused = false

        override fun getTransformation(currentTime: Long, outTransformation: Transformation?, scale: Float): Boolean {
            if (mPaused && mElapsedAtPause == 0L) {
                mElapsedAtPause = currentTime - startTime
            }
            if (mPaused) {
                startTime = currentTime - mElapsedAtPause
            }
            return super.getTransformation(currentTime, outTransformation, scale)
        }

        fun pause() {
            if (mPaused) return
            mElapsedAtPause = 0
            mPaused = true
        }

        fun resume() {
            mPaused = false
        }
    }

    interface Callback {
        fun onStartProgress()
        fun onFinishProgress()
    }
}