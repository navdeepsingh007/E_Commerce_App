package com.uniongoods.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.ecommerce.R
import com.example.ecommerce.constants.GlobalConstants
import com.example.ecommerce.sharedpreference.SharedPrefClass
import com.example.ecommerce.views.home.LandingMainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {


    //*
    //* Called when message is received.
    //*
    //* @param remoteMessage Object representing the message received from Firebase Cloud Messaging.

    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        remoteMessage.notification
        Log.d("Notification--", "Received")
        sendNotification(
            ""/*data.get("Id").toString()*/,
            ""/*data.get("notificationType").toString()*/,
            ""/*data.get("message").toString()*/
        )
//notificationType
        //data.get("Id").toString()
    }


    override fun onNewToken(token: String) {
        SharedPrefClass()!!.putObject(
            applicationContext,
            GlobalConstants.NOTIFICATION_TOKEN,
            token
        )
        Log.d("token", token + "")
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }

    //* @param messageBody FCM message body received.
    private fun sendNotification(messageBody: String, notificationType: String, message: String) {

        val intent = Intent(this, LandingMainActivity::class.java)
        intent.putExtra("from", "notification")
        /* if (notificationType.equals("Job")) {
             intent = Intent(this, DashboardActivity::class.java)
         } else {
             intent = Intent(this, ServicesListActivity::class.java)
         }*/
        intent.putExtra("from", "notification")
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0/*Request code*/, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = getString(R.string.app_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0 /*ID of notification*/, notificationBuilder.build())
    }
}

//* @param messageBody FCM message body received.
/*private fun sendNotification(messageBody: String) {
    val intent = Intent(this, DashboardActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val pendingIntent = PendingIntent.getActivity(this, 0Request code, intent,
            PendingIntent.FLAG_ONE_SHOT)

    val channelId = getString(R.string.app_name)
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_app)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Since android Oreo notification channel is needed.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }
    notificationManager.notify(0 ID of notification, notificationBuilder.build())
}*/



