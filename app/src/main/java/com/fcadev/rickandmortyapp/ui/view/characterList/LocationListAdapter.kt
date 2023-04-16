package com.fcadev.rickandmortyapp.ui.view.characterList

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.fcadev.rickandmortyapp.R
import com.fcadev.rickandmortyapp.databinding.LocationItemRowBinding
import com.fcadev.rickandmortyapp.model.character.CharacterResult
import com.fcadev.rickandmortyapp.model.character.RamCharacter
import com.fcadev.rickandmortyapp.model.location.Result
import com.fcadev.rickandmortyapp.service.character.CharacterAPIService
import com.fcadev.rickandmortyapp.viewmodel.CharacterListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LocationListAdapter(
    private val locationList: ArrayList<Result>,
    viewModel: CharacterListViewModel,
    private val context: Context
) :
    RecyclerView.Adapter<LocationListAdapter.LocationListViewHolder>() {

    private var residentCostsArray: MutableLiveData<ArrayList<String>> =
        viewModel.residentNumbersArray
    private val charListViewModel = viewModel
    private var selectedPosition = RecyclerView.NO_POSITION
    private var onItemClickListener: ((position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        onItemClickListener = listener
    }

    class LocationListViewHolder(var binding: LocationItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {
        val binding =
            LocationItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationListViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val locationItem = locationList[position]

        holder.binding.tvLocationBtnName.text = locationItem.name

        if (selectedPosition == position) {
            holder.binding.cvLocationBtn.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.character_card_border
                )
            )
        } else {
            holder.binding.cvLocationBtn.setCardBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
        }

        holder.binding.cvLocationBtn.setOnClickListener {
            val residentsArray = ArrayList<String>()
            val residentsList = locationItem.residents
            val size = residentsList!!.size
            for (i in 0 until size) {
                residentsArray.add(residentsList[i]!!)
            }

            val residentNumbersArray = ArrayList<String>()
            for (i in 0 until size) {
                val residentUrl = residentsArray[i]
                val residentNumber = residentUrl.substring(residentUrl.lastIndexOf("/") + 1)
                residentNumbersArray.add(residentNumber)
            }

            if (position != selectedPosition) {
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                onItemClickListener?.invoke(position)
            }

            Log.d("residents", residentNumbersArray.toString())
            residentCostsArray.value = residentNumbersArray
            charListViewModel.getCharacterDataByLocation()
        }
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    fun clearSelection() {
        val previousPosition = selectedPosition
        selectedPosition = RecyclerView.NO_POSITION
        notifyItemChanged(previousPosition)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateLocationList(newLocationList: MutableList<Result>) {
        locationList.clear()
        locationList.addAll(newLocationList)
        notifyDataSetChanged()
    }
}