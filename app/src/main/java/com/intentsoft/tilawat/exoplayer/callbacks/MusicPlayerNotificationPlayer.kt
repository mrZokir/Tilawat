package com.intentsoft.tilawat.exoplayer.callbacks

import android.app.Notification
import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.intentsoft.tilawat.data.other.Contstants.NOTIFICATION_ID
import com.intentsoft.tilawat.exoplayer.MusicService

/**
 * @author user
 * @date 29.09.2021
 */
class MusicPlayerNotificationPlayer(
    private val musicService: MusicService
): PlayerNotificationManager.NotificationListener {

    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        musicService.apply {
            stopForeground(true)
            isForegroundService = false
            stopSelf()
        }
    }

    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        super.onNotificationPosted(notificationId, notification, ongoing)

        musicService.apply {
            if (ongoing && isForegroundService) {
                ContextCompat.startForegroundService(
                    this,
                    Intent(applicationContext, this::class.java)
                )
                startForeground(NOTIFICATION_ID, notification) // starts notification player
                isForegroundService = true
            }
        }
    }
}