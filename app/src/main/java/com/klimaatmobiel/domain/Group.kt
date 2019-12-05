package com.klimaatmobiel.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Group(val groupId: Long, val groupName: String, val projectId: Long, val project : Project,
            var order : Order, val uniqueGroupCode: String) : Parcelable {



    fun findOrderItemById(orderitemId : Long) : OrderItem?{
        return order.orderItems.find {
            it.orderItemId == orderitemId
        }
    }






}