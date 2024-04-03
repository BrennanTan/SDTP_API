package com.brennanUOP.SDTP.Controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SDTPTest_connectAndReturnJson {

    // Check that the function can connect to UOP API endpoint and return a JSON array
    @Test
    public void testAPIReturnJsonArrayTrue() {
        try {
            SDTPController controller = new SDTPController();
            JSONArray jsonArray = controller.connectAndReturnJson("Admissions");
            assertNotNull(jsonArray);
            System.out.println(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Check that the function returns null if anything goes wrong
    @Test
    public void testAPIReturnJsonArrayNullIfError() {
        try {
            SDTPController controller = new SDTPController();
            JSONArray jsonArray = controller.connectAndReturnJson("Test");
            assertNull(jsonArray);
            System.out.println(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Check that the function can connect to UOP API endpoint with id and return a JSON object
    @Test
    public void testAPIWithIdReturnJsonObjectTrue() {
        try {
            SDTPController controller = new SDTPController();
            JSONObject jsonObject = controller.connectAndReturnJson("Admissions", 1);
            assertNotNull(jsonObject);
            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Check that the function returns null if anything goes wrong
    @Test
    public void testAPIReturnJsonObjectNullIfError() {
        try {
            SDTPController controller = new SDTPController();
            JSONObject jsonObject = controller.connectAndReturnJson("Admissions", 10);
            assertNull(jsonObject);
            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
