package com.example.manetesartistes_game

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomCanvasView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var bgColor: Int = Color.WHITE;
    private var currentPath: Path? = null
    private var startX = 0f
    private var startY = 0f
    private var isDrawing = false

    private val figures = mutableListOf<Figure>()
    var isEditable: Boolean? = true
    private var selectedColor = Color.WHITE

    private val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
    }

    private val fillPaint = Paint().apply {
        color = Color.TRANSPARENT
        style = Paint.Style.FILL
    }

    private val borderPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 20f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        // transparent color for teacher
        if(isEditable == true){
            fillPaint.color = Color.TRANSPARENT
            borderPaint.color = Color.BLACK
            paint.color = Color.TRANSPARENT

        }else{
            paint.color = bgColor
        }

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        // render first biggest figures
        val sortedFigures = figures.sortedByDescending { calculateArea(it) }

        for (figure in sortedFigures) {
            fillPaint.color = figure.color
            canvas.drawPath(figure.path, fillPaint)
            canvas.drawPath(figure.path, borderPaint)
        }

        currentPath?.let {
            canvas.drawPath(it, borderPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if(isEditable == true){
                    currentPath = Path().apply {
                        moveTo(x, y)
                    }
                    startX = x
                    startY = y
                    isDrawing = true
                }else{
                    val touchedFigure = getTouchedFigure(x, y)
                    if (touchedFigure != null) {
                        touchedFigure.color = selectedColor
                    }else{
                        // only change the background color if no figure was touched
                        bgColor = selectedColor
                    }
                }

                invalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath?.lineTo(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                currentPath?.let {
                    it.lineTo(startX, startY)

                    figures.add(Figure(Path(it), Color.TRANSPARENT))

                    currentPath = null
                }
                isDrawing = false
                invalidate()
            }
        }

        return true
    }

    // Detect figure touch with smallest area
    private fun getTouchedFigure(x: Float, y: Float): Figure? {
        var smallestFigure: Figure? = null
        var smallestArea: Float = Float.MAX_VALUE

        for (figure in figures) {
            val figureBounds = RectF()
            figure.path.computeBounds(figureBounds, true)

            // Check if the touch point is within the figure's bounds
            if (figureBounds.contains(x, y)) {
                // Calculate the area of the figure
                val area =  calculateArea(figure)

                // Update the smallest figure if this one is smaller
                if (area < smallestArea) {
                    smallestArea = area
                    smallestFigure = figure
                }
            }
        }

        return smallestFigure
    }

    private fun calculateArea(figure: Figure): Float {
        val figureBounds = RectF()
        figure.path.computeBounds(figureBounds, true)
        return figureBounds.width() * figureBounds.height()
    }

    fun toggleTeacherMode() {
        isEditable = !(isEditable ?: true)
        invalidate() // Redraw the canvas to reflect the mode change
    }

    fun setSelectedColor(color: Int) {
        selectedColor = color
    }
}
