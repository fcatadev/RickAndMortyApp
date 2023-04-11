package com.fcadev.rickandmortyapp.service.location

import com.fcadev.rickandmortyapp.model.location.RamLocation
import io.reactivex.Single
import retrofit2.http.GET

interface LocationAPI {

    //https://rickandmortyapi.com/api/location

    @GET("api/location")
    fun getLocations(): Single<RamLocation>

}