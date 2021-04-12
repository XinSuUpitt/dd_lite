package com.suxin.ddlite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RestaurantDetailViewModelFactory(private val restaurantId: String): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantDetailViewModel(restaurantId) as T
    }
}