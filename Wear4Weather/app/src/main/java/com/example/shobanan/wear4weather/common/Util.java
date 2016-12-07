package com.example.shobanan.wear4weather.common;

import android.app.Service;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shobanan.wear4weather.R;
import com.example.shobanan.wear4weather.WeatherActivity;
import com.example.shobanan.wear4weather.api.CurrentWeather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kannan.Velusamy on 12/4/2016.
 */

public class Util {

    public final String API_KEY = "f151b0409347e904376652e0ac01f877" ;


    public  Boolean validateZipCode(String zipcode){

        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zipcode);
        return matcher.matches();
    }
    public String getCoordsEndpoint(double latitude,double longitude){

        double dlatitude = latitude;
        String lat = Double.toString(dlatitude);

        double dlongitude = longitude;
        String lon = Double.toString(dlongitude);

        String coordEndpoint = "";

        if( lat != null && lon != null) {


            coordEndpoint = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY + "&units=imperial";
        }
        else{

            coordEndpoint = "Not Valid";

        }

        return coordEndpoint;

    }

    public String getZipcodeEndpoint(String zipcode){

        String zipcodeendpoint = "";
        zipcodeendpoint = "http://api.openweathermap.org/data/2.5/weather?zip="+zipcode+"&appid="+API_KEY+"&units=imperial";
        return zipcodeendpoint;
    }


    public String getTodayDateInStringFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, d MMM", Locale.getDefault());
        return df.format(c.getTime());
    }

    public static String camelCase(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    /* Function takes the argument UTC Date converts it to a Day, Month date.*/
    public String convertUTCtoDate(String date)
    {
        long number = Long.parseLong(date);
        Date converteddate = new Date(number * 1000);

        SimpleDateFormat formatdate = new SimpleDateFormat("EEE, MMM d");
        String returnDate = formatdate.format(converteddate);

        return returnDate;
    }

    public String getLocation(CurrentWeather objCurrentWeather){

        String location;

        if(objCurrentWeather.getName() != null && objCurrentWeather.getSys().getCountry() != null ) {

             location = objCurrentWeather.getName() + "," + objCurrentWeather.getSys().getCountry();
        }
        else{

            location = "No data";

        }

        return location;
    }

    public String getCurrentTemp(CurrentWeather objCurrentWeather){
        String currenttemp = "";

        if (objCurrentWeather.getMain().getTemp() != null) {
            currenttemp = objCurrentWeather.getMain().getTemp() + "";

        } else {
            //Toast.makeText(getApplicationContext(), "Sorry, Weather data not available. Technical error.", Toast.LENGTH_LONG).show();


        }
        return currenttemp;
    }

    public Bitmap getWeatherIcon(CurrentWeather objCurrentWeather){

        Bitmap bmWeathericon = null;

        if (objCurrentWeather.getWeather().get(0).getIcon() != null) {
            String icon = objCurrentWeather.getWeather().get(0).getIcon();
            byte[] iconbytearray = Base64.decode(icon,0);
            Bitmap bm = BitmapFactory.decodeByteArray(iconbytearray,0,iconbytearray.length);



        } else  {
            //Toast.makeText(getApplicationContext(), "Sorry, icon data not available. Technical error.", Toast.LENGTH_LONG).show();


        }

        return bmWeathericon;
    }

    public String getDescription(CurrentWeather objCurrentWeather){
        String description = "";

        if (objCurrentWeather.getWeather().get(0).getDescription() != null) {

            description = (objCurrentWeather.getWeather().get(0).getDescription());


        } else  {
            //Toast.makeText(getApplicationContext(), "Sorry, Weather data not fully available. Technical error.", Toast.LENGTH_LONG).show();


        }
        return description;
    }

    public String getMinTemp(CurrentWeather objCurrentWeather){
        String mintemp = "";
        if (objCurrentWeather.getMain().getTemp_min() != null) {
            mintemp = objCurrentWeather.getMain().getTemp_min() + "°F";


        } else {
            //Toast.makeText(getApplicationContext(), "Sorry, Minimum temperature data not available. Technical error.", Toast.LENGTH_LONG).show();

        }
        return mintemp;
    }

    public String getMaxTemp(CurrentWeather objCurrentWeather){
        String maxtemp = "";
        if (objCurrentWeather.getMain().getTemp_max() != null) {

            maxtemp = objCurrentWeather.getMain().getTemp_max() + "°F";


        } else  {
            //Toast.makeText(getApplicationContext(), "Sorry, Maximum temperature data not available. Technical error.", Toast.LENGTH_LONG).show();


        }
        return maxtemp;
    }

    public String getWindSpeed(CurrentWeather objCurrentWeather){
        String windspeed = "";

        if (objCurrentWeather.getWind().getSpeed() != null) {
            windspeed = objCurrentWeather.getWind().getSpeed() + "mph";
        } else  {
            //Toast.makeText(getApplicationContext(), "Sorry, Wind data not available. Technical error.", Toast.LENGTH_LONG).show();

        }

        return windspeed;
    }
    public String getHumidityPercent(CurrentWeather objCurrentWeather){
        String humidity = "";
        if (objCurrentWeather.getMain().getHumidity() != null) {
            humidity = objCurrentWeather.getMain().getHumidity() + "%";
        } else  {
            //Toast.makeText(getApplicationContext(), "Sorry, humidity data not available. Technical error.", Toast.LENGTH_LONG).show();
        }

        return humidity;
    }

}
