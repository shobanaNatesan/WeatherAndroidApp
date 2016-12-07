package com.example.shobanan.wear4weather.api;

import org.apache.commons.lang3.StringUtils;



public class Main {

    public String getTemp() {

        if(StringUtils.isNotBlank(temp))
        {
            return temp + "°F";
        }
        return "N/A";
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPressure() {


        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {

        if(StringUtils.isNotEmpty(humidity)){
            return humidity+"%";
        }
        return "N/A";
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTemp_min() {

        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {

        return temp_max;

    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    private String temp;
    private String pressure;
    private String humidity;
    private String temp_min;
    private String temp_max;

}
