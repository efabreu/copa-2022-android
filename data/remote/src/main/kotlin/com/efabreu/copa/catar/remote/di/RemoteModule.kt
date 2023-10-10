package com.efabreu.copa.catar.remote.di

import dagger.Binds
import dagger.Module
import com.efabreu.copa.catar.data.source.MatchesDataSource
import com.efabreu.copa.catar.remote.source.MatchDataSourceRemote

@Module
interface RemoteModule {

    @Binds
    fun providesMatchDataSourceRemote(impl: MatchDataSourceRemote): MatchesDataSource.Remote
}
