package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Patients;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest_GetListAdmittedPatients {

    // Test for GetListAdmittedPatientsId method
    @Test
    public void testGetListAdmittedPatientsId() {
        long startTime = System.currentTimeMillis();

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(
                    "[{\"id\":1,\"admissionDate\":\"2020-11-28T16:45:00\",\"dischargeDate\":\"2020-11-28T23:56:00\",\"patientID\":2}," +
                            "{\"id\":2,\"admissionDate\":\"2020-12-07T22:14:00\",\"dischargeDate\":\"0001-01-01T00:00:00\",\"patientID\":1}," +
                            "{\"id\":3,\"admissionDate\":\"2021-09-23T21:50:00\",\"dischargeDate\":\"2021-09-27T09:56:00\",\"patientID\":2}]"
            );
            SDTPController controller = new SDTPController();
            List<Integer> result = controller.GetListAdmittedPatientsId(jsonArray);

            assertEquals(1, result.size());
            assertEquals(1, result.get(0));

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("Result: " + result);
            System.out.println("testGetListAdmittedPatientsId - Execution time: " + executionTime + "ms");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // Test for GetListAdmittedPatientsId method with empty JSONArray
    @Test
    public void testGetListAdmittedPatientsIdEmptyJsonArray() {
        long startTime = System.currentTimeMillis();

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray("[]");
            SDTPController controller = new SDTPController();
            List<Integer> result = controller.GetListAdmittedPatientsId(jsonArray);
            assertNull(result);
            System.out.println(result);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetListAdmittedPatientsIdEmptyJsonArray - Execution time: " + executionTime + "ms");
    }

    // Test for GetListAdmittedPatients method
    @Test
    public void testGetListAdmittedPatients() {
        long startTime = System.currentTimeMillis();

        List<Integer> patientsId = List.of(1);
        SDTPController controller = new SDTPController();
        List<Patients> result = controller.GetListAdmittedPatients(patientsId);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Robinson", result.get(0).getSurname());
        assertEquals("Viv", result.get(0).getForename());
        assertEquals("1113335555", result.get(0).getNhsNumber());

        for (Patients patient : result) {
            System.out.println(patient.getId());
            System.out.println(patient.getSurname());
            System.out.println(patient.getForename());
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetListAdmittedPatients - Execution time: " + executionTime + "ms");
    }

    // Test for GetListAdmittedPatients method with multiple patient IDs
    @Test
    public void testGetListAdmittedPatientsMultiplePatientId() {
        long startTime = System.currentTimeMillis();

        List<Integer> patientsId = List.of(1, 2);
        SDTPController controller = new SDTPController();
        List<Patients> result = controller.GetListAdmittedPatients(patientsId);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Robinson", result.get(0).getSurname());
        assertEquals("Viv", result.get(0).getForename());
        assertEquals("1113335555", result.get(0).getNhsNumber());

        assertEquals(2, result.get(1).getId());
        assertEquals("Carter", result.get(1).getSurname());
        assertEquals("Heather", result.get(1).getForename());
        assertEquals("2224446666", result.get(1).getNhsNumber());

        for (Patients patient : result) {
            System.out.println(patient.getId());
            System.out.println(patient.getSurname());
            System.out.println(patient.getForename());
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetListAdmittedPatientsMultiplePatientId - Execution time: " + executionTime + "ms");
    }

    // Test for GetListAdmittedPatients method with null patient IDs
    @Test
    public void testGetListAdmittedPatientsNullPatientId() {
        long startTime = System.currentTimeMillis();

        List<Integer> patientsId = null;
        SDTPController controller = new SDTPController();
        List<Patients> result = controller.GetListAdmittedPatients(patientsId);

        assertNull(result);
        System.out.println(result);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetListAdmittedPatientsNullPatientId - Execution time: " + executionTime + "ms");
    }

}
