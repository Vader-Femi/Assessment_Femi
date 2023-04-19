package com.femi.assessment_femi.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.femi.assessment_femi.databinding.ItemBrandProductTypeBinding


class ProductsAdapter(
    private val clickProduct: (product: String) -> Unit,
) : RecyclerView.Adapter<ProductsAdapter.BrandViewHolder>() {

    inner class BrandViewHolder(val binding: ItemBrandProductTypeBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var products: List<String>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder(
            ItemBrandProductTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.binding.apply {
            val product = products[position]
            tvProduct.text = product
            cvProduct.setOnClickListener {
                clickProduct(product)
            }
        }
    }

    override fun getItemCount() = products.size
}