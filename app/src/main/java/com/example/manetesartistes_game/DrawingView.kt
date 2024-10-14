package com.example.manetesartistes_game

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View

class DrawingView(context: Context) : View(context) {
    private var path = Path() // Ruta del dibujo con el dedo
    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 8f
        isAntiAlias = true
    }

    // Define un área limitada (ejemplo: un rectángulo)
    private val limitRect = RectF(100f, 100f, 600f, 600f)
    private val limitRegion = Region().apply {
        val path = Path().apply { addRect(limitRect, Path.Direction.CW) }
        setPath(path, Region(0, 0, 600, 600))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Dibuja el área límite (opcional, solo para visualización)
        val areaPaint = Paint().apply {
            color = Color.LTGRAY
            style = Paint.Style.FILL
        }
        canvas.drawRect(limitRect, areaPaint)

        // Crea una región para el trazado del usuario
        val clipPath = Path()
        clipPath.addPath(path)

        val clipRegion = Region()
        clipRegion.setPath(clipPath, Region(0, 0, 600, 600))

        // Aplica la operación de intersección para recortar el dibujo fuera de los límites
        if (clipRegion.op(limitRegion, Region.Op.INTERSECT)) {
            // Si hay intersección, dibuja el trazado recortado
            clipRegion.getBoundaryPath(clipPath)
            canvas.drawPath(clipPath, paint)        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y) // Comienza una nueva línea
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(event.x, event.y) // Dibuja la línea mientras el dedo se mueve
                invalidate() // Redibuja la vista
            }
        }
        return true
    }

    // Método para borrar el trazado
    fun clearDrawing() {
        path.reset()
        invalidate()
    }
}