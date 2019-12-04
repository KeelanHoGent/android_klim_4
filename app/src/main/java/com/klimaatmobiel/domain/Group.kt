package com.klimaatmobiel.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Group(val groupId: Long, val groupName: String, val projectId: Double, val project : Project,
            var order : Order, val uniqueGroupCode: String, val pupils: MutableList<Pupil>) : Parcelable {

    fun findOrderItemById(orderitemId : Long) : OrderItem?{
        return order.orderItems.find {
            it.orderItemId == orderitemId
        }
    }

    fun addPupil(pupilName: String) {
        val pupilNames = pupilName.split(" ")
        val pupil = Pupil(null, pupilNames[0], pupilNames[1])

        pupils.add(pupil)
    }

}