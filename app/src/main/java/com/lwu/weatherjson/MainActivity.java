package com.lwu.weatherjson;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText edtCity;
    //private TextView txvCity;
    private RequestQueue mQueue;
    private Button button_parse;
    private TextView txvTemp;
   // private TextView txwind;
  //  private TextView txForecast;
    private TextView txvDate;
    private TextView txvyahoo;
    private ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        txvDate= findViewById(R.id.txvDate);
        txvTemp= findViewById(R.id.txvTemp);
        edtCity=findViewById(R.id.edtCity);
        txvyahoo = findViewById(R.id.txvyahoo);
        img = findViewById(R.id.img);

        button_parse=findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

           button_parse.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   getWeatherInfo();
               }
           });

    }


    private void getWeatherInfo() {

        String location= edtCity.getText().toString();


        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + location + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //JSONArray jsonArray = response.getJSONArray("name");

                            String temp = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("temp");
                            String code = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("code");
                            String forecast = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("text");
                            String title = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getString("description");
                            String windy = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("wind").getString("chill");
                            String date = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getString("lastBuildDate");


                              int code1= Integer.parseInt(code);
                                 if(code1==32) {
                                     img.setImageResource(R.drawable.im1);
                                 }else if(code1==47)
                                 {
                                       img.setImageResource(R.drawable.fseven);
                                 }else if(code1==30)
                                 {
                                     img.setImageResource(R.drawable.thirty);

                                 }else if(code1==28)
                                 {
                                     img.setImageResource(R.drawable.tweeight);

                                 }else if(code1==12)
                                 {
                                     img.setImageResource(R.drawable.twelve);

                                 }else if(code1==34)
                                 {
                                        img.setImageResource(R.drawable.thfour);
                                 }
                                    else if(code1==4)
                                 {
                                        img.setImageResource(R.drawable.four);
                                 }
                                    else if(code1==23)
                                 {
                                        img.setImageResource(R.drawable.twothree);
                                 }
                                    else if(code1==26)
                                 {
                                        img.setImageResource(R.drawable.twosix);
                                 }



                            String jsonResponse3="";
                            String jsonResponse4="";
                            String jsonResponse5="";




                            jsonResponse3+= title+"\n";
                            jsonResponse4+= date+"\n";
                            jsonResponse5+= "Temp is " +temp + "\n"+ forecast+"\n" + "chill"+ windy ;

                            txvTemp.setText(jsonResponse5);
                            txvyahoo.setText(jsonResponse3);
                            txvDate.setText(jsonResponse4);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}