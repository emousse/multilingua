package com.oc.emousse.multilingua;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by emousse on 07/12/2016.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
        //On construit la notification
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.multilingua);
        builder.setContentTitle("Multilingua");
        builder.setContentText("Rappel : Vous avez rendez-vous dans 1H.");

        //On récupère le NotificationManager depuis les services systèmes
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //On demande l'affichage de la notification en précisant un identifiant pour la notification
        notificationManager.notify(1, builder.build());
    }
}
