package com.klimaatmobiel.ui

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.klimaatmobiel.PusherApplication
import com.klimaatmobiel.data.database.WebshopDatabase
import com.klimaatmobiel.data.network.KlimaatmobielApiService
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.viewModels.AddGroupViewModel
import com.klimaatmobiel.ui.viewModels.MainMenuViewModel
import com.klimaatmobiel.ui.viewModels.ProductDetailViewModel
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val appViewModelModule = module {
    viewModel { ProductDetailViewModel(get(), PusherApplication.huidigProjectId, PusherApplication.huidigProductId)}
    viewModel { AddGroupViewModel(PusherApplication.group, get()) }
    viewModel { MainMenuViewModel(get()) }
    viewModel { WebshopViewModel(PusherApplication.group, get()) }
    single { KlimaatmobielRepository(retrofitService, getDatabase(androidContext())) }
}




fun getDatabase(context: Context): WebshopDatabase {
    synchronized(WebshopDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                WebshopDatabase::class.java,
                "webshop").fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}

private fun moshi(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun retrofit() = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi()).asLenient())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

private const val BASE_URL = "http://10.0.2.2:5000/api/"
private lateinit var INSTANCE: WebshopDatabase
val retrofitService: KlimaatmobielApiService by lazy {
    retrofit().create(KlimaatmobielApiService::class.java)
}