package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Admission;
import com.brennanUOP.SDTP.Model.Patients;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTest_F2Endpoint {
    @Test
    public void F2Endpoint_Success() throws Exception {
        HttpURLConnection mockConnection = mock(HttpURLConnection.class);
        // Mock response from the API
        String jsonResponse = "[{\"id\":1,\"admissionDate\":\"2020-11-28T16:45:00\",\"dischargeDate\":\"2020-11-28T23:56:00\",\"patientID\":2},{\"id\":2,\"admissionDate\":\"2020-12-07T22:14:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":1},{\"id\":3,\"admissionDate\":\"2021-09-23T21:50:00\",\"dischargeDate\":\"2021-09-27T09:56:00\",\"patientID\":2}]";
        InputStream responseStream = new ByteArrayInputStream(jsonResponse.getBytes());
        // Configure mock to return response
        when(mockConnection.getInputStream()).thenReturn(responseStream);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

        JSONArray jsonArray = connectAndReturnJson(mockConnection);

        SDTPController sdtpController = new SDTPController();
        List<Integer> patientsId = sdtpController.GetListAdmittedPatientsId(jsonArray);
        List<Patients> result = sdtpController.GetListAdmittedPatients(patientsId);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Robinson", result.get(0).getSurname());
        assertEquals("Viv", result.get(0).getForename());
        assertEquals("1113335555", result.get(0).getNhsNumber());
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
