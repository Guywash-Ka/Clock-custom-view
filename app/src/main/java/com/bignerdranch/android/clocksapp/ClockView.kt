package com.bignerdranch.android.clocksapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*


class ClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {
    private var paint: Paint? = Paint()
    private lateinit var bitmap: Bitmap
    private lateinit var calendar: Calendar
    private var angle = 0f
    private var matr = Matrix()


    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.BLACK)
        drawClocks(canvas)
        drawHands(canvas)
        postInvalidateDelayed(100)
        invalidate()
    }

    private fun drawHand(canvas: Canvas, time: Float, type: Int) {
        angle = (Math.PI * time / 30 - Math.PI / 2).toFloat()
        bitmap = when (type) {
            Calendar.SECOND -> BitmapFactory.decodeResource(resources, R.drawable.second_hand)
            Calendar.MINUTE -> BitmapFactory.decodeResource(resources, R.drawable.minute_hand)
            else -> BitmapFactory.decodeResource(resources, R.drawable.hour_hand)
        }

        bitmap = Bitmap.createScaledBitmap(bitmap, width, width, false)
        bitmap = rotateBitmap(bitmap, angle * 57.29f + 90)
        bitmap = Bitmap.createScaledBitmap(bitmap, width, width, false)
        canvas.drawBitmap(bitmap, (0).toFloat(), (0).toFloat(), paint)

    }

    private fun drawHands(canvas: Canvas) {
        calendar = Calendar.getInstance()
        drawHand(canvas, (calendar[Calendar.HOUR] % 12).toFloat(), Calendar.HOUR)
        drawHand(canvas, calendar[Calendar.MINUTE].toFloat(), Calendar.MINUTE)
        drawHand(canvas, calendar[Calendar.SECOND].toFloat(), Calendar.SECOND)
    }

    private fun drawClocks(canvas: Canvas) {
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.clocks)
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)

        canvas.drawBitmap(bitmap, 0f, 0f, paint)
    }

    private fun rotateBitmap(source: Bitmap, degrees: Float): Bitmap {
        matr = Matrix()
        matr.setRotate(degrees, (width/2).toFloat(), (width/2).toFloat())
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.width, matr, true
        )
    }
}