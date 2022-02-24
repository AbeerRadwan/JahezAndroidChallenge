package net.jahez.jahezchallenge.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.jahez.jahezchallenge.R

import net.jahez.jahezchallenge.databinding.CardviewRestaurantBinding
import net.jahez.jahezchallenge.model.RestaurantModel


class RestaurantAdapter (var restaurantList: List<RestaurantModel>,
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    // create an inner class with name ViewHolder
    // It takes a view argument, in which pass the generated class of single_item.xml
    // ie SingleItemBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
     class ViewHolder(val binding: CardviewRestaurantBinding) : RecyclerView.ViewHolder(binding.root)

    // inside the onCreateViewHolder inflate the view of CardviewRestaurantBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = CardviewRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    // bind the items with each item
    // of the list languageList
    // which than will be
    // shown in recycler view
    // to keep it simple we are
    // not setting any image data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(restaurantList[position]){

                binding.tvRestaurantName.text = this.name
                binding.tvRestaurantHours.text = this.hours
                Glide.with(holder.itemView.context).load(this.image).into(binding.ivRestaurantImage)
                var distance = String.format("%.2f", this.distance)
                binding.tvRestaurantDistance.text =  "$distance km"
            }
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return restaurantList.size
    }
}