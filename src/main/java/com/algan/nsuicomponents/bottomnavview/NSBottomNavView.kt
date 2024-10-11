package com.algan.nsuicomponents.bottomnavview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.algan.nsuicomponents.R
import com.example.ns.ui.MenuItem

class NSBottomNavView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var menuItems: List<MenuItem> = listOf()
    private var selectedItemId: Int = -1
    private var navViewBackgroundColor: Int
    private var iconSize: Int
    private val menuItemViews = mutableMapOf<Int, View>() // Store item views
    var onItemSelectedListener: ((MenuItem) -> Unit)? = null

    init {
        orientation = HORIZONTAL

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NSBottomNavView)
        navViewBackgroundColor = typedArray.getColor(R.styleable.NSBottomNavView_navViewBackgroundColor, ContextCompat.getColor(context, R.color.white))
        iconSize = typedArray.getDimensionPixelSize(R.styleable.NSBottomNavView_iconSize, resources.getDimensionPixelSize(R.dimen.default_icon_size))
        typedArray.recycle()

        setBackgroundColor(navViewBackgroundColor)
        setPadding(16)
    }

    fun setSelectedItem(id: Int) {
        val previousSelectedId = selectedItemId
        selectedItemId = id
        updateMenuItem(previousSelectedId)
        updateMenuItem(selectedItemId)
        menuItems.find { menuItem ->menuItem.id == id }?.let { onItemSelectedListener?.invoke(it) }
    }

    fun setMenu(items: List<MenuItem>) {
        menuItems = items
        updateMenuView()
    }

    private fun updateMenuView() {
        removeAllViews()
        menuItemViews.clear()
        menuItems.forEach { item ->
            val view = createMenuItemView(item)
            addView(view)
            menuItemViews[item.id] = view
        }
    }

    private fun createMenuItemView(item: MenuItem): View {
        val view = LayoutInflater.from(context).inflate(R.layout.view_bottom_nav_item, this, false)
        val icon = view.findViewById<ImageView>(R.id.nav_item_icon)
        val textView = view.findViewById<TextView>(R.id.nav_item_text)


        icon.layoutParams.width = iconSize
        icon.layoutParams.height = iconSize

        updateIconColor(icon, item)

        if (item.text != null) {
            textView.visibility = View.VISIBLE
            textView.text = item.text
        } else {
            textView.visibility = View.GONE
        }

        view.setOnClickListener {
            setSelectedItem(item.id)
        }

        return view
    }

    private fun updateMenuItem(itemId: Int) {
        menuItemViews[itemId]?.let { view ->
            val item = menuItems.find { it.id == itemId } ?: return@let
            val icon = view.findViewById<ImageView>(R.id.nav_item_icon)
            val textView = view.findViewById<TextView>(R.id.nav_item_text)

            // Update icon color
            updateIconColor(icon, item)

            item.text?.let {
                val textColorRes = if (item.id == selectedItemId) {
                    item.activeColor
                } else {
                    item.inactiveColor
                }
                textView.setTextColor(ContextCompat.getColor(context, textColorRes))
            }
        }
    }


    private fun updateIconColor(icon: ImageView, item: MenuItem) {
        item.shouldChangeColor?.let { shouldChangeColor ->
            if (!shouldChangeColor) {
                icon.setImageResource(item.iconRes)
                return
            }
        }
        val colorRes = if (item.id == selectedItemId) {
            item.activeColor
        } else {
            item.inactiveColor
        }
        icon.setImageResource(item.iconRes)
        icon.setColorFilter(ContextCompat.getColor(context, colorRes))
    }

    fun getSelectedItemId(): Int = selectedItemId

    fun setNavViewBackgroundColor(colorRes: Int) {
        setBackgroundColor(ContextCompat.getColor(context, colorRes))
    }

    fun setIconSize(sizePx: Int) {
        iconSize = sizePx
        menuItems.forEach { updateMenuItem(it.id) }
    }
}
