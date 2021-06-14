package com.farhan.demo.util

import android.content.Context
import android.content.Intent
import android.view.View


/**
 * Extension [Context]: navigate To with giving Flags
 * @param it Giving Class
 */
fun <T> Context.navigateToWithClearBackStack(it: Class<T>) {
    val intent = Intent(this, it).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

/**
 * Extension [Context]: navigate To
 * @param it Giving Class
 */
fun <T> Context.navigateTo(it: Class<T>) {
    val intent = Intent(this, it)
    startActivity(intent)
}


/**
 * Extension [View]: Visible View
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * Extension [View]: Gone View
 */
fun View.gone() {
    visibility = View.GONE
}
