package com.example.shobanan.wear4weather.common;



import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class UtilTest {
    @Test
    public void testValidZipCode() throws IOException {
        String actual = Util.getZipcodeEndpoint("97006");
        Assert.assertNotNull("getCurrentWeather is NULL",actual);
        Assert.assertEquals("endpoint is not matching","http://api.openweathermap.org/data/2.5/weather?zip=97006&appid=f151b0409347e904376652e0ac01f877&units=imperial",actual);
    }
}
