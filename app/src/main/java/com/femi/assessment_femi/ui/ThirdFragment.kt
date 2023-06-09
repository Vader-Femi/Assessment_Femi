package com.femi.assessment_femi.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.femi.assessment_femi.R
import com.femi.assessment_femi.data.product.ProductItem
import com.femi.assessment_femi.databinding.FragmentThirdBinding
import com.femi.assessment_femi.viewmodels.ProductsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ProductsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root

    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("KEY_PRODUCT_NAME", ProductItem::class.java)
        } else {
            arguments?.getSerializable("KEY_PRODUCT_NAME") as ProductItem
        }
        if (productItem == null) {
            Timber.i("Cannot find product")
            Toast.makeText(context, "Cannot find product", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }

        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(this)
            .load(productItem?.image_link)
            .placeholder(R.drawable.ic_downloading)
            .error(R.drawable.ic_error)
            .circleCrop()
            .transition(withCrossFade(factory))
            .into(binding.imgProduct)


        binding.tvName.text = productItem?.name
        binding.tvDesc.text = productItem?.description
        binding.tvPrice.text = productItem?.price
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}