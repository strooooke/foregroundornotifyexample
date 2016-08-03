package com.lambdasoup.foregroundornotifyexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by jl on 03.08.16.
 */
public class SomeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("myapp.CORRECT_ACTION".equals(intent.getAction())) {
            context.startService(MessageResolutionService.getMessageResolutionIntent(context, (Message) intent.getParcelableExtra("myapp.EXTRA_MESSAGE")));
        }
    }
}
