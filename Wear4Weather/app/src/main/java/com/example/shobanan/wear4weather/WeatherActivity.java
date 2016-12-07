package com.example.shobanan.wear4weather;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.shobanan.wear4weather.api.CurrentWeather;
import com.example.shobanan.wear4weather.datamanager.WeatherDataManager;
import com.example.shobanan.wear4weather.common.Util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/* Weather Activity Class gets the weather for current location and zipcode using the API provided by
   http://openweathermap.org/ and binds the value to the Android user interface.
  */
public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{

    protected Context context;

    static final String API_KEY = "b3c788493142dec185a8b6cd4fb17ff9";
    static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?";

    //Instantiating the Views
    EditText ETzipcode = null;
    ImageView IMsearch = null;
    TextView TVlocation = null;
    TextView TVdate = null;
    TextView TVcurrenttemp = null;
    ImageView IMicon = null;
    TextView TVdescription = null;
    TextView TVmintemp = null;
    TextView TVmaxtemp = null;
    TextView TVhumiditypercent = null;
    TextView TVwindspeed = null;
    ImageView IMwear = null;
    Button BTsearch = null;





    Util objUtil = new Util();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatheractivity);

        context = this.getApplicationContext();
        getViewIDs();

        LocationManager locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        String gpsPermission = "android.permission.ACCESS_COARSE_LOCATION";
        int res = context.checkCallingOrSelfPermission(gpsPermission);
        if(res == PackageManager.PERMISSION_GRANTED) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastKnownLocation == null) {

                Toast.makeText(WeatherActivity.this, "GPS not available", Toast.LENGTH_LONG).show();

            } else {

                if ((lastKnownLocation.getLatitude() != 0.0) && (lastKnownLocation.getLongitude() != 0.0)) {

                    String coordEndpoint = "";
                    coordEndpoint = objUtil.getCoordsEndpoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                    new DownloadData().execute(coordEndpoint);
                } else {

                    Toast.makeText(WeatherActivity.this, "could not get the GPS location", Toast.LENGTH_LONG).show();

                }
            }
        }

            else {

                Toast.makeText(WeatherActivity.this,"Sorry, could not get the GPS location",Toast.LENGTH_LONG).show();

            }
        }




    /* Getting all the view IDs*/
    public void getViewIDs(){

        ETzipcode = (EditText)findViewById(R.id.edtT_zipcode);
        TVlocation = (TextView) findViewById(R.id.txt_location);
        TVdate = (TextView) findViewById(R.id.txt_date);
        TVcurrenttemp = (TextView)findViewById(R.id.txt_currenttemp);
        IMicon = (ImageView)findViewById(R.id.img_emojis);
        TVdescription = (TextView)findViewById(R.id.txt_description);
        TVmintemp = (TextView)findViewById(R.id.txt_tempmin);
        TVmaxtemp = (TextView)findViewById(R.id.txt_tempmax);
        TVhumiditypercent = (TextView)findViewById(R.id.txt_humiditypercent);
        TVwindspeed = (TextView)findViewById(R.id.txt_windspeed);
        IMwear = (ImageView)findViewById(R.id.img_wear);
        BTsearch = (Button)findViewById(R.id.btn_search);
        BTsearch.setOnClickListener(this);

    }
    /* */
    public void getZipcode(){

        String zipcode = "";
        zipcode = ETzipcode.getText().toString();
        if(zipcode == null || zipcode.isEmpty() ){

            Toast.makeText(WeatherActivity.this, "Enter Zipcode", Toast.LENGTH_LONG).show();
        }
        else {
            if (objUtil.validateZipCode(zipcode) == true) {
                String zipcodeendpoint = objUtil.getZipcodeEndpoint(zipcode);
                new DownloadData().execute(zipcodeendpoint);
            } else if(objUtil.validateZipCode(zipcode) == false) {

                Toast.makeText(WeatherActivity.this, "Enter a valid Zipcode", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void setCurrentWeather(CurrentWeather objCurrentWeather){

        TVlocation.setText(objUtil.getLocation(objCurrentWeather));
        TVdate.setText(objUtil.getTodayDateInStringFormat());
        TVcurrenttemp.setText(objUtil.getCurrentTemp(objCurrentWeather));
        IMicon.setImageBitmap(objUtil.getWeatherIcon(objCurrentWeather));
        TVdescription.setText(objUtil.getDescription(objCurrentWeather));
        TVmintemp.setText(objUtil.getMinTemp(objCurrentWeather));
        TVmaxtemp.setText(objUtil.getMaxTemp(objCurrentWeather));
        TVwindspeed.setText(objUtil.getWindSpeed(objCurrentWeather));
        TVhumiditypercent.setText(objUtil.getHumidityPercent(objCurrentWeather));


//        if (objCurrentWeather.getName() != null && objCurrentWeather.getSys().getCountry() != null) {
//            TVlocation.setText(objCurrentWeather.getName()+","+ objCurrentWeather.getSys().getCountry());
//
//        } else  {
//            Toast.makeText(getApplicationContext(), "Sorry, Location data not available. Technical error.", Toast.LENGTH_LONG).show();
//
//        }


//        if (objCurrentWeather.getWeather().get(0).getDescription() != null) {
//
//            TVdescription.setText(objCurrentWeather.getWeather().get(0).getDescription());
//
//        } else  {
//            Toast.makeText(getApplicationContext(), "Sorry, Weather data not fully available. Technical error.", Toast.LENGTH_LONG).show();
//
//
//        }
//
//        if (objCurrentWeather.getWind().getSpeed() != null) {
//            TVwindspeed.setText(objCurrentWeather.getWind().getSpeed());
//
//        } else  {
//            Toast.makeText(getApplicationContext(), "Sorry, Wind data not available. Technical error.", Toast.LENGTH_LONG).show();
//
//        }

//        if (objCurrentWeather.getWeather().get(0).getIcon() != null) {
//            String icon = objCurrentWeather.getWeather().get(0).getIcon();
//            byte[] iconbytearray = Base64.decode(icon,0);
//            Bitmap bm = BitmapFactory.decodeByteArray(iconbytearray,0,iconbytearray.length);
//            IMicon.setImageBitmap(bm);
//
//
//
//        } else  {
//            Toast.makeText(getApplicationContext(), "Sorry, icon data not available. Technical error.", Toast.LENGTH_LONG).show();
//
//
//        }

//        if (objCurrentWeather.getMain().getHumidity() != null) {
//            TVhumiditypercent.setText(objCurrentWeather.getMain().getHumidity());
//
//        } else  {
//            Toast.makeText(getApplicationContext(), "Sorry, humidity data not available. Technical error.", Toast.LENGTH_LONG).show();
//
//
//
//        }

//        if (objCurrentWeather.getMain().getTemp_min() != null) {
//           TVmintemp.setText(objCurrentWeather.getMain().getTemp_min());
//
//        } else {
//            Toast.makeText(getApplicationContext(), "Sorry, Minimum temperature data not available. Technical error.", Toast.LENGTH_LONG).show();
//
//        }

//        if (objCurrentWeather.getMain().getTemp_max() != null) {
//           TVmaxtemp.setText(objCurrentWeather.getMain().getTemp_max());
//
//        } else  {
//            Toast.makeText(getApplicationContext(), "Sorry, Maximum temperature data not available. Technical error.", Toast.LENGTH_LONG).show();
//
//
//        }
    }

    public void onClick(View v) {

            if(v == BTsearch){
                getZipcode();
            }

    }


    private class DownloadData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... endpoints) {

            try {

                WeatherDataManager dataManager = new WeatherDataManager(context);
                return dataManager.getCurrentWeather(endpoints[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            WeatherDataManager dataManager = new WeatherDataManager(context);
            setCurrentWeather(dataManager.getCurrentWeatherObject(result));
            }
        }


}
