package com.lambdasoup.foregroundornotifyexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by jl on 03.08.16.
 */
public class MessageResolutionService extends Service {
    private static final String EXTRA_MESSAGE = "com.lambdasoup.foregroundornotifyexample.MESSAGE";
    private static final int NOTIFICATION_MESSAGE = 0;

    private final Binder binder = new Binder();
    private MessageResolver currentForegroundResolver = null;
    private boolean isBound = false;

    public static Intent getMessageResolutionIntent(Context context, Message message) {
        Intent intent = new Intent(context, MessageResolutionService.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        return intent;
    }

    @Override
    public IBinder onBind(Intent intent) {
        isBound = true;
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        isBound = true;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isBound = false;
        return true; // we want onRebind called
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!intent.hasExtra(EXTRA_MESSAGE)) {
            throw new IllegalArgumentException("Required extra " + EXTRA_MESSAGE + " missing.");
        }
        Message message = intent.getParcelableExtra(EXTRA_MESSAGE);
        if (isBound && currentForegroundResolver != null) {
            handleMessageInForeground(message, startId);
        } else {
            handleMessageInBackground(message, startId);
        }
        return START_REDELIVER_INTENT;
    }

    private void handleMessageInForeground(Message message, int startId) {
        currentForegroundResolver.onMessage(message);
        stopSelfResult(startId);
    }

    private void handleMessageInBackground(Message message, int startId) {

        Notification notification = new Notification.Builder(this)
                // TODO: construct notification here
                .build();

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(NOTIFICATION_MESSAGE, notification);
        stopSelfResult(startId);
    }


    public interface MessageResolver {
        void onMessage(Message message);
    }

    public class Binder extends android.os.Binder {
        public void registerAsCurrentForeground(MessageResolver messageResolver) {
            MessageResolutionService.this.currentForegroundResolver = messageResolver;
        }
    }
}
