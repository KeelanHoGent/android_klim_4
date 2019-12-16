package com.klimaatmobiel.ui

import com.klimaatmobiel.ui.viewModels.AddGroupViewModel
import com.klimaatmobiel.ui.viewModels.MainMenuViewModel
import com.klimaatmobiel.ui.viewModels.ProductDetailViewModel
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel


val appViewModelModule = module {
    viewModel {ProductDetailViewModel(get(), get(), get())}
    viewModel { AddGroupViewModel(get(), get()) }
    viewModel { MainMenuViewModel(get()) }
    viewModel { WebshopViewModel(get(), get()) }
}