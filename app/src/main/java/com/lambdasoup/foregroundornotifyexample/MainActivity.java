package com.lambdasoup.foregroundornotifyexample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MessageResolutionService.MessageResolver {

    private final ServiceConnection messageResolutionServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MessageResolutionService.Binder binder = (MessageResolutionService.Binder) service;
            binder.registerAsCurrentForeground(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: stuff
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, MessageResolutionService.class), messageResolutionServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(messageResolutionServiceConnection);
    }


    @Override
    public void onMessage(Message message) {
        // TODO: show dialog, refresh view, launch some intent that came with the message... whatever this is about.
    }
}
