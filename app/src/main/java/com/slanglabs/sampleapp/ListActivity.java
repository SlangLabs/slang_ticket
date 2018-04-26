package com.slanglabs.sampleapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.slanglabs.slang.Slang;
import com.slanglabs.slang.action.SlangIntent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<Flight> allFlights = null;
    Date mDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);

        findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FilterDialog((Activity) view.getContext()).show();
            }
        });

        Intent intent = getIntent();

        String dateStr = intent.getStringExtra(VoiceInterface.ENTITY_TRAVEL_DATE) != null
            ? intent.getStringExtra(VoiceInterface.ENTITY_TRAVEL_DATE)
            : null;
        String source_city = intent.getStringExtra(VoiceInterface.ENTITY_SOURCE_CITY) != null
            ? intent.getStringExtra(VoiceInterface.ENTITY_SOURCE_CITY)
            : null;
        String destination_city = intent.getStringExtra(VoiceInterface.ENTITY_DESTINATION_CITY) != null
            ? intent.getStringExtra(VoiceInterface.ENTITY_DESTINATION_CITY)
            : null;
        String airline = intent.getStringExtra("airline") != null
            ? intent.getStringExtra("airline")
            : null;
        String sortOnPrice = intent.getStringExtra("sortOnPrice") != null
            ? intent.getStringExtra("sortOnPrice")
            : "false";

        if (dateStr == null || source_city == null || destination_city == null) {
            new AlertDialog.Builder(this).setMessage("Date/Source City/Destination city not set!!").show();

            return;
        }

        try {
            mDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (Exception e) {
            mDate = new Date();
        }

        // Show all flights
        allFlights = getFlightsForDate(mDate, source_city, destination_city);

        showFlights(mDate, airline, source_city, destination_city, Integer.MAX_VALUE, sortOnPrice.equals("true"));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public List<Flight> showFlights(
        Date date,
        String airline,
        String source,
        String destination,
        int max,
        boolean sortOnStart
    ) {
        List<Flight> retFlights = new ArrayList<>();

        if (allFlights == null) {
            allFlights = getFlightsForDate(date, source, destination);
        }

        // First restrict to name
        for (Flight flight:allFlights) {
            // Ignore flights whose name does not match
            if (airline != null && !airline.equals(flight.airline)) {
                continue;
            }

            // Ignore flights whose city does not match
            if (source != null && !source.equals(flight.source)) {
                continue;
            }

            // Ignore flights whose city does not match
            if (destination != null && !destination.equals(flight.destination)) {
                continue;
            }

            // Ignore flights which is above max
            if (flight.price > max) {
                continue;
            }

            retFlights.add(flight);
        }

        if (sortOnStart) {
            Collections.sort(retFlights);
        }

        LinearLayout layout = findViewById(R.id.list);
        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layout.removeAllViews();

        for (Flight flight : retFlights) {
            View row = vi.inflate(R.layout.item_layout, null);

            String message = String.format(
                "%s  %s %s  %s %d",
                flight.airline,
                flight.source,
                flight.destination,
                new SimpleDateFormat("EEE, MMM d, HH:mm").format(flight.start),
                flight.price
            );
            ((TextView) row.findViewById(R.id.item_text)).setText(message);
            layout.addView(row);
        }

        return retFlights;
    }

    private static class Flight implements Comparable<Flight> {
        public String airline;
        public String source;
        public String destination;
        public int price;
        public Date start;

        public Flight(String airline, String source, String destination, int price, Date start) {
            this.airline = airline;
            this.source = source;
            this.destination = destination;
            this.price = price;
            this.start = start;
        }

        @Override
        public int compareTo(@NonNull Flight flight) {
            if (start.getTime() > flight.start.getTime()) {
                return 1;
            } else if (start.getTime() < flight.start.getTime()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private List<Flight> getFlightsForDate(Date date, String source, String destination) {
        List<Flight> flights = new ArrayList<>();
        long HOUR = 60 * 60 * 1000;

        try {
            flights.add(new Flight("GoAir", source, destination, 800, new Date(date.getTime() + 0 * HOUR)));
            flights.add(new Flight("GoAir", source, destination, 900, new Date(date.getTime() + 0 * HOUR)));
            flights.add(new Flight("GoAir", source, destination, 1200, new Date(date.getTime() + 0 * HOUR)));
            flights.add(new Flight("GoAir", source, destination, 1500, new Date(date.getTime() + 1 * HOUR)));
            flights.add(new Flight("GoAir", source, destination, 2000, new Date(date.getTime() + 2 * HOUR)));

            flights.add(new Flight("Indigo", source, destination,1000, new Date(date.getTime() + 0 * HOUR)));
            flights.add(new Flight("Indigo", source, destination,1200, new Date(date.getTime() + 0 * HOUR)));
            flights.add(new Flight("Indigo", source, destination,1200, new Date(date.getTime() + 1 * HOUR)));
            flights.add(new Flight("Indigo", source, destination,2500, new Date(date.getTime() + 2 * HOUR)));
            flights.add(new Flight("Indigo", source, destination,3500, new Date(date.getTime() + 3 * HOUR)));

            flights.add(new Flight("Jet", source, destination,1000, new Date(date.getTime() + 0 * HOUR)));
            flights.add(new Flight("Jet", source, destination,1000, new Date(date.getTime() + 0 * HOUR)));
            flights.add(new Flight("Jet", source, destination,1200, new Date(date.getTime() + 0 * HOUR)));
            flights.add(new Flight("Jet", source, destination,1200, new Date(date.getTime() + 1 * HOUR)));
            flights.add(new Flight("Jet", source, destination,2500, new Date(date.getTime() + 2 * HOUR)));

            flights.add(new Flight("Spice", source, destination,1000, new Date(date.getTime() + 0 * HOUR)));
            flights.add(new Flight("Spice", source, destination,1200, new Date(date.getTime() + 0 * HOUR)));
            flights.add(new Flight("Spice", source, destination,1200, new Date(date.getTime() + 1 * HOUR)));
            flights.add(new Flight("Spice", source, destination,2500, new Date(date.getTime() + 2 * HOUR)));
            flights.add(new Flight("Spice", source, destination,4000, new Date(date.getTime() + 3 * HOUR)));
        } catch (Exception e) {
            // ignore
        }

        return flights;
    }

    @Override
    protected void onPause() {
        super.onPause();
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
            Intent intent = new Intent(this, MainActivity3.class);

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
