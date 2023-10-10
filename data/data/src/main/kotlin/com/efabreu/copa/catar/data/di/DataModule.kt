package com.efabreu.copa.catar.data.di

import dagger.Binds
import dagger.Module
import com.efabreu.copa.catar.data.repository.MatchesRepositoryImpl
import com.efabreu.copa.catar.domain.repositories.MatchesRepository

@Module
interface DataModule {

    @Binds
    fun providesMatchesRepository(impl: MatchesRepositoryImpl): MatchesRepository
}
