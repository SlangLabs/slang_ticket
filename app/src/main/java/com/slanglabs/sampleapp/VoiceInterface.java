package com.slanglabs.sampleapp;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.slanglabs.slang.Slang;
import com.slanglabs.slang.action.SlangAction;
import com.slanglabs.slang.action.SlangIntent;

/**
 * TODO: Add a class header comment!
 */

public class VoiceInterface {
    public static final String ENTITY_SOURCE_CITY = "source";
    public static final String ENTITY_DESTINATION_CITY = "destination";
    public static final String ENTITY_TRAVEL_DATE = "travel_date";
    public static final String VOICE_TRIGGERED = "voice_triggered";

    public static void init(Application appContext) {
        Slang
            .init(appContext)
            .appId(R.string.appId)
            .authKey(R.string.authKey);

        registerActions();
    }

    private static void registerActions() {
        Slang
            .action()
            .register("search", new SlangAction.ActionCallback() {
                @Override
                public boolean onEntityProcessing(@NonNull Activity activity, SlangIntent intent) {
                    if (!intent.getEntity(ENTITY_SOURCE_CITY).isSet()) {
                        // If source is not set, set it to the default
                        intent.setEntityValue(ENTITY_SOURCE_CITY, getDefaultCity());
                    }
                    return true;
                }

                @Override
                public boolean onIntentDetected(
                    @NonNull Activity activity,
                    SlangIntent intent,
                    SlangAction.IntentProgressListener listener
                ) {
                    String source = intent.getEntity(ENTITY_SOURCE_CITY).getValue();
                    String destination = intent.getEntity(ENTITY_DESTINATION_CITY).getValue();
                    String date = intent.getEntity(ENTITY_TRAVEL_DATE).getValue();

                    Intent i = new Intent(activity, ListActivity.class);
                    i.putExtra(ENTITY_SOURCE_CITY, source);
                    i.putExtra(ENTITY_DESTINATION_CITY, destination);
                    i.putExtra(ENTITY_TRAVEL_DATE, date);
                    i.putExtra(VOICE_TRIGGERED, true);
                    activity.startActivity(i);
                    listener.intentCompleted(intent, "Showing flights to - " + destination, true, "Which flight would you like to book?");
                    return false;
                }
            });
    }

    private static String getDefaultCity() {
        // TODO: Get the city closest to current location, which has an airport
        return "Bangalore";
    }
}
