package com.efabreu.copa.catar.notification.scheduler.extensions

import androidx.work.Worker
import androidx.work.WorkerParameters
import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.efabreu.copa.catar.domain.model.Match
import java.time.Duration
import java.time.LocalDateTime


class NotificationWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {

        val title = inputData.getString(NOTIFICATION_TITLE_KEY)
            ?: throw IllegalArgumentException("title is required")

        val content = inputData.getString(Companion.NOTIFICATION_CONTENT_KEY)
            ?: throw IllegalArgumentException("content is required")

        context.showNotification(title, content)

        return Result.success()
    }

    companion object {
        private const val NOTIFICATION_CONTENT_KEY = "Conteudo da nitificacao"
        private const val NOTIFICATION_TITLE_KEY = "Titulo da nitificacao"
        fun start(context: Context, match: Match) {

            Log.e("WORKER", "Criado com sucesso")
            val initialDelay = Duration.between(LocalDateTime.now(), match.date).minusMinutes(30)

            var inputData = if(match.team1.displayName == "BRA"){
                workDataOf(
                    NOTIFICATION_TITLE_KEY to "É jogo do BRAZEEEEEEEEEEELLLLLLLLL!!!",
                    NOTIFICATION_CONTENT_KEY to "Hoje tem ${match.team1.flag}-zil-zil vs ${match.team2.flag}",
                )
            }else if(match.team2.displayName == "BRA"){
                workDataOf(
                    NOTIFICATION_TITLE_KEY to "É jogo do BRAZEEEEEEEEEEELLLLLLLLL!!!",
                    NOTIFICATION_CONTENT_KEY to "Hoje tem ${match.team1.flag} vs ${match.team2.flag}-zil-zil",
                )
            }else{
                workDataOf(
                    NOTIFICATION_TITLE_KEY to "Se prepare que o jogo vai começar",
                    NOTIFICATION_CONTENT_KEY to "Hoje tem ${match.team1.flag} vs ${match.team2.flag}",
                )
            }
            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    match.id,
                    ExistingWorkPolicy.KEEP,
                    createRequest(initialDelay, inputData)
                )
        }
        fun cancel(context: Context, match: Match) {
            Log.e("WORKER", "Cancelado com sucesso")
            WorkManager.getInstance(context)
                .cancelUniqueWork(match.id)
        }

        private fun createRequest(initialDelay: Duration, inputData: Data): OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(initialDelay)
                .setInputData(inputData)
                .build()
    }


}