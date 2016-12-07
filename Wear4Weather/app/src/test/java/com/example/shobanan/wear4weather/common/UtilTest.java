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
    @Test
    public void testInValidZipCode() throws IOException{
        String actual = Util.getZipcodeEndpoint("98abcd");
        Assert.assertNotNull("Invalid zipcode",actual);
        Assert.assertEquals("Endpoint is not matching","http://api.openweathermap.org/data/2.5/weather?zip=98abcd&appid=f151b0409347e904376652e0ac01f877&units=imperial",actual);
    }

    @Test
    public void testValidateZipCode() throws IOException{

        Boolean actual = Util.validateZipCode("");
        Assert.assertEquals("Empty Zipcode",false,actual);
        Boolean actualvalid = Util.validateZipCode("97006");
        Boolean actualinvalid = Util.validateZipCode("tyudi");
        Assert.assertEquals("Valid Zipcode",true,actualvalid);
        Assert.assertEquals("Invalid Zipcode",false,actualinvalid);
    }


}
