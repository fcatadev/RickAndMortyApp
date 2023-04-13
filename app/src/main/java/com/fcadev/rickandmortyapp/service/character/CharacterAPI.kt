package com.fcadev.rickandmortyapp.service.character

import androidx.lifecycle.MutableLiveData
import com.fcadev.rickandmortyapp.model.character.RamCharacter
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterAPI {

    //https://rickandmortyapi.com/api/character

    @GET("api/character")
    fun getCharacters(): Single<RamCharacter>

    @GET("api/character/{ids}")
    fun getCharactersByIds(@Path("ids") ids: String): Single<RamCharacter>
}
