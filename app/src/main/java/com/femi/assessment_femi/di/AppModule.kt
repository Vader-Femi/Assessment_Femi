package com.femi.assessment_femi.di

import android.app.Application
import com.femi.assessment_femi.data.pref.UserPreferences
import com.femi.assessment_femi.data.remote.ProductsApi
import com.femi.assessment_femi.data.remote.RetrofitInstance
import com.femi.assessment_femi.data.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(application: Application): UserPreferences {
        return UserPreferences(application)
    }

    @Provides
    @Singleton
    fun provideProductsRepository(api: ProductsApi, userPreferences: UserPreferences): ProductsRepository{
        return ProductsRepository(api, userPreferences)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(): ProductsApi {
        return RetrofitInstance.api
    }



}