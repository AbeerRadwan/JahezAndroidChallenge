package net.jahez.jahezchallenge.Network

class MainRepository constructor(private val retrofitService: RetrofitService) {
        fun getAllRestaurants() = retrofitService.getAllRestaurants()
    }
