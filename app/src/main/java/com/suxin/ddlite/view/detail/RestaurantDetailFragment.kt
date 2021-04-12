package com.suxin.ddlite.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.suxin.ddlite.R
import com.suxin.ddlite.model.RestaurantDetailModel
import com.suxin.ddlite.viewmodel.RestaurantDetailViewModel
import com.suxin.ddlite.viewmodel.RestaurantDetailViewModelFactory

class RestaurantDetailFragment: Fragment() {

    companion object {
        fun newInstance() = RestaurantDetailFragment()
        const val RESTAURANT_ID = "Restaurant_ID"
        const val HEADER_URL = "header_url"
        const val COVER_URL = "cover_url"
    }

    private lateinit var detailViewModel: RestaurantDetailViewModel
    private lateinit var headerImageView: ImageView
    private lateinit var coverImageView: ImageView
    private lateinit var name: TextView
    private lateinit var rating: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var address: TextView
    private lateinit var headerUrl: String
    private lateinit var coverUrl: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.detail_fragment, container, false)
        headerImageView = root.findViewById(R.id.header_image)
        coverImageView = root.findViewById(R.id.cover_image)
        name = root.findViewById(R.id.name)
        rating = root.findViewById(R.id.rating)
        phoneNumber = root.findViewById(R.id.phone_number)
        address = root.findViewById(R.id.address)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = arguments
        val restaurantId = bundle?.getString(RESTAURANT_ID)
        headerUrl = bundle?.getString(HEADER_URL) ?: ""
        coverUrl = bundle?.getString(COVER_URL) ?: ""
        if (restaurantId != null) {
            detailViewModel = ViewModelProvider(this,
                RestaurantDetailViewModelFactory(
                    restaurantId
                )
            ).get(RestaurantDetailViewModel::class.java)
            detailViewModel.getData().observe(this, object : Observer<RestaurantDetailModel>{
                override fun onChanged(model: RestaurantDetailModel?) {
                    setupView(model)
                }
            })
        }
    }

    private fun setupView(model: RestaurantDetailModel?) {
        if (model == null) {
            return
        }
        name.text = model.name
        rating.text = model.average_rating.toString() + "/5"
        phoneNumber.text = model.phone_number
        address.text = model.address.printable_address

        if (headerUrl.isNotEmpty()) {
            Picasso.get().load(headerUrl).into(headerImageView)
        } else {
            Picasso.get().load("https://reactnativecode.com/wp-content/uploads/2018/02/Default_Image_Thumbnail.png").into(headerImageView)
        }

        if (coverUrl.isNotEmpty()) {
            Picasso.get().load(coverUrl).into(coverImageView)
        }
    }
}