package com.klimaatmobiel.domain.DTOs



import android.os.Parcelable
import com.klimaatmobiel.domain.OrderItem
import com.klimaatmobiel.domain.Pupil
import kotlinx.android.parcel.Parcelize


@Parcelize
class RemoveOrAddedOrderItemDTO(val totalOrderPrice : Double, val removedOrAddedOrderItem : OrderItem): Parcelable {
}
