package com.fcadev.rickandmortyapp.ui.view.characterList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcadev.rickandmortyapp.databinding.FragmentCharacterListBinding
import com.fcadev.rickandmortyapp.model.character.CharacterResult
import com.fcadev.rickandmortyapp.model.character.RamCharacter
import com.fcadev.rickandmortyapp.service.character.CharacterAPIService
import com.fcadev.rickandmortyapp.viewmodel.CharacterListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharacterListViewModel
    private lateinit var locationAdapter: LocationListAdapter
    private val characterAdapter = CharacterListAdapter(arrayListOf())
    private lateinit var characterList : MutableList<CharacterResult>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CharacterListViewModel::class.java]
        viewModel.downloadLocationData()
        viewModel.downloadCharacterData()
        locationAdapter = LocationListAdapter(arrayListOf(), viewModel)

        setRecyclerView()
        observeLiveData()
    }

    private fun setRecyclerView() {
        binding.rvLocation.adapter = locationAdapter
        binding.rvLocation.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvCharacters.adapter = characterAdapter
        binding.rvCharacters.layoutManager = LinearLayoutManager(context)
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

        viewModel.multipleCharactersByLocation.observe(viewLifecycleOwner, Observer { characters ->
            characters?.let {
                binding.rvCharacters.visibility = View.VISIBLE
                characterAdapter.updateCharacterList(characters)
            }
        })

        viewModel.singleCharacterByLocation.observe(viewLifecycleOwner, Observer { characters ->
            characters?.let {
                binding.rvCharacters.visibility = View.VISIBLE
                characterAdapter.updateSingleCharacterList(characters)
            }
        })

        viewModel.locationsLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    binding.rvLocation.visibility = View.GONE
                    binding.rvCharacters.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.clStartedPage.alpha = 0.2f
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.rvLocation.visibility = View.VISIBLE
                    binding.rvCharacters.visibility = View.VISIBLE
                    binding.clStartedPage.alpha = 1f
                }
            }
        })
    }
}