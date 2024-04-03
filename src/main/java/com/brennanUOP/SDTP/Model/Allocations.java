package com.brennanUOP.SDTP.Model;

public class Allocations {

    private final int id;
    private final int admissionID;
    private final int employeeID;
    private final String startTime;
    private final String endTime;

    // Constructor
    public Allocations(int id, int admissionID, int employeeID, String startTime, String endTime) {
        this.id = id;
        this.admissionID = admissionID;
        this.employeeID = employeeID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getAdmissionID() {
        return admissionID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
