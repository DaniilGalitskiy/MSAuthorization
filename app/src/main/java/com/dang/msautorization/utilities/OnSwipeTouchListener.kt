package com.dang.msautorization.utilities

import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs


open class OnSwipeTouchListener : OnTouchListener {
    private val gestureDetector = GestureDetector(GestureListener())
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    private inner class GestureListener : SimpleOnGestureListener() {

        override fun onFling(e1: MotionEvent,
                             e2: MotionEvent,
                             velocityX: Float,
                             velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffX) <= abs(diffY)) {
                    if (abs(diffY) > SWIPE_THRESHOLD && abs(
                                    velocityY
                            ) > SWIPE_VELOCITY_THRESHOLD) {
                        result = if (diffY > 0) {
                            onSwipeBottom()
                        } else {
                            onSwipeTop()
                        }
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }

    }

    open fun onSwipeTop(): Boolean {
        return false
    }

    open fun onSwipeBottom(): Boolean {
        return false
    }
}