package com.example.draganddraw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import kotlin.math.max
import kotlin.math.min

class BoxDrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var currentBox: Box? = null
    private var boxen = arrayListOf<Box>()
    private var boxPaint = Paint().apply { color = 0x22ff0000 }
    private var backgroundPaint = Paint().apply { color = 0xfff8efe }

    constructor(context: Context) : this(context, null)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        val action = when (event.actionMasked) {
            ACTION_DOWN -> {
                currentBox = Box(current)
                boxen.add(currentBox!!)
                ACTION_DOWN.toString()
            }
            ACTION_MOVE -> {
                currentBox?.let { it.current = current }
                invalidate()
                ACTION_MOVE.toString()
            }
            ACTION_UP -> {
                currentBox = null
                ACTION_UP.toString()
            }
            else -> {
                currentBox = null
                ACTION_CANCEL.toString()
            }
        }
        Log.i(LOG_TAG, "$action at x=${current.x}, y=${current.y}")
        return true
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
        for (box in boxen) {
            val left = min(box.origin.x, box.current.x)
            val right = max(box.origin.x, box.current.x)
            val top = min(box.origin.y, box.current.y)
            val bottom = max(box.origin.y, box.current.y)
            canvas.drawRect(left, top, right, bottom, boxPaint)
        }
    }

    companion object {
        private val LOG_TAG = BoxDrawingView::class.java.simpleName
    }

    private inner class Box(val origin: PointF) {
        lateinit var current: PointF
    }
}
