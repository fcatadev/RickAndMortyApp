package com.fcadev.rickandmortyapp.service.character

import com.fcadev.rickandmortyapp.model.character.CharacterResult
import com.fcadev.rickandmortyapp.model.character.RamCharacter
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CharacterAPIService {

    //https://rickandmortyapi.com/api/character

    private val BASE_URL = "https://rickandmortyapi.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CharacterAPI::class.java)

    fun getCharacterData(page: String): Single<RamCharacter>{
        return api.getCharacters(page = page)
    }

    fun getCharactersByIds(ids: String): Single<ArrayList<CharacterResult>> {
        return api.getCharactersByIds(ids)
    }

    fun getCharactersBySingleId(id: String): Single<CharacterResult> {
        return api.getCharactersBySingleId(id)
    }
}
