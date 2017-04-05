package com.example.listview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Activity_ListView extends AppCompatActivity {


    private SharedPreferences prefs;
    private Spinner spinner;
    private ListView my_listView;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private URL url;
    private List<BikeData> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Change title to indicate sort by
        setTitle("Sort by:");

        //listView that you will operate on
        my_listView = (ListView) findViewById(R.id.lv);


        spinner = (Spinner) findViewById(R.id.spinner);
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                }
            };
        }

        prefs.registerOnSharedPreferenceChangeListener(listener);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(20);

                if(data != null) {
                    sortList(spinner.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        setupSimpleSpinner();


        //set the listView onclick listener
        setupListViewOnClickListener();
    }

    private void sortList(String childAt) {
        switch (childAt) {
            case "Company":
                Collections.sort(data, new ComparatorCompany());
                break;
            case "Location":
                Collections.sort(data, new ComparatorLocation());
                break;
            case "Price":
                Collections.sort(data, new ComparatorPrice());
                break;
            default:
                break;
        }
        setupListView();
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
        DownloadTask down = new DownloadTask(this);

        down.execute(url.toString());
    }

    private void setupListViewOnClickListener() {
        my_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(new ContextThemeWrapper(Activity_ListView.this, R.style.AppTheme));
                BikeData temp = (BikeData) parent.getItemAtPosition(position);

                alert.setMessage(temp.toString());
                alert.setPositiveButton("OK", null);
                alert.create().show();
            }
        });

    }

    private void setupListView() {
        ListAdapter adapter = new CustomAdapter(this, data);

        my_listView.setAdapter(adapter);
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

        sortList(spinner.getItemAtPosition(0).toString());

        setupListView();
    }


    /**
     * create a data adapter to fill above spinner with choices(Company,Location and Price),
     * bind it to the spinner
     * Also create a OnItemSelectedListener for this spinner so
     * when a user clicks the spinner the list of bikes is resorted according to selection
     * dontforget to bind the listener to the spinner with setOnItemSelectedListener!
     */
    private void setupSimpleSpinner() {
        List<String> options = new ArrayList<>();

        options.add("Company");
        options.add("Location");
        options.add("Price");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
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
            case R.id.reset:
                resetListAndSpinner();
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetListAndSpinner() {
        spinner.setSelection(0);
        runDownload();
    }
}
