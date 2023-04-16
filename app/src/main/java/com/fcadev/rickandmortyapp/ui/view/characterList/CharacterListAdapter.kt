package com.fcadev.rickandmortyapp.ui.view.characterList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fcadev.rickandmortyapp.R
import com.fcadev.rickandmortyapp.databinding.CharacterItemRowBinding
import com.fcadev.rickandmortyapp.model.character.CharacterResult
import androidx.navigation.Navigation.findNavController
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class CharacterListAdapter(private val characterList: ArrayList<CharacterResult>) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

    var onItemClick: ((CharacterResult) -> Unit)? = null

    class CharacterListViewHolder(var binding: CharacterItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val binding =
            CharacterItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        val characterItem = characterList[position]
        val imgUrl = characterItem.image

        Glide.with(holder.binding.root.context).load(imgUrl)
            .centerCrop()
            .into(holder.binding.ivCharacterPicture)

        holder.binding.tvNameText.text = characterItem.name

        when (characterItem.gender) {
            "Male" -> {
                holder.binding.ivGenderLogo.setImageResource(R.drawable.male)
            }
            "Female" -> {
                holder.binding.ivGenderLogo.setImageResource(R.drawable.femenine)
            }
            else -> {
                holder.binding.ivGenderLogo.setImageResource(R.drawable.planet)
            }
        }

        holder.binding.cvExpenseItem.setOnClickListener {
            onItemClick?.invoke(characterItem)

            val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
            val outputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm:ss")
            val createdDate = LocalDateTime.parse(characterItem.created, inputFormatter)
                .atOffset(ZoneOffset.UTC)
                .format(outputFormatter)

            val episodeUrls = characterList[position].episode!!
            var episodeNumbersString = ""

            for (episodeUrl in episodeUrls) {
                val episodeNumber = episodeUrl!!.split("episode/").last()
                episodeNumbersString += "$episodeNumber, "
            }

            episodeNumbersString = episodeNumbersString.dropLast(2)

            val showEditLocation = if (characterItem.location!!.name.toString().length > 18) {
                characterItem.location.name.toString().substring(0, 18) + "..."
            } else {
                characterItem.location.name.toString()
            }

            val showEditOrigin = if (characterItem.origin!!.name.toString().length > 18) {
                characterItem.origin.name.toString().substring(0, 18) + "..."
            } else {
                characterItem.origin.name.toString()
            }

            val bundle = Bundle()
            bundle.putString("characterName", characterItem.name)
            bundle.putString("characterStatus", characterItem.status)
            bundle.putString("characterGender", characterItem.gender)
            bundle.putString("characterCreated", createdDate)
            bundle.putString("characterImage", characterItem.image)
            bundle.putString("characterOrigin", showEditOrigin)
            bundle.putString("characterLocation", showEditLocation)
            bundle.putString("characterEpisodes", episodeNumbersString)
            bundle.putString("characterSpecy", characterItem.species)


            Log.d("name :", characterItem.name.toString())

            findNavController(it).navigate(
                R.id.action_characterListFragment_to_characterDetailFragment,
                bundle
            )
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCharacterList(newCharacterList: MutableList<CharacterResult>) {
        characterList.clear()
        characterList.addAll(newCharacterList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSingleCharacterList(newCharacterList: CharacterResult) {
        characterList.clear()
        characterList.addAll(listOf(newCharacterList))
        notifyDataSetChanged()
    }
}