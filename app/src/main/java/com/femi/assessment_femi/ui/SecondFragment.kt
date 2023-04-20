package com.femi.assessment_femi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.femi.assessment_femi.R
import com.femi.assessment_femi.data.adapters.ProductsAdapter
import com.femi.assessment_femi.databinding.FragmentSecondBinding
import com.femi.assessment_femi.viewmodels.ProductsViewModel
import timber.log.Timber

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ProductsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() = binding.rvBrandProductType.apply {
        val productsAdapter = ProductsAdapter{ productName ->
            val bundle = Bundle()
            bundle.putSerializable("KEY_PRODUCT_NAME", productName)
            Timber.i("Loading $productName")
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment, bundle)
        }
        adapter = productsAdapter
        layoutManager = LinearLayoutManager(context)

        viewModel.selectedBrandProducts.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.CREATED ||
                viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.STARTED ||
                viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED
            ) {
                productsAdapter.products = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}