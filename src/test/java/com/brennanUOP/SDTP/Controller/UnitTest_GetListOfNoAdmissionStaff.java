package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Employees;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest_GetListOfNoAdmissionStaff {

    // Test for GetListStaffIdsWithAdmissions method
    @Test
    public void testGetListStaffIdsWithAdmissions() {
        long startTime = System.currentTimeMillis();

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(
                    "[{\"id\":1,\"admissionID\":1,\"employeeID\":4,\"startTime\":\"2020-11-28T16:45:00\",\"endTime\":\"2020-11-28T23:56:00\"}," +
                            "{\"id\":2,\"admissionID\":3,\"employeeID\":4,\"startTime\":\"2021-09-23T21:50:00\",\"endTime\":\"2021-09-24T09:50:00\"}," +
                            "{\"id\":3,\"admissionID\":2,\"employeeID\":6,\"startTime\":\"2020-12-07T22:14:00\",\"endTime\":\"2020-12-08T20:00:00\"}," +
                            "{\"id\":4,\"admissionID\":2,\"employeeID\":3,\"startTime\":\"2020-12-08T20:00:00\",\"endTime\":\"2020-12-09T20:00:00\"}]"
            );
            SDTPController controller = new SDTPController();
            Set<Integer> result = controller.GetListStaffIdsWithAdmissions(jsonArray);

            assertEquals(3, result.size());
            System.out.println(result);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetListStaffIdsWithAdmissions - Execution time: " + executionTime + "ms");
    }

    // Test for GetListStaffIdsWithAdmissions method with empty JSONArray
    @Test
    public void testGetListStaffIdsWithAdmissionsEmptyJsonArray() {
        long startTime = System.currentTimeMillis();

        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray("[]");
            SDTPController controller = new SDTPController();
            Set<Integer> result = controller.GetListStaffIdsWithAdmissions(jsonArray);

            assertNull(result);
            System.out.println(result);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetListStaffIdsWithAdmissionsEmptyJsonArray - Execution time: " + executionTime + "ms");
    }

    // Test for GetListOfNoAdmissionStaff method
    @Test
    public void testGetListOfNoAdmissionStaff() {
        long startTime = System.currentTimeMillis();

        Set<Integer> staffIdsWithAdmissions = Set.of(3, 4, 6);
        SDTPController controller = new SDTPController();
        List<Employees> result = controller.GetListOfNoAdmissionStaff(staffIdsWithAdmissions);

        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Finley", result.get(0).getSurname());
        assertEquals("Sarah", result.get(0).getForename());

        assertEquals(2, result.get(1).getId());
        assertEquals("Jackson", result.get(1).getSurname());
        assertEquals("Robert", result.get(1).getForename());

        assertEquals(5, result.get(2).getId());
        assertEquals("Wicks", result.get(2).getSurname());
        assertEquals("Patrick", result.get(2).getForename());

        for (Employees employee : result) {
            System.out.println(employee.getId());
            System.out.println(employee.getSurname());
            System.out.println(employee.getForename());
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetListOfNoAdmissionStaff - Execution time: " + executionTime + "ms");
    }

    // Test for GetListOfNoAdmissionStaff method with null staff IDs
    @Test
    public void testGetListOfNoAdmissionStaffNullIDs() {
        long startTime = System.currentTimeMillis();

        Set<Integer> staffIdsWithAdmissions = null;
        SDTPController controller = new SDTPController();
        List<Employees> result = controller.GetListOfNoAdmissionStaff(staffIdsWithAdmissions);

        assertNull(result);
        System.out.println(result);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetListOfNoAdmissionStaffNullIDs - Execution time: " + executionTime + "ms");
    }

    // Test for GetListOfNoAdmissionStaff method with empty staff IDs
    @Test
    public void testGetListOfNoAdmissionStaffEmptyIDs() {
        long startTime = System.currentTimeMillis();

        Set<Integer> staffIdsWithAdmissions = Set.of();
        SDTPController controller = new SDTPController();
        List<Employees> result = controller.GetListOfNoAdmissionStaff(staffIdsWithAdmissions);

        assertNull(result);
        System.out.println(result);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("testGetListOfNoAdmissionStaffEmptyIDs - Execution time: " + executionTime + "ms");
    }

}
