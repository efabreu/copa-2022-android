package com.efabreu.copa.catar.domain.usecase

import com.efabreu.copa.catar.domain.repositories.MatchesRepository
import javax.inject.Inject

class EnableNotificationUseCase @Inject constructor(private val matchesRepository: MatchesRepository) {

    suspend fun execute(id :String){
        matchesRepository.enableNotificationFor(id)
    }

}