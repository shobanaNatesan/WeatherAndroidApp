package com.example.shobanan.wear4weather.common;

import android.app.Service;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
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

public class Util {

    public static final String API_KEY = "f151b0409347e904376652e0ac01f877" ;
    private static final String TAG = "UtilClass";
    public static final String GPS_PERMISSION = "android.permission.ACCESS_COARSE_LOCATION";

    /**
     * zipCode should be 6 digit numeric value else will return false.
     * @param zipcode
     * @return Boolean
     */
    public static  Boolean validateZipCode(@NonNull String zipcode){
        boolean result = false;
        try {
            String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(zipcode);
            result = matcher.matches();
        }catch (Exception ex){
            Log.e(TAG,ex.getMessage());
            return false;
        }
        return result;
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @return String
     */
    public static String getCoordsEndpoint(double latitude,double longitude){
        String coordEndpoint = "";
        try {
            coordEndpoint = "http://api.openweathermap.org/data/2.5/weather?lat=" + Double.toString(latitude) + "&lon=" + Double.toString(longitude) + "&appid=" + API_KEY + "&units=imperial";
        }catch(Exception ex){
            Log.e(TAG,ex.getMessage());
            return coordEndpoint;
        }

        return coordEndpoint;

    }

    /**
     *
     * @param zipcode
     * @return String
     */
    public static String getZipcodeEndpoint(@NonNull String zipcode){
        String zipcodeendpoint = "http://api.openweathermap.org/data/2.5/weather?zip="+zipcode+"&appid="+API_KEY+"&units=imperial";
        return zipcodeendpoint;
    }


    public static String getTodayDateInStringFormat(){
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("E, d MMM", Locale.getDefault());
            return df.format(c.getTime());
        }catch(Exception ex){
            Log.e(TAG,ex.getMessage());
            return "";
        }

    }

    public static String camelCase(@NonNull String original) {
        if (original.length() == 0) {
            return "";
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }


    public static String getLocation(@NonNull CurrentWeather objCurrentWeather){

        String location;

        if(objCurrentWeather.getName() != null && objCurrentWeather.getSys().getCountry() != null ) {

             location = objCurrentWeather.getName() + "," + objCurrentWeather.getSys().getCountry();
        }
        else{

            location = "No data";

        }

        return location;
    }

    public static String getCurrentTemp(CurrentWeather objCurrentWeather){
        String currenttemp = "";

        if (objCurrentWeather.getMain().getTemp() != null) {
            currenttemp = objCurrentWeather.getMain().getTemp() + "";

        } else {
            //Toast.makeText(getApplicationContext(), "Sorry, Weather data not available. Technical error.", Toast.LENGTH_LONG).show();


        }
        return currenttemp;
    }

    public static Bitmap getWeatherIcon(CurrentWeather objCurrentWeather){

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

    public static String getDescription(CurrentWeather objCurrentWeather){
        String description = "";

        if (objCurrentWeather.getWeather().get(0).getDescription() != null) {

            description = (objCurrentWeather.getWeather().get(0).getDescription());


        } else  {
            //Toast.makeText(getApplicationContext(), "Sorry, Weather data not fully available. Technical error.", Toast.LENGTH_LONG).show();


        }
        return description;
    }

    public static String getMinTemp(CurrentWeather objCurrentWeather){
        String mintemp = "";
        if (objCurrentWeather.getMain().getTemp_min() != null) {
            mintemp = objCurrentWeather.getMain().getTemp_min() + "°F";


        } else {
            //Toast.makeText(getApplicationContext(), "Sorry, Minimum temperature data not available. Technical error.", Toast.LENGTH_LONG).show();

        }
        return mintemp;
    }

    public static String getMaxTemp(CurrentWeather objCurrentWeather){
        String maxtemp = "";
        if (objCurrentWeather.getMain().getTemp_max() != null) {

            maxtemp = objCurrentWeather.getMain().getTemp_max() + "°F";


        } else  {
            //Toast.makeText(getApplicationContext(), "Sorry, Maximum temperature data not available. Technical error.", Toast.LENGTH_LONG).show();


        }
        return maxtemp;
    }

    public static String getWindSpeed(CurrentWeather objCurrentWeather){
        String windspeed = "";

        if (objCurrentWeather.getWind().getSpeed() != null) {
            windspeed = objCurrentWeather.getWind().getSpeed() + "mph";
        } else  {
            //Toast.makeText(getApplicationContext(), "Sorry, Wind data not available. Technical error.", Toast.LENGTH_LONG).show();

        }

        return windspeed;
    }
    public static String getHumidityPercent(CurrentWeather objCurrentWeather){
        String humidity = "";
        if (objCurrentWeather.getMain().getHumidity() != null) {
            humidity = objCurrentWeather.getMain().getHumidity() + "%";
        } else  {
            //Toast.makeText(getApplicationContext(), "Sorry, humidity data not available. Technical error.", Toast.LENGTH_LONG).show();
        }
        return humidity;
    }

}
