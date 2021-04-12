package com.suxin.ddlite.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suxin.ddlite.DoorDashRetrofitManager
import com.suxin.ddlite.model.RestaurantModel
import com.suxin.ddlite.view.main.RestaurantListFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RestaurantListViewModel : ViewModel() {

    companion object {

        private val TAG = RestaurantListFragment::class.java.simpleName
        private const val LAT = "37.422740"
        private const val LNG = "-122.139956"
        private const val LIMIT = "50"
    }

    var restaurantsLiveData: MutableLiveData<List<RestaurantModel>> = MutableLiveData()

    init {
        init()
    }

    fun getData(): MutableLiveData<List<RestaurantModel>> {
        return restaurantsLiveData
    }

    private fun init() {
        getMoreData(0)
    }

    fun getMoreData(page: Int) {
        DoorDashRetrofitManager()
            .getDoorDashService()
            ?.getRestaurantListData(
                LAT,
                LNG,
                (page * 50).toString(),
                LIMIT
            )
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                val originalValues = restaurantsLiveData.value
                val newValue = mutableListOf<RestaurantModel>()
                if (originalValues != null) {
                    newValue.addAll(originalValues)
                }
                newValue.addAll(it.stores)
                restaurantsLiveData.postValue(newValue)
            },
                {
                    Log.e(TAG, "Error when fetching restaurant list ${it.message}")
                })
    }
}