package net.jahez.jahezchallenge.Network


import net.jahez.jahezchallenge.Model.RestaurantModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {
    @GET("restaurants.json")
    fun getAllRestaurants(): Call<List<RestaurantModel>>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://jahez-other-oniiphi8.s3.eu-central-1.amazonaws.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}