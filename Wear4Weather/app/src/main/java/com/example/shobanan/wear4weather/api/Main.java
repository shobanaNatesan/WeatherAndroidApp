package com.example.shobanan.wear4weather.api;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Kannan.Velusamy on 12/3/2016.
 */

public class Main {

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {



        String tempF = "";
        double F = 0;
        int K = 0;

        K = Integer.parseInt(temp);

        F = 1.8 * ( K - 273) + 32;

        tempF = Double.toString(F);

        this.temp = tempF;






    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        if(StringUtils.isNotBlank(humidity)){
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
