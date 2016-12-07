package com.example.shobanan.wear4weather.datamanager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.shobanan.wear4weather.WeatherActivity;
import com.example.shobanan.wear4weather.api.CurrentWeather;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherDataManager implements IWeatherDataManager {

    protected Context context;
    CurrentWeather objCurrentWeather = null;


    public  WeatherDataManager (Context context){
        this.context = context;
    }

    @Override
    public String getCurrentWeather(String endpoint) throws IOException {
        return getRestCall(endpoint);
    }



    @Override
    public CurrentWeather getCurrentWeatherObject(String response) {

        Gson objRespose = new Gson();
        objCurrentWeather = new CurrentWeather();

        objCurrentWeather = objRespose.fromJson(response,CurrentWeather.class);

        return objCurrentWeather;
    }

    private String getRestCall(String endpoint) throws IOException {

            InputStream is = null;

            try {
                URL url = new URL(endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 );
                conn.setConnectTimeout(15000 );
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("Http Coonection:", "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = getStringfromInputStream(is);
                return contentAsString;

            }
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
            finally {
                if (is != null) {
                    is.close();
                }
            }
        }
    // Convert the InputStream into a string
        public String getStringfromInputStream(InputStream stream) throws IOException, UnsupportedEncodingException {
            BufferedReader reader = null;
            StringBuilder sb = new StringBuilder();
            String line;
            try{
                reader = new BufferedReader(new InputStreamReader(stream));
                while((line = reader.readLine()) != null){
                    sb.append(line);
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }finally {
                if(reader != null){
                    reader.close();
                }
            }

            return sb.toString().trim();
        }



}
