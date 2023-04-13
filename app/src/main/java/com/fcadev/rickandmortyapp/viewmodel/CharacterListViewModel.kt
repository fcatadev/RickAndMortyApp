package com.fcadev.rickandmortyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fcadev.rickandmortyapp.model.character.CharacterResult
import com.fcadev.rickandmortyapp.model.character.RamCharacter
import com.fcadev.rickandmortyapp.model.location.RamLocation
import com.fcadev.rickandmortyapp.model.location.Result
import com.fcadev.rickandmortyapp.service.character.CharacterAPIService
import com.fcadev.rickandmortyapp.service.location.LocationAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CharacterListViewModel : ViewModel() {

    private val locationAPIService = LocationAPIService()
    private val characterAPIService = CharacterAPIService()
    private val disposable = CompositeDisposable()

    val locations = MutableLiveData<MutableList<Result>>(mutableListOf())
    val characters = MutableLiveData<MutableList<CharacterResult>?>(mutableListOf())
    val locationsLoading = MutableLiveData<Boolean>()
    val residentNumbersArray = MutableLiveData<ArrayList<String>>()


    fun downloadLocationData(){
        getLocationDataFromAPI()
    }

    fun downloadCharacterData(){
        getCharacterDataFromAPI()
    }

    fun downloadCharacterDataByLocation(){
        getCharacterDataByLocation()
    }

    private fun getLocationDataFromAPI(){
        locationsLoading.value = true

        disposable.add(
            locationAPIService.getLocationsData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RamLocation>(){
                    override fun onSuccess(t: RamLocation) {
                        locations.value = t.results as MutableList<Result>?
                        locationsLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        locationsLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun getCharacterDataFromAPI(){
        locationsLoading.value = true

        disposable.add(
            characterAPIService.getCharacterData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RamCharacter>(){
                    override fun onSuccess(t: RamCharacter) {
                        characters.value = t.results as MutableList<CharacterResult>?
                        locationsLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        locationsLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun getCharacterDataByLocation(){
        locationsLoading.value = true

        val ids = residentNumbersArray
        disposable.add(
            characterAPIService.getCharactersByIds(ids)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RamCharacter>(){
                    override fun onSuccess(t: RamCharacter) {
                        characters.value = t.results as MutableList<CharacterResult>?
                        locationsLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        locationsLoading.value = false
                        e.printStackTrace()
                    }
                })
        )

    }

}
