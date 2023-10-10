package com.efabreu.copa.catar.domain.repositories

import kotlinx.coroutines.flow.Flow
import com.efabreu.copa.catar.domain.model.Match

interface MatchesRepository {
    suspend fun getMatches(): Flow<List<Match>>
    suspend fun enableNotificationFor(id: String)
    suspend fun disableNotificationFor(id: String)
}
