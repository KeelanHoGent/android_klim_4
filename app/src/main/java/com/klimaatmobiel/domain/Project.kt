package com.klimaatmobiel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Project(val projectId : Long, val projectName : String, val projectDescr : String,
              val projectImage : String, val projectBudget : Double, val classRoomId : Long, val closed : Boolean,
              val applicationDomainId : Long, val applicationDomain : ApplicationDomain, val products : List<Product>) : Parcelable {
}