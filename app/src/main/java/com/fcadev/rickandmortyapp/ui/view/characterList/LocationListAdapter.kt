package com.fcadev.rickandmortyapp.ui.view.characterList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fcadev.rickandmortyapp.databinding.LocationItemRowBinding
import com.fcadev.rickandmortyapp.model.location.Result

class LocationListAdapter(private val locationList : ArrayList<Result>) :
    RecyclerView.Adapter<LocationListAdapter.LocationListViewHolder>(){

    class LocationListViewHolder(var binding: LocationItemRowBinding) :
        RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {
        val binding = LocationItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationListViewHolder, position: Int) {
        val locationItem = locationList[position]

        holder.binding.tvLocationBtnName.text = locationItem.name
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateLocationList(newLocationList: MutableList<Result>){
        locationList.clear()
        locationList.addAll(newLocationList)
        notifyDataSetChanged()
    }
}