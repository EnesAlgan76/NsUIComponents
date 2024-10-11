package com.algan.nsuicomponents.imageview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import com.algan.nsuicomponents.R

class NSImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    private var cornerRadius: Float = 0f
    private var borderColor: Int = Color.TRANSPARENT
    private var borderWidth: Float = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    init {
        attrs?.let {
            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.NSImageView)
            cornerRadius = typedArray.getDimension(R.styleable.NSImageView_imageCornerRadius, 0f)
            borderColor = typedArray.getColor(R.styleable.NSImageView_imageBorderColor, Color.TRANSPARENT)
            borderWidth = typedArray.getDimension(R.styleable.NSImageView_imageBorderWidth, 0f)
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()

        path.reset()
        path.addRoundRect(0f, 0f, width, height, cornerRadius, cornerRadius, Path.Direction.CW)

        canvas.clipPath(path)

        super.onDraw(canvas)

        if (borderWidth > 0) {
            paint.style = Paint.Style.STROKE
            paint.color = borderColor
            paint.strokeWidth = borderWidth
            canvas.drawPath(path, paint)
        }
    }
}
