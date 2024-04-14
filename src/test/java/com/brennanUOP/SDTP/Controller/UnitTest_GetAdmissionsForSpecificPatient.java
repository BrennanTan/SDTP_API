package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Admission;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest_GetAdmissionsForSpecificPatient {

    // Test for GetAdmissionsForSpecificPatient method
    @Test
    public void testGetAdmissionsForSpecificPatient() {
        long startTime = System.currentTimeMillis();

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(
                    "[{\"id\":1,\"admissionDate\":\"2020-11-28T16:45:00\",\"dischargeDate\":\"2020-11-28T23:56:00\",\"patientID\":2}," +
                            "{\"id\":2,\"admissionDate\":\"2020-12-07T22:14:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":1}," +
                            "{\"id\":3,\"admissionDate\":\"2021-09-23T21:50:00\",\"dischargeDate\":\"2021-09-27T09:56:00\",\"patientID\":2}]"
            );

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String patientId = "1";
        SDTPController controller = new SDTPController();
        List<Admission> result = controller.GetAdmissionsForSpecificPatient(jsonArray, patientId);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getId());
        assertEquals("2020-12-07T22:14:00", result.get(0).getAdmissionDate());
        assertEquals("0001-01-01T00:00:00", result.get(0).getDischargeDate());
        assertEquals(1, result.get(0).getPatientID());

        for (Admission admission : result) {
            System.out.println(admission.getId());
            System.out.println(admission.getAdmissionDate());
            System.out.println(admission.getDischargeDate());
            System.out.println(admission.getPatientID());
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetAdmissionsForSpecificPatient - Execution time: " + executionTime + "ms");
    }

    // Test for GetAdmissionsForSpecificPatient method with no matching patientId
    @Test
    public void testGetAdmissionsForSpecificPatientWithNoMatchingPatientId() {
        long startTime = System.currentTimeMillis();

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(
                    "[{\"id\":1,\"admissionDate\":\"2020-11-28T16:45:00\",\"dischargeDate\":\"2020-11-28T23:56:00\",\"patientID\":2}," +
                            "{\"id\":2,\"admissionDate\":\"2020-12-07T22:14:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":1}," +
                            "{\"id\":3,\"admissionDate\":\"2021-09-23T21:50:00\",\"dischargeDate\":\"2021-09-27T09:56:00\",\"patientID\":2}]"
            );
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String patientId = "1000";
        SDTPController controller = new SDTPController();

        List<Admission> result = controller.GetAdmissionsForSpecificPatient(jsonArray, patientId);

        assertNull(result);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetAdmissionsForSpecificPatientWithNoMatchingPatientId - Execution time: " + executionTime + "ms");
    }

    // Test for GetAdmissionsForSpecificPatient method with empty JSONArray
    @Test
    public void testGetAdmissionsForSpecificPatientWithEmptyJsonArray() {
        long startTime = System.currentTimeMillis();

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray("[]");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String patientId = "1";
        SDTPController controller = new SDTPController();

        List<Admission> result = controller.GetAdmissionsForSpecificPatient(jsonArray, patientId);

        assertNull(result);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetAdmissionsForSpecificPatientWithEmptyJsonArray - Execution time: " + executionTime + "ms");
    }

}
