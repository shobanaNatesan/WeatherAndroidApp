package com.example.shobanan.wear4weather.api;

import org.apache.commons.lang3.StringUtils;



public class Main {


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



    public Double getTemp() {
        return temp;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }



    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    private Double temp;
    private String pressure;
    private String humidity;
    private Double temp_min;
    private Double temp_max;

}
