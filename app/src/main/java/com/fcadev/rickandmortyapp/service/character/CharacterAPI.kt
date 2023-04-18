package com.fcadev.rickandmortyapp.service.character

import com.fcadev.rickandmortyapp.model.character.CharacterResult
import com.fcadev.rickandmortyapp.model.character.RamCharacter
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterAPI {

    //https://rickandmortyapi.com/api/character

    @GET("api/character/")
    fun getCharacters(@Query("page") page: String): Single<RamCharacter>

    @GET("api/character/{ids}")
    fun getCharactersByIds(@Path("ids") ids: String): Single<ArrayList<CharacterResult>>

    @GET("api/character/{id}")
    fun getCharactersBySingleId(@Path("id") id: String): Single<CharacterResult>
}
