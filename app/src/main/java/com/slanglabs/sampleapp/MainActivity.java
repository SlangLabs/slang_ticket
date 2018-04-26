package com.slanglabs.sampleapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        findViewById(R.id.sample_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ListActivity.class);

                String source = ((EditText) findViewById(R.id.source)).getText().toString();
                String destination = ((EditText) findViewById(R.id.destination)).getText().toString();
                Date date = new Date(((CalendarView) findViewById(R.id.travel_date)).getDate());

                if (source.length() == 0 || destination.length() == 0 || date == null) {
                    new AlertDialog.Builder(view.getContext()).setMessage("Date/Source City/Destination city not set!!").show();
                    return;
                }

                intent.putExtra(VoiceInterface.ENTITY_SOURCE_CITY, source);
                intent.putExtra(VoiceInterface.ENTITY_DESTINATION_CITY, destination);
                intent.putExtra(VoiceInterface.ENTITY_TRAVEL_DATE, new SimpleDateFormat("yyyy-MM-dd").format(date));

                startActivity(intent);
            }
        });
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
            Intent intent = new Intent(this, ListActivity.class);

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
