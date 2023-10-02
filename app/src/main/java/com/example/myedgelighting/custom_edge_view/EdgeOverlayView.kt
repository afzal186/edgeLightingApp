package com.example.myedgelighting.custom_edge_view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.RED
import android.graphics.ComposeShader
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View
import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.CommonKeys.DISPLAY_MODE_KEY
import com.example.myedgelighting.utils.CommonKeys.DISPLAY_NOTCH_KEY
import com.example.myedgelighting.utils.MySharedPrefs


@SuppressLint("SuspiciousIndentation")
class EdgeOverlayView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var strokeWidth: Float = 13f
    private var currentColor: Int = Color.WHITE
    private var cornerRadius: Float = 0f
    private var rectF: RectF? = null
    private var animator: ValueAnimator? = null
    private var displayMode: Int = 0
    private var notchMode: Int = 8
    private var customString: String = "*"
    private var symbolTextSize: Float = 20f
    private var symbolGapSize: Float = 10f // You can adjust the initial gap size as needed
    private var startColorSingle: Int = Color.RED
    private var endColorSingle: Int = Color.RED
    private var animationSpeed: Float = 1.0f // Default animation speed
    private var currentHue: Float = 100f
    private var notchWidthRatio = 0.8f // Adjust the ratio for the width of the notch
    private var notchWidth = 0.8f // Adjust the ratio for the width of the notch
    private var notchHeight = 0.8f // Adjust the ratio for the width of the notch
    private val cameraStrokeWidth = 20f
    private val cameraCircleSize = 80f
    private val cameraPaddingFromTop = 50f // Adjust this value as needed
    private val cameraPaddingFromLeft = 90f // Adjust this value as needed
    private var gradientRotationAngle = 0f


    private fun drawRectangularRightShapeNotch(canvas: Canvas, currentColor: Int) {
        val notchWidth = 250
        val paddingFromLeft = 10
        val paddingFromTop = 0 // Adjust this value as needed
        val notchHeight = cameraCircleSize
        val cornerRadius = 10f // Adjust this value as needed

        //paint.style = Paint.Style.STROKE
        // paint.strokeWidth = 15f
        // paint.color = currentColor

        val notchLeft = rectF!!.left + paddingFromLeft
        val notchRight = notchLeft + notchWidth
        val notchTop =
            rectF!!.top + paddingFromTop + cornerRadius // Adjusted for top position and padding
        val notchBottom = notchTop + notchHeight

        val path = Path()
        path.moveTo(notchLeft + cornerRadius, notchTop)
        path.lineTo(notchRight - cornerRadius, notchTop)
        path.arcTo(
            RectF(
                notchRight - 2 * cornerRadius,
                notchTop,
                notchRight,
                notchTop + 2 * cornerRadius
            ),
            -90f,
            90f,
            false // Set to false to ensure proper arc drawing
        )
        path.lineTo(notchRight, notchBottom - cornerRadius)
        path.arcTo(
            RectF(
                notchRight - 2 * cornerRadius,
                notchBottom - 2 * cornerRadius,
                notchRight,
                notchBottom
            ),
            0f,
            90f,
            false // Set to false to ensure proper arc drawing
        )
        path.lineTo(notchLeft + cornerRadius, notchBottom)
        path.arcTo(
            RectF(
                notchLeft,
                notchBottom - 2 * cornerRadius,
                notchLeft + 2 * cornerRadius,
                notchBottom
            ),
            90f,
            90f,
            false // Set to false to ensure proper arc drawing
        )
        path.lineTo(notchLeft, notchTop + cornerRadius)
        path.arcTo(
            RectF(
                notchLeft,
                notchTop,
                notchLeft + 2 * cornerRadius,
                notchTop + 2 * cornerRadius
            ),
            180f,
            90f,
            false // Set to false to ensure proper arc drawing
        )
        path.close()

        canvas.drawPath(path, paint)
    }


    private fun drawWaterdropNotch(canvas: Canvas) {
        val notchWidth = rectF!!.width() * 0.95f

        // Draw the waterdrop-like V shape notch
        val path = Path()
        path.moveTo(rectF!!.left + notchWidth / 2/*v x*/, rectF!!.top/*v y*/)
        path.lineTo(
            rectF!!.left + rectF!!.width() / 2,
            rectF!!.top + (rectF!!.height() * 0.04f/*v bottom*/)
        )
        path.lineTo(rectF!!.right - notchWidth / 2, rectF!!.top)
        path.lineTo(rectF!!.left + notchWidth / 2, rectF!!.top)

        canvas.drawPath(path, paint)


    }


    private fun drawLeftSideCircleNotch(canvas: Canvas) {
        // Draw the circular camera shape
        val leftCircleCenterX =
            rectF!!.left + cameraPaddingFromLeft // Adjust this value for the left padding
        val leftCircleCenterY =
            rectF!!.top + cameraPaddingFromTop // Adjust this value for the top padding
        val leftCircleRadius =
            cameraCircleSize / 1.5f // Adjust this value for the size of the circle

        // paint.style = Paint.Style.STROKE
        // paint.strokeWidth = cameraStrokeWidth
        //  paint.color = currentColor
        currentColor = MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor, Color.RED)

        canvas.drawCircle(leftCircleCenterX, leftCircleCenterY, leftCircleRadius, paint)
    }


    private fun drawCylindricalShapeNotch(
        canvas: Canvas,
        currentColor: Int,
        notchWidth: Float,
        notchHeight: Float
    ) {
        var notchWidth = MySharedPrefs(context).getFloatPref(CommonKeys.NotchWidth, 100f)
//        notchWidth= MySharedPrefs(context).getFloatPref(CommonKeys.NOTCH_WIDTH,250f)
        val paddingFromTop = MySharedPrefs(context).getFloatPref(CommonKeys.NotchY, 30f)
        val notchHeight = MySharedPrefs(context).getFloatPref(CommonKeys.NotchHeight, 100f)
        val cornerRadius = MySharedPrefs(context).getFloatPref(
            CommonKeys.NotchCRadius,
            20f
        ) // Adjust this value as needed

        //   paint.style = Paint.Style.STROKE
        //  paint.strokeWidth = 15f
        //  paint.color = currentColor

        val notchLeft = MySharedPrefs(context).getFloatPref(
            CommonKeys.NotchX,
            rectF!!.left + rectF!!.width() / 2 - notchWidth / 2
        )
        val notchRight = notchLeft + notchWidth
        val notchTop = paddingFromTop
        val notchBottom = notchTop + notchHeight

        val path = Path()
        path.moveTo(notchLeft + cornerRadius, notchTop)
        path.lineTo(notchRight - cornerRadius, notchTop)
        path.arcTo(
            RectF(
                notchRight - 2 * cornerRadius,
                notchTop,
                notchRight,
                notchTop + 2 * cornerRadius
            ),
            -90f,
            90f,
            false // Set to false to ensure proper arc drawing
        )
        path.lineTo(notchRight, notchBottom - cornerRadius)
        path.arcTo(
            RectF(
                notchRight - 2 * cornerRadius,
                notchBottom - 2 * cornerRadius,
                notchRight,
                notchBottom
            ),
            0f,
            90f,
            false // Set to false to ensure proper arc drawing
        )
        path.lineTo(notchLeft + cornerRadius, notchBottom)
        path.arcTo(
            RectF(
                notchLeft,
                notchBottom - 2 * cornerRadius,
                notchLeft + 2 * cornerRadius,
                notchBottom
            ),
            90f,
            90f,
            false // Set to false to ensure proper arc drawing
        )
        path.lineTo(notchLeft, notchTop + cornerRadius)
        path.arcTo(
            RectF(
                notchLeft,
                notchTop,
                notchLeft + 2 * cornerRadius,
                notchTop + 2 * cornerRadius
            ),
            180f,
            90f,
            false // Set to false to ensure proper arc drawing
        )
        path.close()

        canvas.drawPath(path, paint)
    }

    private fun drawUShapeNotch(canvas: Canvas) {
        val centerX = rectF!!.left + rectF!!.width() / 2
        val centerY = rectF!!.top + cameraPaddingFromTop // Adjust this value for the padding
        val radius = cameraCircleSize / 1.5f // Adjust this value for the size of the camera circle

        currentColor = MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor, Color.RED)

        canvas.drawCircle(centerX, centerY, radius, paint)
    }


    init {
        val startColor = RED
        val endColor = RED
        paint.style = Paint.Style.STROKE
        strokeWidth = MySharedPrefs(context).getFloatPref(CommonKeys.STROKE_WIDTH, 13f)
        paint.strokeWidth = strokeWidth
        //getting display mode
        displayMode = MySharedPrefs(context).getIntPref(DISPLAY_MODE_KEY, 0)
        updateGradient(startColor, endColor, startColor, endColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        rectF?.let { rect ->
            //checking/getting display mode
            if (MySharedPrefs(context).getIntPref(DISPLAY_MODE_KEY, 0) == 0) {
                var selectedColorMode = 1
                var selectedColor: Int = Color.RED
                var selectedColor1: Int = Color.RED
                var selectedColor2: Int = Color.RED
                selectedColor =
                    MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor, selectedColor)
                selectedColor1 =
                    MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor1, selectedColor1)
                selectedColor2 =
                    MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor2, selectedColor2)


                //code to draw the stroke
                val centerX = rect.centerX()
                val centerY = rect.centerY()
                //checking color modes
                selectedColorMode =
                    MySharedPrefs(context).getIntPref(CommonKeys.SelectedColorMode, 1)
                val gradientColors = when (selectedColorMode) {
                    1 -> intArrayOf(selectedColor, selectedColor)
                    2 -> intArrayOf(selectedColor, selectedColor1)
                    3 -> intArrayOf(selectedColor, selectedColor1, selectedColor2)
                    else -> intArrayOf(
                        Color.RED,
                        Color.MAGENTA,
                        Color.YELLOW,
                        Color.GREEN,
                        Color.BLUE,
                        Color.CYAN,
                        Color.MAGENTA
                    )
                }
                //creating gradient from selected colors
                val gradient = SweepGradient(centerX, centerY, gradientColors, null)
                //lines for animations
                val rotationMatrix = Matrix()
                rotationMatrix.setRotate(gradientRotationAngle, centerX, centerY)
                gradient.setLocalMatrix(rotationMatrix)
                paint.shader = gradient

                val currentColor =
                    MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor, Color.RED)
                paint.color = currentColor
                paint.style = Paint.Style.STROKE

                gradientRotationAngle += MySharedPrefs(context).getFloatPref(
                    CommonKeys.ANIM_SPEED,
                    gradientRotationAngle
                ) // Adjust the rotation speed as needed

                if (gradientRotationAngle >= 360f) {
                    gradientRotationAngle -= 360f
                }

                // Draw the rounded rectangle with updated color
                /*   canvas.drawRoundRect(
                       rect,
                       MySharedPrefs(context).getFloatPref(CommonKeys.Corner_Radius, 20f),
                       MySharedPrefs(context).getFloatPref(CommonKeys.Corner_Radius, 20f),
                       paint
                   )*/

                // Apply hue shift to the current color
                val shiftedColor = shiftHue(currentColor, currentHue)
                paint.color = shiftedColor

                // Draw the rounded rectangle with the hue-shifted color

                canvas.drawRoundRect(
                    rect,
                    MySharedPrefs(context).getFloatPref(CommonKeys.Corner_Radius, 20f),
                    MySharedPrefs(context).getFloatPref(CommonKeys.Corner_Radius, 20f),
                    paint
                )
                // Update the hue for the next frame
                currentHue += 0.5f // Adjust the hue change rate as needed
                if (currentHue > 360f) {
                    currentHue -= 360f

                }

                when (MySharedPrefs(context).getIntPref(CommonKeys.notchType)) {

                    1 -> {
                        drawDefaultStroke(canvas, rect)
                    }

                    2 -> {

                        drawCylindricalShapeNotch(canvas, currentColor, notchWidth, notchHeight)
                    }

                    3 -> {
                        drawRectangularRightShapeNotch(canvas, currentColor)
                    }

                    4 -> {
                        drawWaterdropNotch(canvas)
                    }

                    5 -> {
                        drawUShapeNotch(canvas)
                    }

                    6 -> {
                        drawLeftSideCircleNotch(canvas)
                    }
                }
            } else {
                var selectedColorMode = 1
                var selectedColor: Int = Color.RED
                var selectedColor1: Int = Color.RED
                var selectedColor2: Int = Color.RED
                selectedColor =
                    MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor, selectedColor)
                selectedColor1 =
                    MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor1, selectedColor1)
                selectedColor2 =
                    MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor2, selectedColor2)


                //code to draw the stroke
                val centerX = rect.centerX()
                val centerY = rect.centerY()
                //checking color modes
                selectedColorMode =
                    MySharedPrefs(context).getIntPref(CommonKeys.SelectedColorMode, 1)
                val gradientColors = when (selectedColorMode) {
                    1 -> intArrayOf(selectedColor, selectedColor)
                    2 -> intArrayOf(selectedColor, selectedColor1)
                    3 -> intArrayOf(selectedColor, selectedColor1, selectedColor2)
                    else -> intArrayOf(
                        Color.RED,
                        Color.MAGENTA,
                        Color.YELLOW,
                        Color.GREEN,
                        Color.BLUE,
                        Color.CYAN,
                        Color.MAGENTA
                    )
                }
                //creating gradient from selected colors
                val gradient = SweepGradient(centerX, centerY, gradientColors, null)
                //lines for animations
                val rotationMatrix = Matrix()
                rotationMatrix.setRotate(gradientRotationAngle, centerX, centerY)
                gradient.setLocalMatrix(rotationMatrix)
                paint.shader = gradient

                val currentColor =
                    MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor, Color.RED)
                paint.color = currentColor
                paint.style = Paint.Style.STROKE

                gradientRotationAngle += MySharedPrefs(context).getFloatPref(
                    CommonKeys.ANIM_SPEED,
                    gradientRotationAngle
                ) // Adjust the rotation speed as needed

                if (gradientRotationAngle >= 360f) {
                    gradientRotationAngle -= 360f
                }
                paint.style = Paint.Style.FILL
                val shiftedColor = shiftHue(currentColor, currentHue)
                paint.color = shiftedColor
                // Update the hue for the next frame
                // Adjust the hue change rate as needed

                drawBorderSymbols(
                    canvas,
                    rect.left,
                    rect.top,
                    rect.right,
                    rect.bottom,
                    MySharedPrefs(context).getFloatPref(CommonKeys.Corner_Radius, 20f)
                )
            }
            currentHue += 0.5f
            if (currentHue > 360f) {
                currentHue -= 360f

            }
        }
    }

    private fun drawDefaultStroke(canvas: Canvas, rect: RectF) {
        val currentColor = MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor, Color.RED)
        paint.color = currentColor
        paint.style = Paint.Style.STROKE


        // Apply hue shift to the current color
        val shiftedColor = shiftHue(currentColor, currentHue)
        paint.color = shiftedColor

        // Draw the rounded rectangle with the hue-shifted color
        canvas.drawRoundRect(
            rect,
            MySharedPrefs(context).getFloatPref(CommonKeys.Corner_Radius, 20f),
            MySharedPrefs(context).getFloatPref(CommonKeys.Corner_Radius, 20f),
            paint
        )

        // Update the hue for the next frame
        currentHue += 0.5f // Adjust the hue change rate as needed
        if (currentHue > 360f) {
            currentHue -= 360f

        }
    }


    private fun shiftHue(currentColor: Int, currentHue: Float): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(currentColor, hsv)

        val shiftedHue = (hsv[0] + currentHue) % 360
        hsv[0] = if (shiftedHue < 0) shiftedHue + 360 else shiftedHue

        return Color.HSVToColor(hsv)
    }

    fun NotchDisplay(mode: Int) {
        notchMode = mode
        MySharedPrefs(context).setIntPref(DISPLAY_NOTCH_KEY, mode)
        invalidate()
    }

    fun switchDisplayMode(mode: Int) {

        displayMode = mode
        invalidate()
    }

    fun updateColorsForSingleColorMode() {
        val currentColor = MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor, Color.RED)
        // Update start and end colors for single color mode
        startColorSingle = currentColor
        endColorSingle = currentColor

        // Save the updated color to preferences
        MySharedPrefs(context).setIntPref(CommonKeys.SelectedColor, currentColor)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val startColor = Color.GREEN
        val endColor = Color.RED
        updateGradient(startColor, endColor, startColor, endColor)
        val symbolSize = calculateSymbolSize(w, h)
        cornerRadius = symbolSize / 2

        val rectLeft = 10f
        val rectRight = w.toFloat() - 10f
        val rectTop = 15f
        val rectBottom = h.toFloat() - 10f
        rectF = RectF(
            rectLeft,
            rectTop,
            rectRight,
            rectBottom
        )

        startColorAnimation(startColor, endColor)
    }


    private fun drawBorderSymbols(
        canvas: Canvas,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        cornerRadius: Float
    ) {
        symbolTextSize = MySharedPrefs(context).getFloatPref(CommonKeys.SYMBOL_SIZE, 13f)
        val textSize = symbolTextSize
        paint.textSize = textSize
        val symbolSize =
            cornerRadius * 1.6f // Adjust the multiplier as needed for the desired symbol size
        val gapSize = symbolSize * 0.2f // Set the gap size to 20% of the symbol size

        // Calculate position offset based on the corner radius
        val offsetX = cornerRadius / 2
        val offsetY = cornerRadius / 3

        // Calculate position for drawing the symbols
        val leftPos = left - 2f
        val rightPos = right - 10f
        val topPos = top + 15f
        val bottomPos = bottom + 0f

        // Draw rounded pattern on the top border
        var x = leftPos + cornerRadius
        while (x <= rightPos - cornerRadius) {
            paint.color = currentColor
            canvas.drawSymbol(x, topPos, paint)
            x += symbolSize + gapSize
        }

        // Draw rounded pattern on the bottom border
        x = leftPos + cornerRadius
        while (x <= rightPos - cornerRadius) {
            paint.color = currentColor
            canvas.drawSymbol(x, bottomPos - cornerRadius, paint)
            x += symbolSize + gapSize
        }

        // Draw rounded pattern on the left border
        var y = topPos + cornerRadius
        while (y <= bottomPos - cornerRadius) {
            paint.color = currentColor
            canvas.drawSymbol(leftPos, y, paint)
            y += symbolSize + gapSize
        }

        // Draw rounded pattern on the right border
        y = topPos + cornerRadius
        while (y <= bottomPos - cornerRadius) {
            paint.color = currentColor
            canvas.drawSymbol(rightPos - cornerRadius, y, paint)
            y += symbolSize + gapSize
        }
    }

    private fun calculateSymbolSize(width: Int, height: Int): Float {
        val smallerDimension = minOf(width, height)
        return smallerDimension / 50f // Adjust the divisor as needed for the desired symbol size
    }

    fun updateGradient(startColor1: Int, endColor1: Int, startColor2: Int, endColor2: Int) {
        val isDoubleColorMode = false/* Logic to determine double color mode */

// Retrieve the appropriate color from preferences
        val selectedColor = if (isDoubleColorMode) {
            MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor1, Color.RED)
        } else {
            MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor, Color.RED)
        }
        val gradientTop = LinearGradient(
            rectF?.left ?: 0f, rectF?.top ?: 0f, rectF?.left ?: 0f, rectF?.bottom ?: 0f,
            selectedColor, endColor1, Shader.TileMode.CLAMP
        )
        val gradientBottom = LinearGradient(
            rectF?.left ?: 0f, rectF?.bottom ?: 0f, rectF?.left ?: 0f, rectF?.top ?: 0f,
            startColor2, selectedColor, Shader.TileMode.CLAMP
        )
        val composeShader = ComposeShader(gradientTop, gradientBottom, PorterDuff.Mode.ADD)
        paint.shader = composeShader
        invalidate()
    }


    fun startColorAnimation(startColor: Int, endColor: Int) {
        animator?.cancel()

        val isDoubleColorMode = false
        val selectedColor = if (isDoubleColorMode) {
            MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor1, Color.RED)
        } else {
            MySharedPrefs(context).getIntPref(CommonKeys.SelectedColor, Color.RED)
        }

        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = (1500 / animationSpeed).toLong() // Adjust the duration as desired

            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { animator ->
                val fraction = animator.animatedValue as Float
                val currentColor = interpolateColor(selectedColor, startColor, fraction)
                updateGradient(currentColor, endColor, currentColor, endColor)
                invalidate()
            }
            start()
        }
    }

    private fun interpolateColor(startColor: Int, endColor: Int, fraction: Float): Int {
        val startHsv = FloatArray(3)
        val endHsv = FloatArray(3)
        Color.colorToHSV(startColor, startHsv)
        Color.colorToHSV(endColor, endHsv)

        for (i in 0..2) {
            endHsv[i] = startHsv[i] + fraction * (endHsv[i] - startHsv[i])
        }

        return Color.HSVToColor(endHsv)
    }

    private fun Canvas.drawSymbol(x: Float, y: Float, paint: Paint) {
        customString = MySharedPrefs(context).getStringPref(CommonKeys.CUSTOM_STRING).toString()
        drawText(customString, x, y - ((paint.descent() + paint.ascent()) / 2), paint)
    }


    fun setCustomString(symbol: String) {
        customString = symbol
        invalidate()
    }

    fun setCornerRadiusFromSeekBar(progress: Int) {
        val cornerRadius = progress.toFloat()
        MySharedPrefs(context).setFloatPref(CommonKeys.Corner_Radius, cornerRadius)
        // Update the corner radius and redraw the view
        this.cornerRadius = cornerRadius
        invalidate()
    }


    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }

    fun setSymbolSizeFromSeekBar(progress: Int) {
        val textSize = progress.toFloat()
        symbolTextSize = textSize
        symbolGapSize = textSize / 2 // You can adjust the multiplier as needed
        invalidate()

        // Save the text size to SharedPreferences
        MySharedPrefs(context).setFloatPref("text_size", textSize)
    }


    fun setStrokeWidthFromSeekBar(progress: Int) {
        val strokeWidth = progress.toFloat()
        paint.strokeWidth = strokeWidth

        // Save the stroke width to SharedPreferences
        MySharedPrefs(context).setFloatPref(CommonKeys.STROKE_WIDTH, strokeWidth)

        invalidate() // Redraw the view with the updated stroke width
    }

    fun setUpdateNotch() {

        invalidate() // Redraw the view with the updated stroke width
    }

    fun setAnimationSpeed(speed: Float) {
        animationSpeed = speed
        MySharedPrefs(context).setFloatPref(CommonKeys.ANIM_SPEED, gradientRotationAngle)
        invalidate() // Trigger redraw with the new animation speed
    }

    fun switchDisplayColorMode(i: Int) {

    }

    fun setNotchWidth(progress: Int) {
        val notchWidth = progress.toFloat()
        MySharedPrefs(context).getFloatPref(CommonKeys.NOTCH_WIDTH, notchWidth)
        // Update the corner radius and redraw the view
        this.notchWidthRatio = notchWidth
        invalidate()
    }
}






