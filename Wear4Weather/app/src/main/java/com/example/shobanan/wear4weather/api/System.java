package com.example.shobanan.wear4weather.api;


import org.apache.commons.lang3.StringUtils;

public class System {

    /**
     * @return country code information
     * sys.country Country code (GB, JP etc.)
     */
    public String getCountry() {

        if(StringUtils.isNotBlank(country)){
            return country;
        }
        return "N/A";
    }

    /**
     * @param country information
     * sys.country Country code (GB, JP etc.)
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return sunrise information
     * sys.sunrise Sunrise time, unix, UTC
     */
    public String getSunrise() {
        return sunrise;
    }

    /**
     * @param sunrise information
     * sys.sunrise Sunrise time, unix, UTC
     */
    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    /**
     * @return sunset information
     * sys.sunset Sunset time, unix, UTC
     */
    public String getSunset() {
        return sunset;
    }

    /**
     * @param sunset information
     * sys.sunset Sunset time, unix, UTC
     */
    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    private String type;
    private String id;
    private String message;
    private String country;
    private String sunrise;
    private String sunset;

}
