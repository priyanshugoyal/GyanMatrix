package com.example.priyanshu.gyanmatrix;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BatsmanAdapter madapter;
    private RecyclerView recyclerView;
    private TextView mEmptyStateTextView;
    View loadingIndicator;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private List<Batsman> batsmanlist = new ArrayList<>();
    public static final String Log_TAG = MainActivity.class.getName();
    private static final String BatsmanUrl = "http://hackerearth.0x10.info/api/gyanmatrix?type=json&query=list_player";

    private void sendRequest() {

        StringRequest stringRequest = new StringRequest(BatsmanUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loadingIndicator.setVisibility(View.GONE);
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json) {
        try {

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            JSONObject baseJsonResponse = new JSONObject(json);


            JSONArray batsmanArray = baseJsonResponse.getJSONArray("records");

            if (batsmanArray.length() > 0) {
                for (int i = 0; i < batsmanArray.length(); i++) {

                    JSONObject currentbatsman = batsmanArray.getJSONObject(i);


                    String batsmanname = currentbatsman.getString("name");
                    String imageurl = currentbatsman.getString("image");
                    int runs = currentbatsman.getInt("total_score");
                    String discription = currentbatsman.getString("description");
                    int matches = currentbatsman.getInt("matches_played");
                    String country = currentbatsman.getString("country");
                    Batsman batsman = new Batsman(batsmanname, imageurl, discription, country, runs, matches);

                    batsmanlist.add(batsman);

                }
            }
            madapter.notifyDataSetChanged();

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        madapter = new BatsmanAdapter(batsmanlist, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadingIndicator = findViewById(R.id.loading_indicator);

        recyclerView.setAdapter(madapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Intent i = new Intent(MainActivity.this, Player.class);
                        startActivity(i);


                    }
                })
        );
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            sendRequest();
            recyclerView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setVisibility(View.GONE);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible

            // Update empty state with no connection error message
            loadingIndicator.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText("no_internet_connection");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int item_id = item.getItemId();

        switch (item_id) {
            case R.id.az: {
                Collections.sort(batsmanlist, new Comparator<Batsman>(){
                    public int compare(Batsman s1, Batsman s2) {
                        return s1.getBatsmanname().compareToIgnoreCase(s2.getBatsmanname());
                    }
                });
                madapter.notifyDataSetChanged();
                return true;
            }

            case R.id.matches: {
                //layout.setBackgroundColor(Color.BLUE);
                Collections.sort(batsmanlist, new Comparator<Batsman>(){
                    public int compare(Batsman s1, Batsman s2) {
                        return  Integer.valueOf(s1.getplayed()).compareTo(s2.getplayed());
                    }
                });
                Collections.reverse(batsmanlist);
                madapter.notifyDataSetChanged();

                return true;
            }

            case R.id.runs: {
               // layout.setBackgroundColor(Color.GREEN);
                Collections.sort(batsmanlist, new Comparator<Batsman>(){
                    public int compare(Batsman s1, Batsman s2) {
                        return  Integer.valueOf(s1.getruns()).compareTo(s2.getruns());

                    }
                });
                Collections.reverse(batsmanlist);
                madapter.notifyDataSetChanged();
                return true;
            }


        }
        return super.onOptionsItemSelected(item);


    }
}
