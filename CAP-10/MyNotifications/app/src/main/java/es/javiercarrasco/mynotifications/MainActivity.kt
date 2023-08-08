package es.javiercarrasco.mynotifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import es.javiercarrasco.mynotifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val CHANNELID1 = "es.javiercarrasco.mynotifications"
    private val notificationId1 = 1

    // Se crea una notificación utilizando el builder de NotificationCompat.
    private val builder = NotificationCompat.Builder(this, CHANNELID1).apply {
        setSmallIcon(R.drawable.notificacion)
        setContentTitle("Mi primera notificación")
        setContentText("Esta será la primera notificación creada.")
        priority = NotificationCompat.PRIORITY_DEFAULT
    }

    val requestPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission is granted.
            } else {
                // Se daría una explicación al usuario sobre las consecuencias de denegar el permiso.
            }
        }

    private fun createNotificationChannel(
        channel: String, name: Int, desc: Int, importance: Int
    ) {
        // Se crea el canal de notificación únicamente para API 26+.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mName = getString(name)
            val descriptionText = getString(desc)
            val mChannel = NotificationChannel(channel, mName, importance)
            mChannel.description = descriptionText

            // Se registra el canal en el sistema.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            // Se registra el canal de notificación en el sistema.
            createNotificationChannel(
                CHANNELID1,
                R.string.txt_channel1_name,
                R.string.txt_channel1_desc,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            // Se crea el intent que deberá abrirse al pulsarse la notificación.
            val intent = Intent(this, RequestNotification::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(this, notificationId1, intent, PendingIntent.FLAG_IMMUTABLE)

            builder.setContentIntent(pendingIntent)
            builder.setAutoCancel(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (PermissionHandler(this).checkPermission(Manifest.permission.POST_NOTIFICATIONS)) {
                    with(NotificationManagerCompat.from(this)) {
                        notify(notificationId1, builder.build())
                    }
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                with(NotificationManagerCompat.from(this)) {
                    notify(notificationId1, builder.build())
                }
            }
        }
    }
}