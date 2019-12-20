package com.klimaatmobiel.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Order(val orderId : Long,
            val time : String,
            var submitted : Boolean,
            val approved : Boolean,
            var totalOrderPrice: Double,
            val groupId : Long,
            val avgScore: Double,
            var orderItems : MutableList<OrderItem>
) : Parcelable {


}