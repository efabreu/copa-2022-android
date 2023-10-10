package com.efabreu.copa.catar.remote.di

import dagger.Module
import dagger.Provides
import com.efabreu.copa.catar.remote.services.MatchesServices
import retrofit2.Retrofit
import retrofit2.create

@Module
class ServiceModules {
    @Provides
    fun provideAuthService(retrofit: Retrofit) = retrofit.create<MatchesServices>()
}
