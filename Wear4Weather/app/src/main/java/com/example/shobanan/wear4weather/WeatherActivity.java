package com.example.shobanan.wear4weather;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shobanan.wear4weather.api.CurrentWeather;
import com.example.shobanan.wear4weather.common.ErrorUtil;
import com.example.shobanan.wear4weather.common.Util;
import com.example.shobanan.wear4weather.datamanager.WeatherDataManager;
import com.example.shobanan.wear4weather.datamanager.loadImageTask;

import android.view.View.OnKeyListener;


import org.apache.commons.lang3.StringUtils;

import java.io.IOException;


/* Weather Activity Class gets the weather for current location and zipcode using the API provided by
   http://openweathermap.org/ and binds the value to the Android user interface.
  */
public class WeatherActivity extends AppCompatActivity implements View.OnClickListener, LocationListener,  loadImageTask.Listener {

    private Context context;
    private static final String TAG = "WeatherActivity";
    private Location location;


    //Instantiating the Views
    EditText editTextzipcode = null;
    TextView textViewLocation = null;
    TextView textViweDate = null;
    TextView textViewCurrenttemp = null;
    ImageView imageicon = null;
    TextView textViewDescription = null;
    TextView textViewMintemp = null;
    TextView textViewMaxtemp = null;
    TextView textViewHumiditypercent = null;
    TextView textViewWindspeed = null;

    Button buttonSearch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatheractivity);
        context = this.getApplicationContext();
        getViewIDs();

        LocationManager locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (context.checkCallingOrSelfPermission(Util.GPS_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastKnownLocation == null) {

                Toast.makeText(WeatherActivity.this, ErrorUtil.GPS_LOCATION_NOT_AVAILABLE, Toast.LENGTH_LONG).show();

            } else {

                if ((lastKnownLocation.getLatitude() != 0.0) && (lastKnownLocation.getLongitude() != 0.0)) {
                    String coordEndpoint = Util.getCoordsEndpoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                    //REST Service Call using coordinates
                    new DownloadData().execute(coordEndpoint);
                } else {
                    Toast.makeText(WeatherActivity.this, ErrorUtil.GPS_LOCATION_NOT_AVAILABLE, Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(WeatherActivity.this, ErrorUtil.GPS_NOT_TUNRED_ON, Toast.LENGTH_LONG).show();
        }
    }

    /* Getting all the view IDs*/
    public void getViewIDs() {

        editTextzipcode = (EditText) findViewById(R.id.edtT_zipcode);
        textViewLocation = (TextView) findViewById(R.id.txt_location);
        textViweDate = (TextView) findViewById(R.id.txt_date);
        textViewCurrenttemp = (TextView) findViewById(R.id.txt_currenttemp);
        imageicon = (ImageView) findViewById(R.id.img_emojis);
        textViewDescription = (TextView) findViewById(R.id.txt_description);
        textViewMintemp = (TextView) findViewById(R.id.txt_tempmin);
        textViewMaxtemp = (TextView) findViewById(R.id.txt_tempmax);
        textViewHumiditypercent = (TextView) findViewById(R.id.txt_humiditypercent);
        textViewWindspeed = (TextView) findViewById(R.id.txt_windspeed);

        buttonSearch = (Button) findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(this);
        editTextzipcode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            getZipcode();
                            ;
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    /*This function pass the city or zip information and download data from API */
        public void getZipcode(){
            try {
                String zipcodecity = editTextzipcode.getText().toString();

                if (StringUtils.isBlank(zipcodecity)) {
                    Toast.makeText(WeatherActivity.this, ErrorUtil.ENTER_ZIP_CODE, Toast.LENGTH_LONG).show();
                } else {
                    if (zipcodecity.matches(".*\\d+.*")) {
                        if (Util.validateZipCode(zipcodecity)) {
                            String zipcodeendpoint = Util.getZipcodeEndpoint(zipcodecity);
                            //REST Service Call using zipcode
                            new DownloadData().execute(zipcodeendpoint);
                        } else if (Util.validateZipCode(zipcodecity) == false) {
                            Toast.makeText(WeatherActivity.this, ErrorUtil.ENTER_VALID_ZIP_CODE, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String cityendpoint = Util.getCityEndpoint(zipcodecity);
                        //REST Service Call using city name
                        new DownloadData().execute(cityendpoint);
                    }
                }


                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }catch(Exception ex){
                Log.e(TAG,ex.getMessage());
                Toast.makeText(WeatherActivity.this, ErrorUtil.ZIP_LOCATION_NOT_AVAILABLE, Toast.LENGTH_LONG).show();
            }

        }



    @Override
    public void onImageLoaded(Bitmap bitmap) {

        imageicon.setImageBitmap(bitmap);

    }

    @Override
    public void onError() {
        imageicon.setImageBitmap(null);

    }

    /**
     * @param objCurrentWeather
     * Set the weather information value to Android UI
     */

    public void setCurrentWeather(CurrentWeather objCurrentWeather){

        textViewLocation.setText(objCurrentWeather.getName()+","+objCurrentWeather.getSys().getCountry());
        textViweDate.setText(Util.getTodayDateInStringFormat());
        textViewCurrenttemp.setText(objCurrentWeather.getMain().getTemp());
        textViewDescription.setText(Util.camelCase(objCurrentWeather.getWeather().get(0).getDescription()));
        textViewMintemp.setText(objCurrentWeather.getMain().getTemp_min());
        textViewMaxtemp.setText(objCurrentWeather.getMain().getTemp_max());
        textViewWindspeed.setText(objCurrentWeather.getWind().getSpeed());
        textViewHumiditypercent.setText(objCurrentWeather.getMain().getHumidity());
        String icode = Util.getIcon(objCurrentWeather);
        String path = "http://openweathermap.org/img/w/" + icode + ".png";
        new loadImageTask(this).execute(path);
    }


    /**
     * On Click event trigger when pressing enter key from the text box or click event of the button.
     */
    public void onClick(View v) {
        if(v == buttonSearch){
            getZipcode();
        }
    }

    /**
     *
     * This class uses AsyncTask to load the data for the given URL.
     * The interface function will load the response on the activity.
     */


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
        protected void onPostExecute(String result)  {
            try {
                WeatherDataManager dataManager = new WeatherDataManager(context);
                setCurrentWeather(dataManager.getCurrentWeatherObject(result));
            }catch (Exception ex){
                Log.e(TAG,ex.getMessage());
                Toast.makeText(WeatherActivity.this, ErrorUtil.SERVICE_ERROR, Toast.LENGTH_LONG).show();

            }
            }
        }


}
