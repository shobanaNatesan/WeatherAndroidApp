package com.example.shobanan.wear4weather.api;


import org.apache.commons.lang3.StringUtils;

public class Wind {

    public String getSpeed() {

        if(StringUtils.isNotBlank(speed)){
            return speed + "mph";
        }
        return "N/A";
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    private String speed;
    private String deg;

}
