package com.example.ns.ui

data class MenuItem(
    val id: Int,
    val iconRes: Int,
    val activeColor: Int,
    val inactiveColor: Int,
    val shouldChangeColor: Boolean? = true,
    val text: String? = null
)
