package com.fcadev.rickandmortyapp.service.location

import com.fcadev.rickandmortyapp.model.location.RamLocation
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LocationAPIService {

    //https://rickandmortyapi.com/api/location

    private val BASE_URL = "https://rickandmortyapi.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(LocationAPI::class.java)

    fun getLocationsData(): Single<RamLocation>{
        return api.getLocations()
    }

}