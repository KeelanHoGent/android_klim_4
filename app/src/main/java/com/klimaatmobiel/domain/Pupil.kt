package com.klimaatmobiel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Pupil(val pupilId: Long,
            val firstName: String,
            val surname: String): Parcelable {

}