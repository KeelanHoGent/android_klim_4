package com.klimaatmobiel.domain

import com.klimaatmobiel.data.database.ProductsDatabase
import com.klimaatmobiel.data.database.asDomainModel
import com.klimaatmobiel.data.network.KlimaatmobielApiService
import com.klimaatmobiel.domain.DTOs.RemoveOrAddedOrderItemDTO
import com.klimaatmobiel.ui.groupSpentBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KlimaatmobielRepository(private val apiService: KlimaatmobielApiService, private val database: ProductsDatabase) {

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

    suspend fun refreshProducts(products: List<Product>) {
        withContext(Dispatchers.IO) {
            database.productDao.insertAll(products.asDatabaseModel())
        }
    }

    fun changePupils(group: Group): Deferred<Group> {
        return apiService.changePupils(group, group.groupId)
    }
}