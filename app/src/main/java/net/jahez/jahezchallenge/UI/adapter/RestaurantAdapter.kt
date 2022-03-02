package net.jahez.jahezchallenge.UI.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import net.jahez.jahezchallenge.databinding.CardviewRestaurantBinding
import net.jahez.jahezchallenge.Model.RestaurantModel


class RestaurantAdapter: RecyclerView.Adapter<MainViewHolder>() {
    var restaurants = mutableListOf<RestaurantModel>()
    fun setRestaurantList(restaurants: List<RestaurantModel>?) {
        if (restaurants != null) {
            this.restaurants = restaurants.toMutableList()
        }
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardviewRestaurantBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.binding.tvRestaurantName.text = restaurant.name
        holder.binding.tvRestaurantHours.text = restaurant.hours
        Glide.with(holder.itemView.context).load(restaurant.image).into(holder.binding.ivRestaurantImage)
        var distance = String.format("%.2f", restaurant.distance)
        holder.binding.tvRestaurantDistance.text =  "$distance km"
    }
    override fun getItemCount(): Int {
        return restaurants.size
    }
}
class MainViewHolder(val binding: CardviewRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
}

