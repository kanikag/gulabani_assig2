package com.lnu.auth.service;

import com.lnu.auth.entities.Therapy;
import com.lnu.auth.entities.User;
import com.lnu.auth.model.PatientInformation;
import com.lnu.auth.repository.TherapyRepository;
import com.lnu.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    TherapyRepository therapyRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

   public void getPatientData(User doctor) {
       List<Therapy> therapies = therapyRepository.findByDoctor(doctor);

   }
}
