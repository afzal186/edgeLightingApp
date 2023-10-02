package com.example.myedgelighting.custom_edge_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class LoopInRectangleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val notchPath: Path = Path()
    private val linePath: Path = Path()

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 50f
    }

    fun calculateAngles() {
        val angles = arrayOf(0, 30, 45, 60, 90, 180, 360)
        val averageAngles = Random.nextInt(20) + 40
        val FULL_ROTATION = 360
        while (angles.sum() < FULL_ROTATION - averageAngles) {

        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = canvas.width.toFloat()
        val height = canvas.height.toFloat()

        // Calculate the rectangle dimensions
        val rectLeft = 0f
        val rectTop = 0f
        val rectRight = width
        val rectBottom = height

        // Calculate the notch dimensions (position and size)
        val notchWidth = width * 0.4f // Adjust the notch width as needed
        val notchHeight = height * 0.1f // Adjust the notch height as needed
        val notchX = (width - notchWidth) / 2
        val notchY = 0f

        // Create a path for the waterdrop-like U shape notch in the top center
        notchPath.reset()
        notchPath.moveTo(notchX, notchY)
        notchPath.lineTo(notchX, notchY + notchHeight)
        notchPath.quadTo(
            notchX + notchWidth / 2,
            notchY + 2 * notchHeight,
            notchX + notchWidth,
            notchY + notchHeight
        )
        notchPath.lineTo(notchX + notchWidth, notchY)
        notchPath.close()

        // Calculate the line dimensions
        val lineX1 = 0f
        val lineY1 = notchHeight // Adjust the Y-coordinate for the line start
        val lineX2 = width
        val lineY2 = notchHeight // Adjust the Y-coordinate for the line end

        // Create a path for the line below the notch
        linePath.reset()
        linePath.moveTo(lineX1, lineY1)
        linePath.lineTo(lineX2, lineY2)

        // Draw the rectangle
        canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, paint)

        // Clip the canvas to draw the line only below the notch
        canvas.clipPath(notchPath, Region.Op.DIFFERENCE)

        // Draw the line below the notch
        canvas.drawPath(linePath, paint)
    }
}