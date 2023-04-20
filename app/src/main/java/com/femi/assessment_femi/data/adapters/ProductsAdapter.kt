package com.femi.assessment_femi.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.femi.assessment_femi.data.product.ProductItem
import com.femi.assessment_femi.databinding.ItemBrandProductTypeBinding


class ProductsAdapter(
    private val clickProduct: (product: ProductItem) -> Unit,
) : RecyclerView.Adapter<ProductsAdapter.BrandViewHolder>() {

    inner class BrandViewHolder(val binding: ItemBrandProductTypeBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<ProductItem>() {
        override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var products: List<ProductItem>
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
            tvProduct.text = product.name
            cvProduct.setOnClickListener {
                clickProduct(product)
            }
        }
    }

    override fun getItemCount() = products.size
}