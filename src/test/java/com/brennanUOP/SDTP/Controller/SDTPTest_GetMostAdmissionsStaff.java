package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Employees;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SDTPTest_GetMostAdmissionsStaff {

    @Test
    public void  testGetEmployeeAdmissionsCount(){
        JSONArray jsonArray;
        try{
            jsonArray = new JSONArray(
                    "[{\"id\":1,\"admissionID\":1,\"employeeID\":4,\"startTime\":\"2020-11-28T16:45:00\",\"endTime\":\"2020-11-28T23:56:00\"}," +
                            "{\"id\":2,\"admissionID\":3,\"employeeID\":4,\"startTime\":\"2021-09-23T21:50:00\",\"endTime\":\"2021-09-24T09:50:00\"}," +
                            "{\"id\":3,\"admissionID\":2,\"employeeID\":6,\"startTime\":\"2020-12-07T22:14:00\",\"endTime\":\"2020-12-08T20:00:00\"}," +
                            "{\"id\":4,\"admissionID\":2,\"employeeID\":3,\"startTime\":\"2020-12-08T20:00:00\",\"endTime\":\"2020-12-09T20:00:00\"}]"
            );
            SDTPController controller = new SDTPController();
            HashMap<Integer, Integer> result = controller.GetEmployeeAdmissionsCount(jsonArray);

            assertEquals(3, result.size());
            System.out.println(result);
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }
    @Test
    public void  testGetEmployeeAdmissionsCountNullJsonArray(){
        JSONArray jsonArray;
        try{
            jsonArray = new JSONArray("[]");
            SDTPController controller = new SDTPController();
            HashMap<Integer, Integer> result = controller.GetEmployeeAdmissionsCount(jsonArray);

            assertNull(result);
            System.out.println(result);
        }catch (JSONException e){
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testGetMostAdmissionsStaff() {
        HashMap<Integer, Integer> employeeAdmissionsCount = new HashMap<>();
        employeeAdmissionsCount.put(3,1);
        employeeAdmissionsCount.put(4,2);
        employeeAdmissionsCount.put(6,1);

        SDTPController controller = new SDTPController();
        List<Employees> result = controller.GetMostAdmissionsStaff(employeeAdmissionsCount);

        assertEquals(1, result.size());
        assertEquals(4, result.get(0).getId());
        assertEquals("Jones", result.get(0).getSurname());
        assertEquals("Sarah", result.get(0).getForename());

        System.out.println(result);
    }
    @Test
    public void testGetMostAdmissionsStaffNullCount() {
        HashMap<Integer, Integer> employeeAdmissionsCount = null;
        SDTPController controller = new SDTPController();
        List<Employees> result = controller.GetMostAdmissionsStaff(employeeAdmissionsCount);

        assertNull(result);
        System.out.println(result);
    }
    @Test
    public void testGetMostAdmissionsStaffEmptyCount() {
        HashMap<Integer, Integer> employeeAdmissionsCount = new HashMap<>();
        SDTPController controller = new SDTPController();
        List<Employees> result = controller.GetMostAdmissionsStaff(employeeAdmissionsCount);

        assertNull(result);
        System.out.println(result);
    }
}