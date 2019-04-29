package com.example.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class MainActivity extends AppCompatActivity {
    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    /**
     * Run when this activity is no longer visible.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     *
     * @param month
     * @param day
     */
    void startAPICall(final String month, final String day, final String category) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    NewsEvent.urlString(month, day),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            apiCallDone(response, category);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                }
            });
            jsonObjectRequest.setShouldCache(false);
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void apiCallDone(final JSONObject response, final String category) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(response.toString()).getAsJsonObject();

            TextView textView1 = findViewById(R.id.textView1);
            textView1.setText("Year: " + jsonObject.get("data").getAsJsonObject().get(category).getAsJsonArray().get(0).getAsJsonObject().
                    get("year").getAsString() + "  " + jsonObject.get("data").getAsJsonObject().get(category).getAsJsonArray().get(0).getAsJsonObject().
                    get("text").getAsString());

            TextView textView2 = findViewById(R.id.textView2);
            textView2.setText("Year: " + jsonObject.get("data").getAsJsonObject().get(category).getAsJsonArray().get(1).getAsJsonObject().
                    get("year").getAsString() + "  " + jsonObject.get("data").getAsJsonObject().get(category).getAsJsonArray().get(1).getAsJsonObject().
                    get("text").getAsString());

            TextView textView3 = findViewById(R.id.textView3);
            textView3.setText("Year: " + jsonObject.get("data").getAsJsonObject().get(category).getAsJsonArray().get(2).getAsJsonObject().
                    get("year").getAsString() + "  " + jsonObject.get("data").getAsJsonObject().get(category).getAsJsonArray().get(2).getAsJsonObject().
                    get("text").getAsString());

            TextView textView4 = findViewById(R.id.textView4);
            textView4.setText("Year: " + jsonObject.get("data").getAsJsonObject().get(category).getAsJsonArray().get(3).getAsJsonObject().
                    get("year").getAsString() + "  " + jsonObject.get("data").getAsJsonObject().get(category).getAsJsonArray().get(3).getAsJsonObject().
                    get("text").getAsString());

            TextView textView5 = findViewById(R.id.textView5);
            textView5.setText("Year: " + jsonObject.get("data").getAsJsonObject().get(category).getAsJsonArray().get(4).getAsJsonObject().
                    get("year").getAsString() + "  " + jsonObject.get("data").getAsJsonObject().get(category).getAsJsonArray().get(4).getAsJsonObject().
                    get("text").getAsString());



        } catch (Exception ignored) {

        }
    }

    /**
     * indicate which category is selected
     */
    private boolean eventChosen;
    private boolean birthChosen;
    private boolean deathChosen;

    @SuppressWarnings("deprecation")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the queue for our API requests
        requestQueue = Volley.newRequestQueue(this);

        //set activity_main on current screen
        setContentView(R.layout.activity_main);


        final DatePicker simpleDatePicker = findViewById(R.id.simpleDatePicker); // initiate a date picker
        simpleDatePicker.setCalendarViewShown(false); // set false value for the calendar shown function



        Button event = findViewById(R.id.majorEvent);
        Button birth = findViewById(R.id.births);
        Button death = findViewById(R.id.deaths);
        Button search = findViewById(R.id.search);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String month = Integer.toString(simpleDatePicker.getMonth() + 1); // month starts with 0
                String day = Integer.toString(simpleDatePicker.getDayOfMonth());

                if ((eventChosen == false) && (birthChosen == false) && (deathChosen == false)) {
                    TextView text = findViewById(R.id.title);
                    text.setText("   Please Select a category");
                }
                if (eventChosen == true) {
                    TextView text = findViewById(R.id.title);
                    text.setText("   Events on " + month + "/" + day);
                    startAPICall(month, day, "Events");
                }
                if (birthChosen == true) {
                    TextView text = findViewById(R.id.title);
                    text.setText("   Births on " + month + "/" + day);
                    startAPICall(month, day, "Births");
                }
                if (deathChosen == true) {
                    TextView text = findViewById(R.id.title);
                    text.setText("   Deaths on " + month + "/" + day);
                    startAPICall(month, day, "Deaths");
                }

            }
        });

        event.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                eventChosen = true;
                birthChosen = false;
                deathChosen = false;
            }
        });

        birth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                eventChosen = false;
                birthChosen = true;
                deathChosen = false;
            }
        });

        death.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                eventChosen =false;
                birthChosen = false;
                deathChosen = true;
            }
        });


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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}