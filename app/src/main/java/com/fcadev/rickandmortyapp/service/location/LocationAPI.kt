package com.fcadev.rickandmortyapp.service.location

import com.fcadev.rickandmortyapp.model.location.RamLocation
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationAPI {

    //https://rickandmortyapi.com/api/location

    @GET("api/location")
    fun getLocations(@Query("page") page: String): Single<RamLocation>

}