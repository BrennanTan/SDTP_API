package com.brennanUOP.SDTP.Controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest_connectAndReturnJsonOverload {

    // If wrong endpoint, return null
    @Test
    public void testWrongEndpointReturnsNull() {
        long startTime = System.currentTimeMillis();

        SDTPController sdtpController = new SDTPController();
        JSONObject result = sdtpController.connectAndReturnJson("wrongEndpoint", "1");

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        assertNull(result);
        System.out.println("testWrongEndpointReturnsNull Execution time: " + executionTime + "ms");
    }

    // If correct endpoint but wrong id or invalid id argument, return null
    @Test
    public void testInvalidIdReturnsNull() {
        long startTime = System.currentTimeMillis();

        SDTPController sdtpController = new SDTPController();
        JSONObject result = sdtpController.connectAndReturnJson("Admissions", "1000");

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        assertNull(result);
        System.out.println("testInvalidIdReturnsNull - Execution time: " + executionTime + "ms");
    }

    // If correct endpoint and id, return JsonObject
    @Test
    public void testCorrectEndpointAndValidIdReturnsJSONObject() {
        long startTime = System.currentTimeMillis();

        SDTPController sdtpController = new SDTPController();
        assertInstanceOf(JSONObject.class, sdtpController.connectAndReturnJson("Admissions", "1"));
        JSONObject result = sdtpController.connectAndReturnJson("Admissions", "1");

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        assertNotNull(result);
        System.out.println("testCorrectEndpointAndValidIdReturnsJSONObject - Execution time: " + executionTime + "ms");
        System.out.println("JSONObject: " + result);
    }

}
