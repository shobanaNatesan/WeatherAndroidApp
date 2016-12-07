package com.example.shobanan.wear4weather.api;

import org.apache.commons.lang3.StringUtils;

import java.util.List;



public class CurrentWeather {

    /**
     * @return co-ordinates information (latitude & Longitude)
     */

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    /**
     * @return Weather detail weather list of information (id & main, description, icon)
     */
    public List<WeatherDetail> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherDetail> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
    /**
     * @return Weather information
     * main.temp Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     *   main.pressure Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
     *   main.humidity Humidity, %
     *   main.temp_min Minimum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     *   main.temp_max Maximum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     *   main.sea_level Atmospheric pressure on the sea level, hPa
     *   main.grnd_level Atmospheric pressure on the ground level, hPa
     */
    public Main getMain() {
        return main;
    }
    /**
     * @param main information
     *   main.temp Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     *   main.pressure Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
     *   main.humidity Humidity, %
     *   main.temp_min Minimum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     *   main.temp_max Maximum temperature at the moment. This is deviation from current temp that is possible for large cities and megalopolises geographically expanded (use these parameter optionally). Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
     *   main.sea_level Atmospheric pressure on the sea level, hPa
     *   main.grnd_level Atmospheric pressure on the ground level, hPa
     */
    public void setMain(Main main) {
        this.main = main;
    }

    /**
     * @return Wind information
     * wind.speed Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
     * wind.deg Wind direction, degrees (meteorological)
     */

    public Wind getWind() {
        return wind;
    }
    /**
     * @param wind information
     * wind.speed Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
     * wind.deg Wind direction, degrees (meteorological)
     */

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * @return cloud information
     * clouds.all Cloudiness, %
     */
    public Cloud getClouds() {
        return clouds;
    }

    /**
     * @param clouds information
     * clouds.all Cloudiness, %
     */
    public void setClouds(Cloud clouds) {
        this.clouds = clouds;
    }

    /**
     * @return rain information
     * rain.3h Rain volume for the last 3 hours
     */
    public Rain getRain() {
        return rain;
    }

    /**
     * @param rain information
     * rain.3h Rain volume for the last 3 hours
     */
    public void setRain(Rain rain) {
        this.rain = rain;
    }

    /**
     * @return co-ordinates information (latitude & Longitude)
     */
    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    /**
     * @return Country code, Sunrise & sunset information
     *  sys.country Country code (GB, JP etc.)
     *  sys.sunrise Sunrise time, unix, UTC
     *  sys.sunset Sunset time, unix, UTC
     */

    public System getSys() {
        return sys;
    }


    /**
     * @param sys information
     *  sys.country Country code (GB, JP etc.)
     *  sys.sunrise Sunrise time, unix, UTC
     *  sys.sunset Sunset time, unix, UTC
     */

    public void setSys(System sys) {
        this.sys = sys;
    }

    /**
     * @return cityID information
     */
    public String getId() {
        return id;
    }

    /**
     * @param id information
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return cityName information
     */
    public String getName() {
        if (StringUtils.isNotBlank(name)) {
            return name;
        }
        return "N/A";
    }
    /**
     * @param name information
     */

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    private Coord coord;
    private List<WeatherDetail> weather;
    private String base;
    private Main main;
    private Wind wind;
    private Cloud clouds;
    private Rain rain;
    private String dt;
    private System sys;
    private String id;
    private String name;
    private String cod;


}
