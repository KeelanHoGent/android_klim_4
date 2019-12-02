package com.klimaatmobiel.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.klimaatmobiel.domain.Category
import com.klimaatmobiel.domain.Product

@Entity
data class DatabaseProduct constructor(
    @PrimaryKey
    val productId : Long,
    val productName : String,
    val description : String,
    val productImage : String?,
    val projectId : Long,
    val price : Double,
    val score: Int,
    @Embedded
    val category : Category
)

fun DatabaseProduct.asDomainModel(): Product {
    return Product (
            productId = projectId,
            productName = productName,
            description = description,
            productImage = productImage,
            projectId = projectId,
            price = price,
            score = score,
            categoryId = category.categoryId,
            category = category)
}