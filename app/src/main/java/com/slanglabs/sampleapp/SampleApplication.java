package com.slanglabs.sampleapp;

import android.app.Application;

import com.slanglabs.slang.Slang;

/**
 * TODO: Add a class header comment!
 */

public final class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Create a local Client object
        VoiceInterface.init(this);
    }
}
