package com.fcadev.rickandmortyapp.service.character

import com.fcadev.rickandmortyapp.model.character.RamCharacter
import io.reactivex.Single
import retrofit2.http.GET

interface CharacterAPI {

    //https://rickandmortyapi.com/api/character

    @GET("api/character")
    fun getCharacters(): Single<RamCharacter>

}