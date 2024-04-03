package com.brennanUOP.SDTP.Controller;

import com.brennanUOP.SDTP.Model.Admission;
import com.brennanUOP.SDTP.Model.Employees;
import com.brennanUOP.SDTP.Model.Patients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

                JSONArray jsonArray = new JSONArray(response.toString());
                return jsonArray;
            }else {
                System.err.println("Request failed: " + responseCode);
            }

            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public JSONArray connectAndReturnJson(String endpoint, int id){
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

                JSONArray jsonArray = new JSONArray(response.toString());
                return jsonArray;
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
    public List<Admission> GetAdmissionsForSpecificPatient(@PathVariable final String patientId){
        List<Admission> admissions = new ArrayList<>();
        try{
            JSONArray jsonArray = connectAndReturnJson("Admissions");

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

    @GetMapping("/F2")
    public List<Patients> GetListAdmittedPatients(){
        //Return list of admitted patients (Not discharged)
        List<Patients> admittedPatientsList = new ArrayList<>();
        //Store patient ids so we can fetch the patients
        List<Integer> patientId = new ArrayList<>();

        try{
            //UOP API url to get data from
            String UOP_APIUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Admissions";
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

                JSONArray jsonArray = new JSONArray(response.toString());
                System.out.println(jsonArray);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (Objects.equals(jsonObject.getString("dischargeDate"), "0001-01-01T00:00:00")) {
                        patientId.add(jsonObject.getInt("patientID"));
                    }
                }
                connection.disconnect();

                    try{
                        for(int p = 0; p < patientId.size(); p++){
                            String patientUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Patients/" + patientId.get(p);
                            URL patientUrlObj = new URL(patientUrl);

                            HttpURLConnection patientConnection = (HttpURLConnection) patientUrlObj.openConnection();
                            patientConnection.setRequestMethod("GET");
                            int patientResponseCode = patientConnection.getResponseCode();

                            if (patientResponseCode == HttpURLConnection.HTTP_OK){
                                BufferedReader patientReader = new BufferedReader(new InputStreamReader(patientConnection.getInputStream()));
                                StringBuilder patientResponse = new StringBuilder();
                                String patientText;

                                while ((patientText = patientReader.readLine()) != null) {
                                    patientResponse.append(patientText);
                                }
                                patientReader.close();

                                JSONObject patientJson = new JSONObject(patientResponse.toString());

                                Patients admittedPatient = new Patients(
                                        patientJson.getInt("id"),
                                        patientJson.getString("surname"),
                                        patientJson.getString("forename"),
                                        patientJson.getString("nhsNumber")
                                );
                                admittedPatientsList.add(admittedPatient);
                            }else {
                                System.err.println("Request failed: " + responseCode);
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }

            }else {
                System.err.println("Request failed: " + responseCode);
            }

            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }

        return admittedPatientsList;
    }

    @GetMapping("/F3")
    public List<Employees> GetMostAdmissionsStaff(){
        List<Employees> mostAdmissionsStaff = new ArrayList<>();

        try{
            //UOP API url to get data from
            String UOP_APIUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Allocations";
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

                JSONArray jsonArray = new JSONArray(response.toString());
                System.out.println(jsonArray);

                // HashMap to store count of admissions for each employee
                HashMap<Integer, Integer> employeeAdmissionsCount = new HashMap<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int employeeId = jsonObject.getInt("employeeID");
                    employeeAdmissionsCount.put(employeeId, employeeAdmissionsCount.getOrDefault(employeeId, 0) + 1);
                }

                // Find the employee(s) with the most admissions
                int maxAdmissions = 0;
                List<Integer> mostAdmissionsStaffID = new ArrayList<>();

                for (int employeeId : employeeAdmissionsCount.keySet()) {
                    int admissions = employeeAdmissionsCount.get(employeeId);
                    if (admissions > maxAdmissions) {
                        maxAdmissions = admissions;
                        mostAdmissionsStaffID.clear(); // Clear the list if a new maximum is found
                        mostAdmissionsStaffID.add(employeeId); // Add the new employee ID with the most admissions
                    }else if (admissions == maxAdmissions) {
                        mostAdmissionsStaffID.add(employeeId); // Add the employee ID if they have the same admissions as the current maximum
                    }
                }

                    try{

                        for(int s = 0; s < mostAdmissionsStaffID.size(); s++){
                            String admissionStaffURL = "https://web.socem.plymouth.ac.uk/COMP2005/api/Employees/" + mostAdmissionsStaffID.get(s);
                            URL admissionStaffUrlObj = new URL(admissionStaffURL);

                            HttpURLConnection admissionStaffConnection = (HttpURLConnection) admissionStaffUrlObj.openConnection();
                            admissionStaffConnection.setRequestMethod("GET");
                            int admissionStaffResponseCode = admissionStaffConnection.getResponseCode();

                            if (admissionStaffResponseCode == HttpURLConnection.HTTP_OK){
                                BufferedReader admissionStaffReader = new BufferedReader(new InputStreamReader(admissionStaffConnection.getInputStream()));
                                StringBuilder admissionStaffResponse = new StringBuilder();
                                String admissionStaffText;

                                while ((admissionStaffText = admissionStaffReader.readLine()) != null) {
                                    admissionStaffResponse.append(admissionStaffText);
                                }
                                admissionStaffReader.close();

                                JSONObject admissionStaffJson = new JSONObject(admissionStaffResponse.toString());

                                Employees highestAdmissionStaff = new Employees(
                                        admissionStaffJson.getInt("id"),
                                        admissionStaffJson.getString("surname"),
                                        admissionStaffJson.getString("forename")
                                );
                                mostAdmissionsStaff.add(highestAdmissionStaff);
                            }else {
                                System.err.println("Request failed: " + responseCode);
                            }
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }

            }else {
                System.err.println("Request failed: " + responseCode);
            }

            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }

        return mostAdmissionsStaff;
    }

    @GetMapping("/F4")
    public List<Employees> GetListOfNoAdmissionStaff(){
        List<Employees> noAdmissionStaff = new ArrayList<>();

        try{
            // UOP API url to get data from
            String allocationsUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Allocations";
            URL allocationsUrlObj = new URL(allocationsUrl);

            HttpURLConnection allocationsConnection = (HttpURLConnection) allocationsUrlObj.openConnection();
            allocationsConnection.setRequestMethod("GET");

            int allocationsResponseCode = allocationsConnection.getResponseCode();
            if (allocationsResponseCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(allocationsConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String text;

                while ((text = reader.readLine()) != null){
                    response.append(text);
                }
                reader.close();

                // Assuming you have the admissions data as a JSON array
                JSONArray admissionsData = new JSONArray(response.toString());

                Set<Integer> staffIdsWithAdmissions = new HashSet<>();
                for (int i = 0; i < admissionsData.length(); i++) {
                    JSONObject admission = admissionsData.getJSONObject(i);
                    int employeeId = admission.getInt("employeeID");
                    staffIdsWithAdmissions.add(employeeId);
                }

                // Assuming you have the staff data as a JSON array
                String staffUrl = "https://web.socem.plymouth.ac.uk/COMP2005/api/Employees";
                URL staffUrlObj = new URL(staffUrl);

                HttpURLConnection staffConnection = (HttpURLConnection) staffUrlObj.openConnection();
                staffConnection.setRequestMethod("GET");

                int staffResponseCode = staffConnection.getResponseCode();
                if (staffResponseCode == HttpURLConnection.HTTP_OK){
                    reader = new BufferedReader(new InputStreamReader(staffConnection.getInputStream()));
                    response = new StringBuilder();

                    while ((text = reader.readLine()) != null){
                        response.append(text);
                    }
                    reader.close();

                    JSONArray staffData = new JSONArray(response.toString());

                    for (int i = 0; i < staffData.length(); i++) {
                        JSONObject staff = staffData.getJSONObject(i);
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
                }else {
                    System.err.println("Request failed: " + staffResponseCode);
                }

                staffConnection.disconnect();
            }else {
                System.err.println("Request failed: " + allocationsResponseCode);
            }

            allocationsConnection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }

        return noAdmissionStaff;
    }
    
}

