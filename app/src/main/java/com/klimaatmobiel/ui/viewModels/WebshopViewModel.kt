package com.klimaatmobiel.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.klimaatmobiel.domain.*
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber



class WebshopViewModel(group: Group, private val repository: KlimaatmobielRepository) : ViewModel() {


    private var _status = MutableLiveData<KlimaatMobielApiStatus>()
    val status: LiveData<KlimaatMobielApiStatus> get() = _status

    var posToRefreshInOrderPreviewListItem: Int = -1

    private var _group = MutableLiveData<Group>()
    val group: LiveData<Group> get() = _group

    private val _navigateToWebshop = MutableLiveData<List<Long>>()
    val navigateToWebshop: LiveData<List<Long>> get() = _navigateToWebshop



    init {
        _group.value = group // de groep met het project end de order is hier beschikbaar

    }

    fun addProductToOrder(product: Product){
        viewModelScope.launch {

            val addProductToOrderDeferred = repository.addProductToOrder(OrderItem(0,1,null,product.productId, 0),_group.value!!.order.orderId)
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val orderItemRes = addProductToOrderDeferred.await()

                if(orderItemRes.removedOrAddedOrderItem.amount > 1){ // the orderitem is already in the orderitem

                    _group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId)!!
                        .amount = orderItemRes.removedOrAddedOrderItem.amount

                    posToRefreshInOrderPreviewListItem = _group.value!!.order.orderItems
                        .indexOf(_group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId))

                } else {
                    posToRefreshInOrderPreviewListItem = -1
                    _group.value!!.order.orderItems.add(orderItemRes.removedOrAddedOrderItem)
                }



                _group.value!!.order.totalOrderPrice = orderItemRes.totalOrderPrice

                _group.value = _group.value // trigger live data change, moet wss niet?

                _status.value = KlimaatMobielApiStatus.DONE

            }catch (e: HttpException) {
                Timber.i(e.message())
                _status.value = KlimaatMobielApiStatus.ERROR
            }
            catch (e: Exception) {
                Timber.i(e)
                _status.value = KlimaatMobielApiStatus.ERROR
            }
        }
    }


    fun changeOrderItemAmount(oi: OrderItem, add: Boolean){
        if(add){
            oi.amount++
            updateOrderItem(oi)
        } else {
            oi.amount--
            if(oi.amount < 1) {
                removeOrderItem(oi)
            } else {
                updateOrderItem(oi)
            }
        }
    }


    private fun updateOrderItem(oi: OrderItem){

        viewModelScope.launch {

            val updateOrderItemDeferred = repository.updateOrderItem(oi, oi.orderItemId)
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val orderItemRes = updateOrderItemDeferred.await()

                _group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId)!!
                    .amount = orderItemRes.removedOrAddedOrderItem.amount
                _group.value!!.order.totalOrderPrice = orderItemRes.totalOrderPrice

                posToRefreshInOrderPreviewListItem = _group.value!!.order.orderItems
                    .indexOf(_group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId))

                _group.value = _group.value // trigger live data change, moet wss niet?

                _status.value = KlimaatMobielApiStatus.DONE

            }catch (e: HttpException) {
                Timber.i(e.message())
                _status.value = KlimaatMobielApiStatus.ERROR
            }
            catch (e: Exception) {
                Timber.i(e)
                _status.value = KlimaatMobielApiStatus.ERROR
            }
        }

    }

    fun removeOrderItem(oi : OrderItem){

        viewModelScope.launch {
            val removeOrderItemDeferred = repository.removeOrderItemFromOrder(oi.orderItemId, group.value!!.order.orderId)
            try {
                _status.value = KlimaatMobielApiStatus.LOADING
                val orderItemRes = removeOrderItemDeferred.await()

                _group.value!!.order.orderItems.remove( _group.value!!.findOrderItemById(orderItemRes.removedOrAddedOrderItem.orderItemId)!!)
                _group.value!!.order.totalOrderPrice = orderItemRes.totalOrderPrice

                posToRefreshInOrderPreviewListItem = -1

                _group.value = _group.value // trigger live data change, moet wss niet?

                _status.value = KlimaatMobielApiStatus.DONE

            }catch (e: HttpException) {
                Timber.i(e.message())
                _status.value = KlimaatMobielApiStatus.ERROR
            }
            catch (e: Exception) {
                _status.value = KlimaatMobielApiStatus.ERROR
            }
        }
    }

    fun onProductClicked(product: Product, action: Int) {
        when(action) {
            0 -> addProductToOrder(product)
            1 -> {
                _navigateToWebshop.value = listOf(product.projectId, product.productId)
                Timber.i("productid: ${product.projectId} and ${product.productId}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}