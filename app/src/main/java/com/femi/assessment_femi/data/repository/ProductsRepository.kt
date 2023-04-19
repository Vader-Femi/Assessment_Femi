package com.femi.assessment_femi.data.repository

import com.femi.assessment_femi.data.pref.UserPreferences
import com.femi.assessment_femi.data.remote.ProductsApi
import com.femi.assessment_femi.data.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val api: ProductsApi,
    private val userPreferences: UserPreferences,
) {

    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> T,
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            //Already being logged in the "handleApiError" function in Utills
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }

    suspend fun getProducts() = safeApiCall {
        api.getProducts()
    }

    suspend fun selectedBrand() = userPreferences.selectedBrand.first()

    suspend fun selectedBrand(brand: String) =
        userPreferences.selectedBrand(brand)
}