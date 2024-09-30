package com.algan.nsuicomponents.textfields
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.algan.nsuicomponents.R
class NSEditText : AppCompatEditText {
    private var cornerRadius = 0f
    private var edgeWidth = 0f
    private var edgeColor = 0
    private var backgroundColor = 0

    private var borderPaint: Paint? = null
    private var backgroundPaint: Paint? = null
    private var rectF: RectF? = null

    constructor(context: Context?) : super(context!!) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            // Load custom attributes
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NSEditText)

            cornerRadius = typedArray.getDimension(R.styleable.NSEditText_cornerRadius, 0f)
            edgeWidth =
                typedArray.getDimension(R.styleable.NSEditText_edgeWidth, 2f) // Default edge width
            edgeColor = typedArray.getColor(
                R.styleable.NSEditText_edgeColor,
                Color.BLACK
            ) // Default edge color
            backgroundColor = typedArray.getColor(
                R.styleable.NSEditText_backgroundColor,
                Color.WHITE
            ) // Default background color

            typedArray.recycle()
        }

        // Initialize paints
        borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        borderPaint!!.style = Paint.Style.STROKE
        borderPaint!!.strokeWidth = edgeWidth
        borderPaint!!.color = edgeColor

        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint!!.style = Paint.Style.FILL
        backgroundPaint!!.color = backgroundColor

        rectF = RectF()
    }

    override fun onDraw(canvas: Canvas) {
        // Set the boundaries for the rectangle (minus the padding for the border)
        rectF!!.left = edgeWidth / 2
        rectF!!.top = edgeWidth / 2
        rectF!!.right = width - edgeWidth / 2
        rectF!!.bottom = height - edgeWidth / 2

        // Draw background with corner radius
        canvas.drawRoundRect(rectF!!, cornerRadius, cornerRadius, backgroundPaint!!)

        // Draw the border with corner radius
        canvas.drawRoundRect(rectF!!, cornerRadius, cornerRadius, borderPaint!!)

        // Draw the text and cursor on top of the custom background and border
        super.onDraw(canvas)
    }

    // Public setters to change attributes programmatically if needed
    fun setCornerRadius(radius: Float) {
        this.cornerRadius = radius
        invalidate() // Redraw with new corner radius
    }

    fun setEdgeWidth(width: Float) {
        this.edgeWidth = width
        borderPaint!!.strokeWidth = width
        invalidate() // Redraw with new edge width
    }

    fun setEdgeColor(color: Int) {
        this.edgeColor = color
        borderPaint!!.color = color
        invalidate() // Redraw with new edge color
    }

    override fun setBackgroundColor(color: Int) {
        this.backgroundColor = color
        backgroundPaint!!.color = color
        invalidate() // Redraw with new background color
    }
}
