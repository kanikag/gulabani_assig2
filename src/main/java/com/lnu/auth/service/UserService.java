package com.lnu.auth.service;

import com.lnu.auth.entities.Therapy;
import com.lnu.auth.entities.TherapyList;
import com.lnu.auth.entities.User;
import com.lnu.auth.model.PatientInformation;
import com.lnu.auth.model.Test;
import com.lnu.auth.model.TestSession;
import com.lnu.auth.repository.TherapyRepository;
import com.lnu.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    TherapyRepository therapyRepository;

    @Autowired
    ResourceLoader resourceLoader;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Set<User> getPatients(String doctorUsername) {
        User doctor = findByUsername(doctorUsername);
        Set<User> patients = new HashSet<>();
        for (Therapy therapy : doctor.getTherapies()) {
            patients.add(therapy.getPatient());
        }
        return patients;
    }


    public List<PatientInformation> getPatientData(String doctorUsername) {
        Set<User> patients = getPatients(doctorUsername);
        List<PatientInformation> patientsInfo = new ArrayList<>();

        for (User patient : patients) {
            List<com.lnu.auth.model.Therapy> therapies = new ArrayList<>();
            for (Therapy patientTherapy : patient.getPatientTherapies()) {
                TherapyList therapyList = patientTherapy.getTherapyList();
                List<Test> tests = new ArrayList<>();
                for (com.lnu.auth.entities.Test test : patientTherapy.getTests()) {
                    for (com.lnu.auth.entities.TestSession testSession : test.getTestSessions()) {
                        tests.add(new Test(new Date(), readDataFromFile(testSession.getDataUrl(), testSession.getType())));
                    }
                }
                com.lnu.auth.model.Therapy therapy = new com.lnu.auth.model.Therapy(therapyList.getName(), therapyList.getMedicine().getName(), therapyList.getDosage(), tests);
                therapies.add(therapy);
            }


            PatientInformation patientInfo = new PatientInformation(patient.getUserId(), patient.getUsername(), patient.getEmail(), patient.getLat(), patient.getLng(), therapies);
            patientsInfo.add(patientInfo);
        }
        return patientsInfo;
    }

    private List<TestSession> readDataFromFile(String fileName, int testType) {
        List<TestSession> testSessions = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:" + fileName + ".csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    testSessions.add(new TestSession(testType, values[0], values[1], values[2]));
                } else {
                    testSessions.add(new TestSession(testType, values[0], values[1], values[2], values[3], values[4]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(testSessions);
        return testSessions;
    }
}
