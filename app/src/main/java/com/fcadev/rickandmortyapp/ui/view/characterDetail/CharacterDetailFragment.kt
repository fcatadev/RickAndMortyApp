package com.fcadev.rickandmortyapp.ui.view.characterDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.fcadev.rickandmortyapp.R
import com.fcadev.rickandmortyapp.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCharacterDetails()
    }

    private fun getCharacterDetails() {
        val bundle = arguments
        if (bundle != null) {
            val args = CharacterDetailFragmentArgs.fromBundle(bundle)

            val imageUrl = args.characterImage
            Glide.with(requireContext()).load(imageUrl)
                .centerCrop()
                .into(binding.ivCharacterDetailImage)

            val editEpisode = if (args.characterEpisodes.toString().length > 20){
                args.characterEpisodes.toString().substring(0, 20) + "..."
            }else {
                args.characterEpisodes.toString()
            }

            if (args.characterEpisodes.toString().length > 20){
                binding.ivAllButton.visibility = View.VISIBLE
            }else {
                binding.ivAllButton.visibility = View.GONE
            }

            binding.ivAllButton.setOnClickListener {

            }

            binding.tvCharacterDetailName.text = args.characterName.toString()
            binding.tvStatusContent.text = args.characterStatus.toString()
            binding.tvGenderContent.text = args.characterGender.toString()
            binding.tvCreatedAtContent.text = args.characterCreated.toString()
            binding.tvOriginContent.text = args.characterOrigin.toString()
            binding.tvLocationContent.text = args.characterLocation.toString()
            binding.tvSpecyContent.text = args.characterSpecy.toString()
            binding.tvEpisodesContent.text = editEpisode
        }
    }
}