package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Admission;
import com.brennanUOP.SDTP.Model.Employees;
import com.brennanUOP.SDTP.Model.Patients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
@RestController
public class SDTPController {
    public JSONArray connectAndReturnJson(String endpoint){
        try{
            //UOP API url to get data from
            String UOP_APIUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/" + endpoint;
            URL url = new URL(UOP_APIUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String text;

                while ((text = reader.readLine()) != null){
                    response.append(text);
                }
                reader.close();

                return new JSONArray(response.toString());
            }else {
                System.err.println("Request failed: " + responseCode);
            }

            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public JSONObject connectAndReturnJson(String endpoint, String id){
        try{
            //UOP API url to get data from
            String UOP_APIUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/" + endpoint + "/" + id;
            URL url = new URL(UOP_APIUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String text;

                while ((text = reader.readLine()) != null){
                    response.append(text);
                }
                reader.close();

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.toString());
                } catch (JSONException e) {
                    System.err.println("Error parsing JSON response:");
                    System.err.println(response);
                    e.printStackTrace();
                }

                return jsonObject;
            }else {
                System.err.println("Request failed: " + responseCode);
            }

            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/F1/{patientId}")
    public List<Admission> GetAdmissionsForSpecificPatientEndpoint(@PathVariable final String patientId) {
        var jsonArray = connectAndReturnJson("Admissions");
        return GetAdmissionsForSpecificPatient(jsonArray,patientId);
    }
    @GetMapping("/F2")
    public List<Patients> GetListAdmittedPatientsEndpoint() {
        var jsonArray = connectAndReturnJson("Admissions");
        var ListAdmittedPatientsId = GetListAdmittedPatientsId(jsonArray);
        return GetListAdmittedPatients(ListAdmittedPatientsId);
    }
    @GetMapping("/F3")
    public List<Employees> GetMostAdmissionsStaffEndpoint() {
        var jsonArray = connectAndReturnJson("Allocations");
        var EmployeeAdmissionsCount = GetEmployeeAdmissionsCount(jsonArray);
        return GetMostAdmissionsStaff(EmployeeAdmissionsCount);
    }
    @GetMapping("/F4")
    public List<Employees> GetListOfNoAdmissionStaffEndpoint() {
        var jsonArray = connectAndReturnJson("Allocations");
        var ListStaffIdsWithAdmissions = GetListStaffIdsWithAdmissions(jsonArray);
        return GetListOfNoAdmissionStaff(ListStaffIdsWithAdmissions);
    }

    public List<Admission> GetAdmissionsForSpecificPatient(JSONArray jsonArray, String patientId){
        List<Admission> admissions = new ArrayList<>();
        try{
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getInt("patientID") == Integer.parseInt(patientId)) {
                    Admission admission = new Admission(
                            jsonObject.getInt("id"),
                            jsonObject.getString("admissionDate"),
                            jsonObject.getString("dischargeDate"),
                            jsonObject.getInt("patientID")
                    );
                    admissions.add(admission);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return admissions;
    }

    public List<Integer> GetListAdmittedPatientsId(JSONArray admissionsJsonArray){
        //Store patient ids so we can fetch the patients
        List<Integer> patientId = new ArrayList<>();
        try{
            if (admissionsJsonArray != null) {
                for (int i = 0; i < admissionsJsonArray.length(); i++) {
                    JSONObject jsonObject = admissionsJsonArray.getJSONObject(i);
                    if (Objects.equals(jsonObject.getString("dischargeDate"), "0001-01-01T00:00:00")) {
                        patientId.add(jsonObject.getInt("patientID"));
                    }
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return patientId;
    }

    public List<Patients> GetListAdmittedPatients(List<Integer> patientId){
        //Return list of admitted patients (Not discharged)
        List<Patients> admittedPatientsList = new ArrayList<>();

        try{
            for (int p = 0; p < patientId.size(); p++) {
                JSONObject patientJsonObject = connectAndReturnJson("Patients", String.valueOf(patientId.get(p)));
                if (patientJsonObject != null) {
                    Patients admittedPatient = new Patients(
                            patientJsonObject.getInt("id"),
                            patientJsonObject.getString("surname"),
                            patientJsonObject.getString("forename"),
                            patientJsonObject.getString("nhsNumber")
                    );
                    admittedPatientsList.add(admittedPatient);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return admittedPatientsList;
    }

    public HashMap<Integer, Integer> GetEmployeeAdmissionsCount(JSONArray allocationsJsonArray){
        HashMap<Integer, Integer> employeeAdmissionsCount = new HashMap<>();
        try{
            if (allocationsJsonArray != null) {

                for (int i = 0; i < allocationsJsonArray.length(); i++) {
                    JSONObject jsonObject = allocationsJsonArray.getJSONObject(i);

                    int employeeId = jsonObject.getInt("employeeID");
                    employeeAdmissionsCount.put(employeeId, employeeAdmissionsCount.getOrDefault(employeeId, 0) + 1);
                }
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return employeeAdmissionsCount;
    }

    public List<Employees> GetMostAdmissionsStaff(HashMap<Integer, Integer> employeeAdmissionsCount){
        List<Employees> mostAdmissionsStaff = new ArrayList<>();

        try{
                int maxAdmissions = 0;
                List<Integer> mostAdmissionsStaffID = new ArrayList<>();

                for (int employeeId : employeeAdmissionsCount.keySet()) {
                    int admissions = employeeAdmissionsCount.get(employeeId);
                    if (admissions > maxAdmissions) {
                        maxAdmissions = admissions;
                        mostAdmissionsStaffID.clear();
                        mostAdmissionsStaffID.add(employeeId);
                    } else if (admissions == maxAdmissions) {
                        mostAdmissionsStaffID.add(employeeId);
                    }
                }

                for (int s = 0; s < mostAdmissionsStaffID.size(); s++) {
                    JSONObject employeeJsonObject = connectAndReturnJson("Employees", String.valueOf(mostAdmissionsStaffID.get(s)));
                    if (employeeJsonObject != null) {
                        Employees highestAdmissionStaff = new Employees(
                                employeeJsonObject.getInt("id"),
                                employeeJsonObject.getString("surname"),
                                employeeJsonObject.getString("forename")
                        );
                        mostAdmissionsStaff.add(highestAdmissionStaff);
                    }
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return mostAdmissionsStaff;
    }

    public Set<Integer> GetListStaffIdsWithAdmissions(JSONArray allocationsJsonArray){
        Set<Integer> staffIdsWithAdmissions = new HashSet<>();
        try{
            if (allocationsJsonArray != null) {
                for (int i = 0; i < allocationsJsonArray.length(); i++) {
                    JSONObject admission = allocationsJsonArray.getJSONObject(i);
                    int employeeId = admission.getInt("employeeID");
                    staffIdsWithAdmissions.add(employeeId);
                }
            }else{
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return staffIdsWithAdmissions;
    }

    public List<Employees> GetListOfNoAdmissionStaff(Set<Integer> staffIdsWithAdmissions){
        List<Employees> noAdmissionStaff = new ArrayList<>();
        try{
                JSONArray staffJsonArray = connectAndReturnJson("Employees");
                if (staffJsonArray != null) {
                    for (int i = 0; i < staffJsonArray.length(); i++) {
                        JSONObject staff = staffJsonArray.getJSONObject(i);
                        int staffId = staff.getInt("id");
                        if (!staffIdsWithAdmissions.contains(staffId)) {
                            Employees staffMember = new Employees(
                                    staffId,
                                    staff.getString("surname"),
                                    staff.getString("forename")
                            );
                            noAdmissionStaff.add(staffMember);
                        }
                    }
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return noAdmissionStaff;
    }
}

