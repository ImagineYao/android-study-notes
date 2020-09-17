package com.example.eventdispatch.util

import android.util.Log
import java.lang.StringBuilder

/**
 * 自定义Log，以不同缩进展示不同层级的log
 */
object LogUtil {
    private const val tagLength = 12

    enum class Depth {
        ACTIVITY, VIEW_GROUP, VIEW
    }

    private fun setIndent(depth: Depth): Int {
        return when(depth) {
            Depth.ACTIVITY -> 0
            Depth.VIEW_GROUP -> 2
            Depth.VIEW -> 4
        }
    }

    private fun getSpaceString(len: Int): String {
        val stringBuilder = StringBuilder()
        for (i in 0 until len) {
            stringBuilder.append(" ")
        }
        return stringBuilder.toString()
    }

    fun i(tag: String, message: String, depth: Depth) {
        val indentString = getSpaceString(setIndent(depth))
        val mTag = indentString + tag
        val spaceString = getSpaceString(tagLength - mTag.length)
        val mMessage = spaceString + message
        Log.i(mTag, mMessage)
    }
}