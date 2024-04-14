package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Admission;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IntegrationTest_F1Endpoint {
    @Test
    public void F1Endpoint_Success() throws Exception {
        long startTime = System.currentTimeMillis();
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        // Mock response from the API
        String jsonResponse = "[{\"id\":1,\"admissionDate\":\"2020-11-28T16:45:00\",\"dischargeDate\":\"2020-11-28T23:56:00\",\"patientID\":2},{\"id\":2,\"admissionDate\":\"2020-12-07T22:14:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":1},{\"id\":3,\"admissionDate\":\"2021-09-23T21:50:00\",\"dischargeDate\":\"2021-09-27T09:56:00\",\"patientID\":2}]";
        InputStream responseStream = new ByteArrayInputStream(jsonResponse.getBytes());
        // Configure mock to return response
        when(mockConnection.getInputStream()).thenReturn(responseStream);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

        JSONArray jsonArray = connectAndReturnJson(mockConnection);

        SDTPController sdtpController = new SDTPController();
        List<Admission> result = sdtpController.GetAdmissionsForSpecificPatient(jsonArray, "1");

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getId());
        assertEquals("2020-12-07T22:14:00", result.get(0).getAdmissionDate());
        assertEquals("0001-01-01T00:00:00", result.get(0).getDischargeDate());
        assertEquals(1, result.get(0).getPatientID());

        for (Admission admission : result){
            System.out.println(admission.getId());
            System.out.println(admission.getAdmissionDate());
            System.out.println(admission.getDischargeDate());
            System.out.println(admission.getPatientID());
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("F1Endpoint_Success - Execution time: " + executionTime + "ms");
    }

    public JSONArray connectAndReturnJson(HttpURLConnection connection) {
        // Use the mock connection and return its mock response
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String text;
            while ((text = reader.readLine()) != null) {
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
