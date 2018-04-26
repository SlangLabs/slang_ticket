package com.slanglabs.sampleapp;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

/**
 * TODO: Add a class header comment!
 */

public class FilterDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity activity;
    public Dialog d;


    public FilterDialog(Activity a) {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_layout);
        findViewById(R.id.goair).setOnClickListener(this);
        findViewById(R.id.jet).setOnClickListener(this);
        findViewById(R.id.indigo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jet:
                ((ListActivity) activity).showFlights(null, "Jet", null, null, Integer.MAX_VALUE, false);
                break;
            case R.id.indigo:
                ((ListActivity) activity).showFlights(null, "Indigo", null, null, Integer.MAX_VALUE, false);
                break;
            default:
                ((ListActivity) activity).showFlights(null, "GoAir", null, null, Integer.MAX_VALUE, false);
                break;
        }
        dismiss();
    }
}