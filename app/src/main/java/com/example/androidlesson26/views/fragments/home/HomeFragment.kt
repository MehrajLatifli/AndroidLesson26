package com.example.androidlesson26.views.fragments.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.androidlesson26.R
import com.example.androidlesson26.databinding.FragmentHomeBinding
import com.example.androidlesson26.utilities.gone
import com.example.androidlesson26.utilities.visible
import com.example.androidlesson26.viewmodels.HomeViewModel
import com.example.androidlesson26.views.adapters.NamazTimeAdapder
import com.example.androidlesson26.views.fragments.base.BaseFragment
import com.google.android.gms.common.util.DeviceProperties.isTablet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()

    private val namazTimeAdapder = NamazTimeAdapder()
    private var searchJob: Job? = null
    private var city = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()

        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim()
             //   searchJob?.cancel()

                searchJob = lifecycleScope.launch(Dispatchers.Main) {
                    delay(100)


                    if (searchText.isNotBlank()) {

                        city = searchText
                        viewModel.getAllNamazTimeByRegion(searchText)

                        updateSearchDrawable(true)
                    } else {
                        updateSearchDrawable(false)
                    }
                }
            }
        })


    }


    private fun observeData() {
        viewModel.times.observe(viewLifecycleOwner) { items ->
            namazTimeAdapder.updateList(items)
            binding.cityname.text = "Search results for ${city}"
        }



        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBarContainer.progressBar2.visible()
            } else {
                binding.progressBarContainer.progressBar2.gone()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->


            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }


        val spanCount = if (isTablet()) 3 else 1
        val gridLayoutManager = GridLayoutManager(context, spanCount)
        binding.recycleView.layoutManager = gridLayoutManager
        binding.recycleView.adapter = namazTimeAdapder

    }

    private fun isTablet(): Boolean {
        val configuration = resources.configuration
        return configuration.smallestScreenWidthDp >= 600
    }

    private fun updateSearchDrawable(isSearchActive: Boolean) {
        val drawableId = if (isSearchActive) R.drawable.search else R.drawable.search
        val tintColorId = if (isSearchActive) R.color.brown else R.color.darkgray
        binding.searchText.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
        binding.searchText.compoundDrawables[0].setTint(
            ContextCompat.getColor(
                requireContext(), tintColorId
            )
        )
    }

}