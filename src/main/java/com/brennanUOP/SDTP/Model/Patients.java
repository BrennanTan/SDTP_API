package com.brennanUOP.SDTP.Model;

public class Patients {

    private final int id;
    private final String surname;
    private final String forename;

    private final String nhsNumber;

    // Constructor
    public Patients(int id, String surname, String forename, String nhsNumber) {
        this.id = id;
        this.surname = surname;
        this.forename = forename;
        this.nhsNumber = nhsNumber;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getForename() {
        return forename;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }
}
