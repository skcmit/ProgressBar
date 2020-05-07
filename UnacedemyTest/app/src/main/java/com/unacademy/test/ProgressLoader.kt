package com.unacademy.test

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.unacademy.test.Utils.convertDpToPixelConvert
import kotlin.math.cos
import kotlin.math.sin

class ProgressLoader : View {

    private lateinit var progressPaint: Paint
    private lateinit var progressBackgroundPaint: Paint
    private lateinit var dotPaint: Paint
    private var sweepAngle = 0
    private lateinit var circleBounds: RectF
    private var radius = 0f

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        initializePaints()
        circleBounds = RectF()
    }

    private fun initializePaints() {
        val progressStrokeWidth =
            convertDpToPixelConvert(context, DEFAULT_STROKE_WIDTH_DP.toFloat())

        progressPaint = Paint()
        progressPaint.strokeCap = Paint.Cap.ROUND
        progressPaint.strokeWidth = progressStrokeWidth.toFloat()
        progressPaint.style = Paint.Style.STROKE
        progressPaint.color = ContextCompat.getColor(context, R.color.progress_color)
        progressPaint.isAntiAlias = true

        progressBackgroundPaint = Paint()
        progressBackgroundPaint.style = Paint.Style.STROKE
        progressBackgroundPaint.strokeWidth = progressStrokeWidth.toFloat()
        progressBackgroundPaint.color =
            ContextCompat.getColor(context, R.color.background_progress_color)
        progressBackgroundPaint.isAntiAlias = true

        dotPaint = Paint()
        dotPaint.strokeCap = Paint.Cap.ROUND
        dotPaint.strokeWidth = progressStrokeWidth.toFloat()
        dotPaint.style = Paint.Style.FILL_AND_STROKE
        dotPaint.color = ContextCompat.getColor(context, R.color.dot_color)
        dotPaint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        calculateBounds(w, h)
    }

    private fun calculateBounds(w: Int, h: Int) {
        radius = w / 2f
        val dotWidth = dotPaint.strokeWidth
        val progressWidth = progressPaint.strokeWidth
        val progressBackgroundWidth = progressBackgroundPaint.strokeWidth
        val strokeSizeOffset =
            dotWidth.coerceAtLeast(progressWidth.coerceAtLeast(progressBackgroundWidth))
        val halfOffset = strokeSizeOffset / 2f

        circleBounds.left = halfOffset
        circleBounds.top = halfOffset
        circleBounds.right = w - halfOffset
        circleBounds.bottom = h - halfOffset
        radius = circleBounds.width() / 2f
    }

    override fun onDraw(canvas: Canvas) {
        drawProgressBackground(canvas)
        drawProgress(canvas)
        drawDot(canvas)
    }

    private fun drawProgressBackground(canvas: Canvas) {
        canvas.drawArc(
            circleBounds,
            ANGLE_START_PROGRESS_BACKGROUND.toFloat(),
            ANGLE_END_PROGRESS_BACKGROUND.toFloat(),
            false,
            progressBackgroundPaint
        )
    }

    private fun drawProgress(canvas: Canvas) {
        canvas.drawArc(
            circleBounds,
            START_ANGLE.toFloat(),
            sweepAngle.toFloat(),
            false,
            progressPaint
        )
    }

    private fun drawDot(canvas: Canvas) {
        val angleRadians =
            Math.toRadians(START_ANGLE + sweepAngle + 180.toDouble())
        val cos = cos(angleRadians).toFloat()
        val sin = sin(angleRadians).toFloat()
        val x = circleBounds.centerX() - radius * cos
        val y = circleBounds.centerY() - radius * sin
        canvas.drawPoint(x, y, dotPaint)
    }

    fun setProgress(current: Int) {
        sweepAngle = (current / 100f * 360.toDouble()).toInt()
        invalidate()
    }

    companion object {
        private const val START_ANGLE = 270
        private const val ANGLE_START_PROGRESS_BACKGROUND = 0
        private const val ANGLE_END_PROGRESS_BACKGROUND = 360
        private const val DEFAULT_STROKE_WIDTH_DP = 8
    }
}