package com.suxin.ddlite.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suxin.ddlite.R
import com.suxin.ddlite.model.RestaurantModel
import com.suxin.ddlite.view.EndlessScrollListener
import com.suxin.ddlite.view.detail.RestaurantDetailFragment
import com.suxin.ddlite.view.detail.RestaurantDetailFragment.Companion.COVER_URL
import com.suxin.ddlite.view.detail.RestaurantDetailFragment.Companion.HEADER_URL
import com.suxin.ddlite.view.detail.RestaurantDetailFragment.Companion.RESTAURANT_ID
import com.suxin.ddlite.viewmodel.RestaurantListViewModel

class RestaurantListFragment : Fragment(), RestaurantClickListener {

    companion object {
        fun newInstance() = RestaurantListFragment()
    }

    private lateinit var listViewModel: RestaurantListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var restaurantListFragment: RestaurantListFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        restaurantListFragment = this
        val root = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerView = root.findViewById(R.id.restaurant_list)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        listViewModel = ViewModelProvider(this).get(RestaurantListViewModel::class.java)

        listViewModel.getData().observe(this, object : Observer<List<RestaurantModel>> {
            override fun onChanged(restaurantModels: List<RestaurantModel>?) {
                val context = context
                if (context != null && restaurantModels != null) {
                    if (recyclerView.adapter == null) {
                        recyclerViewAdapter = RecyclerViewAdapter(context,
                            restaurantModels as MutableList<RestaurantModel>, restaurantListFragment)
                        recyclerView.adapter = recyclerViewAdapter
                    } else {
                        (recyclerView.adapter as? RecyclerViewAdapter)?.update(restaurantModels)
                    }
                }
            }
        })
        recyclerView.addOnScrollListener(object : EndlessScrollListener(recyclerView.layoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                listViewModel.getMoreData(page)
            }
        })
    }

    override fun onItemClicked(restaurant: RestaurantModel) {
        val detailFragment = RestaurantDetailFragment.newInstance()
        val bundle = Bundle()
        bundle.putString(RESTAURANT_ID, restaurant.id)
        bundle.putString(HEADER_URL, restaurant.header_img_url)
        bundle.putString(COVER_URL, restaurant.cover_img_url)
        detailFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, detailFragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}