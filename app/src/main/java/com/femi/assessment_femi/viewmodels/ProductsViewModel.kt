package com.femi.assessment_femi.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.femi.assessment_femi.data.product.ProductItem
import com.femi.assessment_femi.data.remote.Resource
import com.femi.assessment_femi.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository,
) : ViewModel() {

    //All products and brands
    fun getProducts() = viewModelScope.launch {
        _getProductsResponse.value = Resource.Loading
        _getProductsResponse.value = repository.getProducts()
    }

    private val _getProductsResponse: MutableLiveData<Resource<List<ProductItem>>> =
        MutableLiveData()
    val getProductsResponse: LiveData<Resource<List<ProductItem>>>
        get() = _getProductsResponse

    private var allMakeupProducts: MutableLiveData<List<ProductItem>> =
        MutableLiveData<List<ProductItem>>()

    var brands: MutableLiveData<List<String>> =
        MutableLiveData<List<String>>()
        private set

    fun saveProductsAndBrand(value: List<ProductItem>) = viewModelScope.launch {
        allMakeupProducts.value = value

        val allBrands = arrayListOf<String>()
        value.forEach {
            if (!it.brand.isNullOrEmpty()) // Ignore the suggestion, there is actually one null brand
                allBrands.add(it.brand)
        }
        brands.value = allBrands.distinct()
    }

    // Products of a brand
    var selectedBrandProducts: MutableLiveData<List<String>> =
        MutableLiveData<List<String>>()
        private set

    fun selectedBrand(brand: String) {
        viewModelScope.launch(Dispatchers.Main) {
            saveSelectedBrandProducts(brand)
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectedBrand(brand)
        }

    }

    private suspend fun selectedBrand() = repository.selectedBrand()

    private fun saveSelectedBrandProducts(brand: String) = viewModelScope.launch {
        val brandProducts = arrayListOf<String>()
        allMakeupProducts.value?.forEach { productItem ->
            if (productItem.brand == brand) {
                brandProducts.add(productItem.name)
                return@forEach
            }
        }
        selectedBrandProducts.value = brandProducts
    }

    var selectedProductItem: MutableLiveData<ProductItem> =
        MutableLiveData<ProductItem>()
        private set

    fun getSelectedProductItem(productName: String) = viewModelScope.launch {
        allMakeupProducts.value?.forEach { productItem ->
            if (productItem.name == productName) {
                selectedProductItem.value = productItem
                return@forEach
            }
        }
    }
}