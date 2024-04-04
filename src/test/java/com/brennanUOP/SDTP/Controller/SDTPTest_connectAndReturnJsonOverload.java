package com.brennanUOP.SDTP.Controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SDTPTest_connectAndReturnJsonOverload {
    // If wrong endpoint, return null
    @Test
    public void testWrongEndpointReturnsNull() {
        SDTPController sdtpController = new SDTPController();
        JSONObject result = sdtpController.connectAndReturnJson("wrongEndpoint", "1");
        assertNull(result);
    }
    // If correct endpoint but wrong id or invalid id argument, return null
    @Test
    public void testInvalidIdReturnsNull() {
        SDTPController sdtpController = new SDTPController();
        JSONObject result = sdtpController.connectAndReturnJson("Admissions", "1000");
        assertNull(result);
    }
    // If correct endpoint and id, return JsonObject
    @Test
    public void testCorrectEndpointAndValidIdReturnsJSONObject() {
        SDTPController sdtpController = new SDTPController();
        assertInstanceOf(JSONObject.class, sdtpController.connectAndReturnJson("Admissions", "1"));
        JSONObject result = sdtpController.connectAndReturnJson("Admissions", "1");
        assertNotNull(result);
        System.out.println("JSONObject: " + result);
    }

}