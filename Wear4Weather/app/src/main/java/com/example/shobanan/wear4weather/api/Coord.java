package com.example.shobanan.wear4weather.api;



public class Coord {

    /**
     * @return longitude information
     */
    public String getLon() {
        return lon;
    }

    /**
     * @param lon to set (in percentage)
     */
    public void setLon(String lon) {
        this.lon = lon;
    }

    /**
     * @return latitude information
     */
    public String getLat() {
        return lat;
    }

    /**
     * @param lat to set (in percentage)
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    private String lon;
    private String lat;




}
