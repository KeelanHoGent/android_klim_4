package com.klimaatmobiel.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.klimaatmobiel.domain.ApplicationDomain
import com.klimaatmobiel.domain.Category
import com.klimaatmobiel.domain.Product
import com.klimaatmobiel.domain.Project

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

@Entity
data class DatabaseProject constructor(
    @PrimaryKey
    val projectId : Long,
    val projectName : String,
    val projectDescr : String,
    val projectImage : String,
    val projectBudget : Double,
    val classRoomId : Long,
    val closed : Boolean,
    @Embedded
    val applicationDomain : ApplicationDomain,
    val products : List<Product>
)

fun DatabaseProject.asDomainModel(): Project {
    return Project (
        projectId = projectId,
        projectName = projectName,
        projectDescr = projectDescr,
        projectImage = projectImage,
        projectBudget = projectBudget,
        closed = closed,
        applicationDomain = applicationDomain,
        applicationDomainId = applicationDomain.applicationDomainId,
        products = products,
        classRoomId = classRoomId
        )
}

