package com.klimaatmobiel.domain

import androidx.core.view.ViewCompat.animate
import android.R.attr.rotation
import android.view.View


class Animations {

    fun toggleArrow(view: View): Boolean {

            view.animate().setDuration(200).rotation(180f)
            return true
    }
}