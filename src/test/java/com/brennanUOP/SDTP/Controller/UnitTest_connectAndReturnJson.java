package com.brennanUOP.SDTP.Controller;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTest_connectAndReturnJson {

    // If wrong endpoint, return null
    @Test
    public void testWrongEndpointReturnsNull() {
        long startTime = System.currentTimeMillis();

        SDTPController sdtpController = new SDTPController();
        JSONArray result = sdtpController.connectAndReturnJson("wrongEndpoint");

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        assertNull(result);
        System.out.println("testWrongEndpointReturnsNull - Execution time: " + executionTime + "ms");
    }

    // If correct endpoint, return JsonArray
    @Test
    public void testCorrectEndpointReturnsJSONArray() {
        long startTime = System.currentTimeMillis();

        SDTPController sdtpController = new SDTPController();
        JSONArray result = sdtpController.connectAndReturnJson("Admissions");

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        assertNotNull(result);
        assertTrue(result.length() > 0);
        System.out.println("testCorrectEndpointReturnsJSONArray - Execution time: " + executionTime + "ms");
        System.out.println("JSONArray: " + result);
    }

}
