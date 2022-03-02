package net.jahez.jahezchallenge.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.jahez.jahezchallenge.Model.RestaurantModel
import net.jahez.jahezchallenge.Network.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainViewModel constructor(private val repository: MainRepository)  : ViewModel() {

    val restaurantsList = MutableLiveData<List<RestaurantModel>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllRestaurants() {
        val response = repository.getAllRestaurants()
        response.enqueue(object : Callback<List<RestaurantModel>> {
            override fun onResponse(call: Call<List<RestaurantModel>>, response: Response<List<RestaurantModel>>) {
                restaurantsList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<RestaurantModel>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}