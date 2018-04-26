package com.slanglabs.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.slanglabs.slang.Slang;

public class MainActivity3 extends AppCompatActivity {

    private boolean mRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, MainActivity4.class);

            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View getSpeakerOverlay() {
        FloatingActionButton fab = new FloatingActionButton(this);
        fab.setSize(FloatingActionButton.SIZE_MINI);
        Toast.makeText(this, fab.getSize(), Toast.LENGTH_LONG).show();
        fab.setBackgroundResource(android.R.drawable.ic_btn_speak_now);
        return fab;
    }
}
