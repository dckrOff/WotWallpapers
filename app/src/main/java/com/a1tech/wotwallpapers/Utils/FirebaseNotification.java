package com.a1tech.wotwallpapers.Utils;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotification extends FirebaseMessagingService {

    public FirebaseNotification() {
        super();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(@NonNull String msgId) {
        super.onMessageSent(msgId);
    }

    @Override
    public void onSendError(@NonNull String msgId, @NonNull Exception exception) {
        super.onSendError(msgId, exception);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}
