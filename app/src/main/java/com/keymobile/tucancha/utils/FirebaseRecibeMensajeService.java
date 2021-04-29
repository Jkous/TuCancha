package com.keymobile.tucancha.utils;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.keymobile.tucancha.MainActivity;
import com.keymobile.tucancha.R;

public class FirebaseRecibeMensajeService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Mediante el evento data
        if(remoteMessage.getData().size() > 0) {
            mostrarNotificacion(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
        }


        // Mediante una notificacion
        if(remoteMessage.getNotification() != null) {
            mostrarNotificacion(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

    }

    private RemoteViews getCustomDesign(String title, String message){

        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),R.layout.notification_partido);
        remoteViews.setTextViewText(R.id.ivLogo, title);
        remoteViews.setTextViewText(R.id.tvTitulo, message);
        remoteViews.setImageViewResource(R.id.tvMensaje, R.drawable.common_google_signin_btn_icon_dark);

        return remoteViews;

    }



    public void mostrarNotificacion(String title, String message){

        Intent intent = new Intent(this, MainActivity.class);
        String channel_id = "web_app_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channel_id)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){

            builder = builder.setContent(getCustomDesign(title,message));

        }else{

            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.common_google_signin_btn_icon_light);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "web_app",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri,null);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        notificationManager.notify(0,builder.build());

    }

}
