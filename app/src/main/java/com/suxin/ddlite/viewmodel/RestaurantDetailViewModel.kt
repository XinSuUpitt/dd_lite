package com.suxin.ddlite.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suxin.ddlite.model.RestaurantDetailModel
import com.suxin.ddlite.DoorDashRetrofitManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RestaurantDetailViewModel(val restaurantId: String) : ViewModel() {
    companion object {
        private val TAG = RestaurantDetailViewModel::class.java.simpleName
    }
    private var restaurantLiveData: MutableLiveData<RestaurantDetailModel> = MutableLiveData()

    init {
        fetchData()
    }

    fun getData(): MutableLiveData<RestaurantDetailModel> {
        return restaurantLiveData
    }

    private fun fetchData() {
        DoorDashRetrofitManager()
            .getDoorDashService()
            ?.getRestaurantDetailData(
                restaurantId = restaurantId
            )
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                restaurantLiveData.postValue(it)
            },
                {
                    Log.e(TAG, "Error when fetching restaurant detail ${it.message}")
                })
    }
}