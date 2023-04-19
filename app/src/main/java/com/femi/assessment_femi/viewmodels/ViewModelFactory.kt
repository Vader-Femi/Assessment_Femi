package com.femi.assessment_femi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.femi.assessment_femi.data.repository.ProductsRepository

class ViewModelFactory(
    private val repository: ProductsRepository // This would have been a base repo, but no need here
    ): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> ProductsViewModel(
                repository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }
    }
}