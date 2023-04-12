package com.fcadev.rickandmortyapp.ui.view.characterList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fcadev.rickandmortyapp.R
import com.fcadev.rickandmortyapp.databinding.CharacterItemRowBinding
import com.fcadev.rickandmortyapp.model.character.CharacterResult

class CharacterListAdapter(private val characterList: ArrayList<CharacterResult>) :
    RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

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
}