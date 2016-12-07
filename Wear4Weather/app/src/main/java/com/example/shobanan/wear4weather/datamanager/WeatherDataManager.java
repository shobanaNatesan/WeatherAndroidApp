package com.example.shobanan.wear4weather.datamanager;

import android.content.Context;
import android.util.Log;
import com.example.shobanan.wear4weather.api.CurrentWeather;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherDataManager implements IWeatherDataManager {

    private Context context;
    private static final String TAG = "WeatherDataManager";


    public  WeatherDataManager (Context context){
        this.context = context;
    }



    @Override
    public String getCurrentWeather(String endpoint) throws IOException {
        return getRestCall(endpoint);
    }

    /**
     *Function getCurrentWeather will take string
     * and convert it to Json using Gson library
     * @param response,
     * @return objCurrentWeather as Json
     */

    @Override
    public CurrentWeather getCurrentWeatherObject(String response) {
        CurrentWeather objCurrentWeather = new CurrentWeather();
        try {
            Gson objRespose = new Gson();
            objCurrentWeather = objRespose.fromJson(response, CurrentWeather.class);
        }catch(Exception ex){
            throw ex;
        }
        return objCurrentWeather;
    }


    /**
     *Function getRestCall will take the URL and connect to the API.
     * @param endpoint, url for API to get the information
     * @return response as string
     */
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
                Log.d(TAG, "The response is: " + response);
                is = conn.getInputStream();
                // Convert the InputStream into a string
                String contentAsString = getStringfromInputStream(is);
                return contentAsString;

            }
            //  InputStream is closed after the app is finished using

            finally {
                if (is != null) {
                    is.close();
                }
            }
        }
    // Convert the InputStream into a string
        public String getStringfromInputStream(InputStream stream) throws IOException {
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
                return "";
            }finally {
                if(reader != null){
                    reader.close();
                }
            }

            return sb.toString().trim();
        }



}
