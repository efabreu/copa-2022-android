package com.efabreu.copa.catar.remote.source

import com.efabreu.copa.catar.data.source.MatchesDataSource
import com.efabreu.copa.catar.domain.model.MatchDomain
import com.efabreu.copa.catar.remote.extensions.getOrThrowDomainError
import com.efabreu.copa.catar.remote.mapper.toDomain
import com.efabreu.copa.catar.remote.services.MatchesServices
import javax.inject.Inject

class MatchDataSourceRemote @Inject constructor(
    private val service: MatchesServices
) : MatchesDataSource.Remote {

    override suspend fun getMatches(): List<MatchDomain> {
        return runCatching {
            service.getMatches()
        }.getOrThrowDomainError().toDomain()
    }
}
