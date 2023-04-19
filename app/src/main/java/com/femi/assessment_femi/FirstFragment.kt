package com.femi.assessment_femi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.femi.assessment_femi.data.adapters.BrandsAdapter
import com.femi.assessment_femi.data.remote.Resource
import com.femi.assessment_femi.databinding.FragmentFirstBinding
import com.femi.assessment_femi.viewmodels.ProductsViewModel
import timber.log.Timber

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ProductsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getProducts()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.getProducts()
        setupObserver()
        setupRecyclerView()

    }

    private fun setupObserver() {
        viewModel.getProductsResponse.observe(viewLifecycleOwner) { response ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.CREATED ||
                viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.STARTED ||
                viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED
            ) {
                binding.progressBar.isVisible = response is Resource.Loading
                when (response) {
                    is Resource.Success -> {
                        viewModel.saveProductsAndBrand(response.value)
                    }

                    is Resource.Failure -> {
                        handleApiError(response) { viewModel.getProducts() }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.rvBrands.apply {
        val brandsAdapter = BrandsAdapter { brand ->
            Timber.i("Showing brand - $brand")
            viewModel.selectedBrand(brand)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        adapter = brandsAdapter
        layoutManager = LinearLayoutManager(context)
        viewModel.brands.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.CREATED ||
                viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.STARTED ||
                viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED
            ) {
                brandsAdapter.brands = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}