package com.efabreu.copa.catar.domain.usecase

import com.efabreu.copa.catar.domain.model.Match
import com.efabreu.copa.catar.domain.repositories.MatchesRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetMatchesUseCase @Inject constructor(private val matchesRepository: MatchesRepository) {
    suspend fun execute() : Flow<List<Match>> {
        return matchesRepository.getMatches()
    }

}