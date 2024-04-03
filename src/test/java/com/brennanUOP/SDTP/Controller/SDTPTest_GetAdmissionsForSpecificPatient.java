package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Admission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SDTPTest_GetAdmissionsForSpecificPatient {

    @Mock
    private URL url;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAdmissionsForSpecificPatient() throws IOException {
        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(url.openConnection()).thenReturn(connection);

        // Mocking the response
        String mockResponse = "[{\"id\":1,\"admissionDate\":\"2024-04-01\",\"dischargeDate\":\"2024-04-03\",\"patientID\":1}]";
        InputStream inputStream = new ByteArrayInputStream(mockResponse.getBytes());
        when(connection.getInputStream()).thenReturn(inputStream);

        SDTPController controller = new SDTPController();
        List<Admission> admissions = controller.GetAdmissionsForSpecificPatient("2");

        // Check if the correct admission is returned
        assertEquals(1, admissions.size());
        Admission admission = admissions.get(0);
        assertEquals(2, admission.getId());
        assertEquals("2020-12-07T22:14:00", admission.getAdmissionDate());
        assertEquals("0001-01-01T00:00:00", admission.getDischargeDate());
        assertEquals(1, admission.getPatientID());
    }
}