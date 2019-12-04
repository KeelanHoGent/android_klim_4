package com.klimaatmobiel.domain

import android.os.Parcelable
import com.klimaatmobiel.data.database.DatabaseProduct
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(val productId : Long, val productName : String, val description : String,
              val productImage : String?, val projectId : Long, val price : Double, val score: Int,
              val categoryId : Long, val category : Category?) : Parcelable {

}

fun List<Product>.asDatabaseModel(): List<DatabaseProduct> {
    return map {
        DatabaseProduct(
            it.productId,
            it.productName,
            it.description,
            it.productImage,
            it.projectId,
            it.price,
            it.score,
            it.category!!)
    }
}