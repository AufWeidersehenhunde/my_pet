package com.example.experimentation

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import android.os.IBinder
import androidx.core.content.getSystemService
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationService : Service() {


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun showNotification(context: FragmentActivity) {
        // Создаем канал уведомлений
        createNotificationChannel(context)

        // Создаем уведомление
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("My Notification")
            .setContentText("This is a notification from my app.")
            .setSmallIcon(R.drawable.btn_star_big_off)
            .setAutoCancel(true)



        // Запускаем уведомление
        val managerNot = context.getSystemService<NotificationManager>()?: return

        managerNot.notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Default kek",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = context.getSystemService<NotificationManager>()?:null
            manager?.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "def"
    }
}