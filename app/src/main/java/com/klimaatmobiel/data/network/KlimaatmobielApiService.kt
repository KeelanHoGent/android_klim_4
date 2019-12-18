package com.klimaatmobiel.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.klimaatmobiel.domain.*
import com.klimaatmobiel.domain.DTOs.RemoveOrAddedOrderItemDTO
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

// launch backend as http -- line 39 properties "applicationUrl": "http://localhost:5000"

//private const val BASE_URL = "https://klimaatmobiel.daandedecker.com/api/"







interface KlimaatmobielApiService {
    // API CALLS HERE


    @GET("group/project/{groupCode}")
    fun getFullGroup(@Path("groupCode") groupCode: String):
            Deferred<Group>

    @GET("project/{projectCode}/products/{productCode}")
    fun getProduct(@Path("projectCode") projectCode: Long, @Path("productCode") productCode: Long): Deferred<Product>

    @GET("project/{projectCode}")
    fun getProject(@Path("projectCode") projectCode: Long): Deferred<Project>


    @PUT("order/addOrderItem/{orderId}" )
    fun addProductToOrder(@Body dto : OrderItem, @Path("orderId") orderId : Long) : Deferred<RemoveOrAddedOrderItemDTO>


    @PUT("order/removeOrderItem/{orderItemId}/{orderId}" )
    fun removeOrderItemFromOrder(@Path("orderItemId") orderItemId : Long, @Path("orderId") orderId : Long) : Deferred<RemoveOrAddedOrderItemDTO>


    @PUT("orderItem/substractOne/{orderItemId}")
    fun substractOrderItemByOne(@Body dto: OrderItem, @Path("orderItemId") orderItemId: Long): Deferred<RemoveOrAddedOrderItemDTO>

    @PUT("orderItem/addOne/{orderItemId}")
    fun addOrderItemByOne(@Body dto: OrderItem, @Path("orderItemId") orderItemId: Long): Deferred<RemoveOrAddedOrderItemDTO>

    @PUT("orderItem/{orderItemId}" )
    fun updateOrderItem(@Body dto : OrderItem, @Path("orderItemId") orderItemId : Long) : Deferred<RemoveOrAddedOrderItemDTO>

    @PUT("group/changePupils/{groupId}")
    fun changePupils(@Body dto: Group, @Path("groupId")groupId: Long): Deferred<Group>

    @PUT("order/removeAllOrderItems/{orderId}")
    fun removeAllOrderItems(@Path("orderId") orderId: Long): Deferred<Order>
}
/*
object KlimaatmobielApi {
    val retrofitService: KlimaatmobielApiService by lazy {
        retrofit.create(KlimaatmobielApiService::class.java)
    }
}
*/
