package com.example.listview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Activity_ListView extends AppCompatActivity {


    private SharedPreferences prefs;
    Spinner spinner;
    ListView my_listview;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private URL url;
    private DownloadTask down;
    private List<BikeData> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Change title to indicate sort by
        setTitle("Sort by:");

        //listview that you will operate on
        my_listview = (ListView) findViewById(R.id.lv);
        spinner = (Spinner) findViewById(R.id.spinner);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        String temp = prefs.getString("url", null);

        if (checkConnections()) {
            //Toast.makeText(this, "Connection is good", Toast.LENGTH_SHORT).show();
            try {
                if (temp != null) {
                    url = new URL(prefs.getString("url", getString(R.string.error)));
                } else {
                    url = new URL("http://www.tetonsoftware.com/bikes/bikes.json");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            runDownload();

            listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    try {
                        url = new URL(prefs.getString("url", getString(R.string.error)));
                        runDownload();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Site changed to: " + url.toString(), Toast.LENGTH_SHORT).show();
                }
            };
        }

        prefs.registerOnSharedPreferenceChangeListener(listener);


        setupSimpleSpinner();

        //set the listview onclick listener
        setupListViewOnClickListener();

        //TODO call a thread to get the JSON list of bikes
        //TODO when it returns it should process this data with bindData
    }

    private boolean checkConnections() {
        if (ConnectivityCheck.isNetworkReachable(this) || ConnectivityCheck.isWifiReachable(this)) {
            return true;
        } else {
            ConnectivityCheck.isNetworkReachableAlertUserIfNot(this);
            return false;
        }
    }

    private void runDownload() {
        down = new DownloadTask(this);

        down.execute(url.toString());
    }

    private void setupListViewOnClickListener() {
        //TODO you want to call my_listviews setOnItemClickListener with a new instance of android.widget.AdapterView.OnItemClickListener() {
    }

    /**
     * Takes the string of bikes, parses it using JSONHelper
     * Sets the adapter with this list using a custom row layout and an instance of the CustomAdapter
     * binds the adapter to the Listview using setAdapter
     *
     * @param JSONString complete string of all bikes
     */
    public void bindData(String JSONString) {
        data = JSONHelper.parseAll(JSONString);

        Log.d("Bikes", data.toString());
    }


    /**
     * create a data adapter to fill above spinner with choices(Company,Location and Price),
     * bind it to the spinner
     * Also create a OnItemSelectedListener for this spinner so
     * when a user clicks the spinner the list of bikes is resorted according to selection
     * dontforget to bind the listener to the spinner with setOnItemSelectedListener!
     */
    private void setupSimpleSpinner() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent myIntent = new Intent(this, activityPreference.class);
                startActivity(myIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
