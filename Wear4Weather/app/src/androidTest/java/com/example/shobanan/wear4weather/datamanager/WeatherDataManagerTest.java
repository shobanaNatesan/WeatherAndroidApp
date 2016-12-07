package com.example.shobanan.wear4weather.datamanager;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(AndroidJUnit4.class)
public class WeatherDataManagerTest {

    Context appContext;
    WeatherDataManager dataManager;

    @Before
    public void init() throws Exception{
        appContext = InstrumentationRegistry.getTargetContext();
        dataManager = new WeatherDataManager(appContext);
    }

    @Test
    public void useAppContext() throws Exception {
        assertEquals("com.example.shobanan.wear4weather", appContext.getPackageName());
    }

    @Test
    public void getCurrentWeatherValidEndPointTest() throws Exception {
        String validEndPoint = "http://api.openweathermap.org/data/2.5/weather?zip=97006&appid=f151b0409347e904376652e0ac01f877&units=imperial";
        String response = dataManager.getCurrentWeather(validEndPoint);
        assertNotNull("response is NULL",response);
    }
}
