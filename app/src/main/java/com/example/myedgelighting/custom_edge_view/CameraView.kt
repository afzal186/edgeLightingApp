package com.example.myedgelighting.custom_edge_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class CameraView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val path = Path()

    init {
        paint.color = 0xFF000000.toInt() // Black color for the line segment
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 35f // Adjust the stroke width as needed
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            val screenWidth = width.toFloat()
            val screenHeight = height.toFloat()
            val loopHeight = screenHeight * 0.1f // Adjust the loop's height as needed
            val loopWidth = screenWidth * 0.4f // Adjust the loop's width as needed

            // Calculate the coordinates of the two points where the line should be cropped
            val startX = screenWidth / 2 - loopWidth / 2
            val endX = screenWidth / 2 + loopWidth / 2
            val y = loopHeight

            // Draw the left curve of the "U"
            path.arcTo(startX, 0f, startX + loopWidth, 2 * loopHeight, 180f, 180f, false)

            // Draw the horizontal line of the "U"
            path.lineTo(endX, y)

            // Draw the right curve of the "U"
            path.arcTo(endX - loopWidth, 0f, endX, 2 * loopHeight, 0f, 180f, false)

            // Crop the line segment between the two points
            val startXCrop = startX + loopWidth / 4 // Adjust the cropping start point as needed
            val endXCrop = endX - loopWidth / 4 // Adjust the cropping end point as needed
            path.lineTo(endXCrop, y)

            // Draw the cropped line segment
            drawPath(path, paint)
        }
    }
}