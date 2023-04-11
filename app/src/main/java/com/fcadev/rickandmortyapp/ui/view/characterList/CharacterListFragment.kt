package com.fcadev.rickandmortyapp.ui.view.characterList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcadev.rickandmortyapp.R
import com.fcadev.rickandmortyapp.databinding.FragmentCharacterListBinding
import com.fcadev.rickandmortyapp.viewmodel.CharacterListViewModel

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharacterListViewModel
    private val locationAdapter = LocationListAdapter(arrayListOf())
    private val characterAdapter = CharacterListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CharacterListViewModel::class.java]
        viewModel.downloadLocationData()
        viewModel.downloadCharacterData()

        binding.rvLocation.adapter = locationAdapter
        binding.rvLocation.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvCharacters.adapter = characterAdapter
        binding.rvCharacters.layoutManager = LinearLayoutManager(context)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.locations.observe(viewLifecycleOwner, Observer { locations ->
            locations?.let {
                binding.rvLocation.visibility = View.VISIBLE
                locationAdapter.updateLocationList(locations)
            }
        })

        viewModel.characters.observe(viewLifecycleOwner, Observer { characters ->
            characters?.let {
                binding.rvCharacters.visibility = View.VISIBLE
                characterAdapter.updateCharacterList(characters)
            }
        })

        viewModel.locationsLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    binding.rvLocation.visibility = View.GONE
                    binding.rvCharacters.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    //binding.clStartedPage.alpha = 0.2f
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.rvLocation.visibility = View.VISIBLE
                    binding.rvCharacters.visibility = View.VISIBLE
                    //binding.clStartedPage.alpha = 1f
                }
            }
        })
    }
}