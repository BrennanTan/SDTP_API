package com.brennanUOP.SDTP.Controller;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SDTPTest_connectAndReturnJson {

    // If wrong endpoint, return null
    @Test
    public void testWrongEndpointReturnsNull() {
        SDTPController sdtpController = new SDTPController();
        JSONArray result = sdtpController.connectAndReturnJson("wrongEndpoint");
        assertNull(result);
    }
    // If correct endpoint, return JsonArray
    @Test
    public void testCorrectEndpointReturnsJSONArray() {
        SDTPController sdtpController = new SDTPController();
        assertInstanceOf(JSONArray.class, sdtpController.connectAndReturnJson("Admissions"));
        JSONArray result = sdtpController.connectAndReturnJson("Admissions");
        assertNotNull(result);
        assertTrue(result.length() > 0);
        System.out.println("JSONArray: " + result.toString());
    }

}
