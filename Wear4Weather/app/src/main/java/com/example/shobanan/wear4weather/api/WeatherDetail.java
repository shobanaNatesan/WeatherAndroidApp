package com.example.shobanan.wear4weather.api;


import org.apache.commons.lang3.StringUtils;

public class WeatherDetail {

    private String id;

    private String main;

    private String description;

    private String icon;

    /**
     *
     *  weather (more info Weather condition codes)
     *  weather.id Weather condition id
     *  weather.main Group of weather parameters (Rain, Snow, Extreme etc.)
     *  weather.description Weather condition within the group
     *  weather.icon Weather icon id */

    public WeatherDetail(String icon, String description, String main, String id) {
        this.icon = icon;
        this.description = description;
        this.main = main;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        if (StringUtils.isNotBlank(description)) {
            return description;
        }
        return "N/A";
    }

    public String getIcon() {
        return icon;
    }
}
