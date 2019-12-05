package com.klimaatmobiel.domain

import com.klimaatmobiel.data.database.WebshopDatabase
import com.klimaatmobiel.data.database.asDomainModel
import com.klimaatmobiel.data.network.KlimaatmobielApiService
import com.klimaatmobiel.domain.DTOs.RemoveOrAddedOrderItemDTO
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KlimaatmobielRepository(private val apiService: KlimaatmobielApiService, private val database: WebshopDatabase) {

    fun getFullGroup(groupCode: String): Deferred<Group> {
        return apiService.getFullGroup(groupCode)
    }


    fun addProductToOrder(orderItem: OrderItem, orderId: Long): Deferred<RemoveOrAddedOrderItemDTO> {
        return apiService.addProductToOrder(orderItem, orderId)
    }

    fun updateOrderItem(orderItem: OrderItem, orderId: Long): Deferred<RemoveOrAddedOrderItemDTO> {
        return apiService.updateOrderItem(orderItem, orderId)
    }

    fun removeOrderItemFromOrder(orderItemId: Long, orderId: Long): Deferred<RemoveOrAddedOrderItemDTO> {
        return apiService.removeOrderItemFromOrder(orderItemId, orderId)
    }

    suspend fun getProduct(projectId: Long, productId: Long) : Product {
        return withContext(Dispatchers.IO) {
            database.productDao.getProduct(projectId, productId).asDomainModel()
        }
    }
    suspend fun getProject(projectId: Long) : Project {
        return withContext(Dispatchers.IO) {
            database.projectDao.getProject(projectId).asDomainModel()
        }
    }

    suspend fun refreshProducts(products: List<Product>) {
        withContext(Dispatchers.IO) {
            database.productDao.insertAll(products.asDatabaseModel())
         }
    }
}