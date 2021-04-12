package com.suxin.ddlite.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.suxin.ddlite.R
import com.suxin.ddlite.model.RestaurantModel


class RecyclerViewAdapter(
    private val context: Context,
    private val restaurants: MutableList<RestaurantModel>,
    private val clickListener: RestaurantClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val rootView: View = LayoutInflater.from(context).inflate(R.layout.restaurant_card, parent, false)
        return RecyclerViewViewHolder(rootView)
    }

    override fun onBindViewHolder(
        @NonNull holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val restaurant: RestaurantModel =
            restaurants[position]
        val viewHolder =
            holder as RecyclerViewViewHolder
        viewHolder.viewTitle.text = restaurant.name
        viewHolder.viewDescription.text = restaurant.description
        viewHolder.distance.text = String.format("%.2f miles", restaurant.distance_from_consumer)

        Picasso.get().load(restaurant.cover_img_url)
            .into(viewHolder.imageView)

        viewHolder.bind(restaurant, clickListener)

    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    fun update(newRestaurants: List<RestaurantModel>) {
        restaurants.clear()
        restaurants.addAll(newRestaurants)
        notifyDataSetChanged()
    }

    internal inner class RecyclerViewViewHolder(@NonNull itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.image)
        var viewTitle: TextView = itemView.findViewById(R.id.title)
        var viewDescription: TextView = itemView.findViewById(R.id.description)
        var distance: TextView = itemView.findViewById(R.id.distance)

        fun bind(restaurantModel: RestaurantModel, clickListener: RestaurantClickListener) {
            itemView.setOnClickListener {
                clickListener.onItemClicked(restaurantModel)
            }
        }
    }

}