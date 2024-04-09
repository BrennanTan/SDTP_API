package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Admission;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class IntegrationTest_F1Endpoint {

    @Mock
    private HttpURLConnection mockHttpURLConnection;

    // Create a spy for the controller
    @Spy
    @InjectMocks
    private SDTPController controllerSpy; // Use the spy

    @Test
    public void getAdmissionsForSpecificPatient_withExistingAdmissions_returnsAdmissionsList() {
        String patientId = "1";
        String admissionsJson = "[{\"id\":1,\"admissionDate\":\"2020-11-28T16:45:00\",\"dischargeDate\":\"2020-11-28T23:56:00\",\"patientID\":2}," +
                "{\"id\":2,\"admissionDate\":\"2020-12-07T22:14:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":1}," +
                "{\"id\":3,\"admissionDate\":\"2021-09-23T21:50:00\",\"dischargeDate\":\"2021-09-27T09:56:00\",\"patientID\":2}]";

        // Mock the behavior of connectAndReturnJson on the controller spy
        try {
            JSONArray jsonArray = new JSONArray(admissionsJson);
            Mockito.doReturn(jsonArray).when(controllerSpy).connectAndReturnJson("Admissions"); // Use Mockito.doReturn for spies
        } catch (JSONException e) {
            throw new RuntimeException("Error creating mock JSON response", e);
        }

        // Call the endpoint using the controller spy
        List<Admission> response = controllerSpy.GetAdmissionsForSpecificPatientEndpoint(patientId);

        System.out.println(response.size());
        for (int i = 0; i < response.size(); i++){
            System.out.println(response.get(i).getId());
            System.out.println(response.get(i).getAdmissionDate());
            System.out.println(response.get(i).getDischargeDate());
            System.out.println(response.get(i).getPatientID());
        }

        assertEquals(1, response.size());
        assertEquals(2, response.get(0).getId());
        assertEquals("2020-12-07T22:14:00", response.get(0).getAdmissionDate());
        assertEquals("0001-01-01T00:00:00", response.get(0).getDischargeDate());
        assertEquals(1, response.get(0).getPatientID());    }
}
