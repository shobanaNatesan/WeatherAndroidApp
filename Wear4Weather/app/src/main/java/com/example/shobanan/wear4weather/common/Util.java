package com.example.shobanan.wear4weather.common;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.shobanan.wear4weather.api.CurrentWeather;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *  Utility class maintains all reusable functions.
 *  */

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
     * Given latitude and longitude ,will return coordendpoit.
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
     * Given Zipcode,will return zipodeendpiont
     * @param zipcode
     * @return String
     */
    public static String getZipcodeEndpoint(@NonNull String zipcode){
        String zipcodeendpoint = "http://api.openweathermap.org/data/2.5/weather?zip="+zipcode+"&appid="+API_KEY+"&units=imperial";
        return zipcodeendpoint;
    }

    /**
     * Given city,will return zipodeendpiont
     * @param city
     * @return String
     */
    public static String getCityEndpoint(@NonNull String city){
        String cityendpoint = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+API_KEY+"&units=imperial";
        return cityendpoint;
    }


    /**
     *
     * @return String with date format as Day, date Month (Web, 7 Dec)
     */
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


    /**
     * @param original
     * @return String - convert the given string to camelCase sentence
     */
    public static String camelCase(@NonNull String original) {
        if (original.length() == 0) {
            return "";
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    /**
     * @param objCurrentWeather
     * @return string icon. Validates for null exception and returns the icon ID.
     */
    public static String getIcon(CurrentWeather objCurrentWeather){
        String icon = "";
        if (objCurrentWeather.getWeather().get(0).getIcon() != null) {
            icon = objCurrentWeather.getWeather().get(0).getIcon();
        } else  {
           icon = "N/A";
        }
        return icon;
    }

}
