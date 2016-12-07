package com.example.shobanan.wear4weather;

import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;


/* Weather Activity Class gets the weather for current location and zipcode using the API provided by
   http://openweathermap.org/ and binds the value to the Android user interface.
  */
public class WeatherActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context;
    private static final String TAG = "WeatherActivity";


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
    ImageView iMagewear = null;
    Button buttonSearch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatheractivity);
        context = this.getApplicationContext();
        getViewIDs();

        LocationManager locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if(context.checkCallingOrSelfPermission(Util.GPS_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (lastKnownLocation == null) {

                Toast.makeText(WeatherActivity.this, ErrorUtil.GPS_LOCATION_NOT_AVAILABLE, Toast.LENGTH_LONG).show();

            } else {

                if ((lastKnownLocation.getLatitude() != 0.0) && (lastKnownLocation.getLongitude() != 0.0)) {
                    String coordEndpoint = Util.getCoordsEndpoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                    //REST Service Call
                    new DownloadData().execute(coordEndpoint);
                } else {
                    Toast.makeText(WeatherActivity.this, ErrorUtil.GPS_LOCATION_NOT_AVAILABLE, Toast.LENGTH_LONG).show();
                }
            }
        }
        else {
                Toast.makeText(WeatherActivity.this,ErrorUtil.GPS_NOT_TUNRED_ON,Toast.LENGTH_LONG).show();
            }
        }

    /* Getting all the view IDs*/
    public void getViewIDs(){

        editTextzipcode = (EditText)findViewById(R.id.edtT_zipcode);
        textViewLocation = (TextView) findViewById(R.id.txt_location);
        textViweDate = (TextView) findViewById(R.id.txt_date);
        textViewCurrenttemp = (TextView)findViewById(R.id.txt_currenttemp);
        imageicon = (ImageView)findViewById(R.id.img_emojis);
        textViewDescription = (TextView)findViewById(R.id.txt_description);
        textViewMintemp = (TextView)findViewById(R.id.txt_tempmin);
        textViewMaxtemp = (TextView)findViewById(R.id.txt_tempmax);
        textViewHumiditypercent = (TextView)findViewById(R.id.txt_humiditypercent);
        textViewWindspeed = (TextView)findViewById(R.id.txt_windspeed);
        iMagewear = (ImageView)findViewById(R.id.img_wear);
        buttonSearch = (Button)findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(this);

    }
    /* */
    public void getZipcode(){
        try {
            String zipcode = editTextzipcode.getText().toString();
            if (StringUtils.isBlank(zipcode)) {
                Toast.makeText(WeatherActivity.this, ErrorUtil.ENTER_ZIP_CODE, Toast.LENGTH_LONG).show();
            } else {
                if (Util.validateZipCode(zipcode)) {
                    String zipcodeendpoint = Util.getZipcodeEndpoint(zipcode);
                    new DownloadData().execute(zipcodeendpoint);
                } else if (Util.validateZipCode(zipcode) == false) {
                    Toast.makeText(WeatherActivity.this, ErrorUtil.ENTER_VALID_ZIP_CODE, Toast.LENGTH_LONG).show();
                }

            }
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);}
        }catch(Exception ex){
            Log.e(TAG,ex.getMessage());
            Toast.makeText(WeatherActivity.this, ErrorUtil.ZIP_LOCATION_NOT_AVAILABLE, Toast.LENGTH_LONG).show();
        }

    }

    public void setCurrentWeather(CurrentWeather objCurrentWeather){

        textViewLocation.setText(Util.getLocation(objCurrentWeather));
        textViweDate.setText(Util.getTodayDateInStringFormat());
        textViewCurrenttemp.setText(Util.getCurrentTemp(objCurrentWeather));
        imageicon.setImageBitmap(Util.getWeatherIcon(objCurrentWeather));
        textViewDescription.setText(Util.getDescription(objCurrentWeather));
        textViewMintemp.setText(Util.getMinTemp(objCurrentWeather));
        textViewMaxtemp.setText(Util.getMaxTemp(objCurrentWeather));
        textViewWindspeed.setText(Util.getWindSpeed(objCurrentWeather));
        textViewHumiditypercent.setText(objCurrentWeather.getMain().getHumidity());
    }

    public void onClick(View v) {
            if(v == buttonSearch){
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
