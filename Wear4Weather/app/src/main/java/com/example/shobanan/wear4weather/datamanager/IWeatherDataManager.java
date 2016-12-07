package com.example.shobanan.wear4weather.datamanager;

import com.example.shobanan.wear4weather.api.CurrentWeather;

import java.io.IOException;



public interface IWeatherDataManager {

    public String getCurrentWeather(String endpoint) throws IOException ;
    public CurrentWeather getCurrentWeatherObject(String response);
}
