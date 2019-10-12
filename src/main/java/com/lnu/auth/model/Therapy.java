package com.lnu.auth.model;

import java.util.List;

public class Therapy {
    private String therapyName;
    private String medicine;
    private String dosage;
    List<Test> tests;

    public Therapy(String therapyName, String medicine, String dosage, List<Test> tests) {
        this.therapyName = therapyName;
        this.medicine = medicine;
        this.dosage = dosage;
        this.tests = tests;
    }

    public String getTherapyName() {
        return therapyName;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public List<Test> getTests() {
        return tests;
    }
}
