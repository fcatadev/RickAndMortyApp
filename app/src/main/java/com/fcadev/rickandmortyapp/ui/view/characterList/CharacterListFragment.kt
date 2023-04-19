package com.fcadev.rickandmortyapp.ui.view.characterList

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcadev.rickandmortyapp.R
import com.fcadev.rickandmortyapp.databinding.FragmentCharacterListBinding
import com.fcadev.rickandmortyapp.model.character.CharacterResult
import com.fcadev.rickandmortyapp.model.character.RamCharacter
import com.fcadev.rickandmortyapp.service.character.CharacterAPIService
import com.fcadev.rickandmortyapp.viewmodel.CharacterListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharacterListViewModel
    private lateinit var locationAdapter: LocationListAdapter
    private val characterAdapter = CharacterListAdapter(arrayListOf())
    private var characterListPageNumber = 1
    private var locationListPageNumber = 1

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
        viewModel.downloadLocationData("$locationListPageNumber")
        viewModel.downloadCharacterData("$characterListPageNumber")
        locationAdapter = LocationListAdapter(arrayListOf(), viewModel, requireContext())

        setRecyclerView()
        initListener()
        observeLiveData()
        updatePreviousButton()
    }

    private fun setRecyclerView() {
        binding.rvLocation.adapter = locationAdapter
        binding.rvLocation.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.rvCharacters.adapter = characterAdapter
        binding.rvCharacters.layoutManager = LinearLayoutManager(context)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initListener() {
        binding.cvAllBtn.setOnClickListener {
            locationAdapter.clearSelection()
            binding.cvAllBtn.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.character_card_border
                )
            )
            viewModel.downloadCharacterData("$characterListPageNumber")
            binding.clBottomButton.visibility = View.VISIBLE
        }

        locationAdapter.setOnItemClickListener {
            binding.cvAllBtn.setCardBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.clBottomButton.visibility = View.GONE
        }

        binding.cvNextBtn.setOnClickListener {
            characterListPageNumber++
            viewModel.downloadCharacterData("$characterListPageNumber")
            updatePreviousButton()
        }

        binding.rvLocation.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisibleItemPosition == totalItemCount - 1 && locationListPageNumber <= 7) {
                    locationListPageNumber++
                    binding.pbLocationList.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).postDelayed({
                        viewModel.downloadLocationData(locationListPageNumber.toString())
                        binding.pbLocationList.visibility = View.GONE
                    }, 3000)
                    Log.d("Location page ", locationListPageNumber.toString())
                }
            }
        })
    }


    private fun updatePreviousButton() {
        if (characterListPageNumber == 1) {
            binding.cvPreviousBtn.isCheckable = false
            binding.cvPreviousBtn.alpha = 0.5f
        } else {
            binding.cvPreviousBtn.isCheckable = true
            binding.cvPreviousBtn.alpha = 1.0f
        }
        binding.cvPreviousBtn.setOnClickListener {
            if (characterListPageNumber != 1) {
                characterListPageNumber--
                viewModel.downloadCharacterData("$characterListPageNumber")
                updatePreviousButton()
            }
        }
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
                    binding.cvAllBtn.visibility = View.GONE
                    binding.cvNextBtn.visibility = View.GONE
                    binding.cvPreviousBtn.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.clStartedPage.alpha = 0.2f
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.rvLocation.visibility = View.VISIBLE
                    binding.rvCharacters.visibility = View.VISIBLE
                    binding.cvAllBtn.visibility = View.VISIBLE
                    binding.cvNextBtn.visibility = View.VISIBLE
                    binding.cvPreviousBtn.visibility = View.VISIBLE
                    binding.clStartedPage.alpha = 1f
                }
            }
        })
    }
}