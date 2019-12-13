package com.group3.kargobikeproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        Log.d("Notifaaa","notif recu !");
        Log.d("Notifaaa","GetNotification() == "+ remoteMessage.getNotification());
        if (remoteMessage.getNotification() != null)
        {
            Log.d("Notifaaa"," Titre :"+remoteMessage.getNotification().getTitle()+" // body "+remoteMessage.getNotification().getBody());

            //popup for notification inapp

        }

    }
}
