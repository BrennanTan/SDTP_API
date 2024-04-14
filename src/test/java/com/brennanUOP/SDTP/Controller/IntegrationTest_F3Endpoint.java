package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Employees;
import com.brennanUOP.SDTP.Model.Patients;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTest_F3Endpoint {
    @Test
    public void F3Endpoint_Success() throws Exception {
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        // Mock response from the API
        String jsonResponse = "[{\"id\":1,\"admissionID\":1,\"employeeID\":4,\"startTime\":\"2020-11-28T16:45:00\",\"endTime\":\"2020-11-28T23:56:00\"},{\"id\":2,\"admissionID\":3,\"employeeID\":4,\"startTime\":\"2021-09-23T21:50:00\",\"endTime\":\"2021-09-24T09:50:00\"},{\"id\":3,\"admissionID\":2,\"employeeID\":6,\"startTime\":\"2020-12-07T22:14:00\",\"endTime\":\"2020-12-08T20:00:00\"},{\"id\":4,\"admissionID\":2,\"employeeID\":3}]";
        InputStream responseStream = new ByteArrayInputStream(jsonResponse.getBytes());
        // Configure mock to return response
        when(mockConnection.getInputStream()).thenReturn(responseStream);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

        JSONArray jsonArray = connectAndReturnJson(mockConnection);

        SDTPController sdtpController = new SDTPController();
        HashMap<Integer, Integer> employeeAdmissionsCount = sdtpController.GetEmployeeAdmissionsCount(jsonArray);
        List<Employees> result = sdtpController.GetMostAdmissionsStaff(employeeAdmissionsCount);

        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getId());
        assertEquals("Jones", result.get(0).getSurname());
        assertEquals("Sarah", result.get(0).getForename());
    }
    public JSONArray connectAndReturnJson(HttpURLConnection connection) {
        // Use the mock connection and return it's mock response
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String text;
            while ((text = reader.readLine()) != null){
                response.append(text);
            }
            reader.close();

            return new JSONArray(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
