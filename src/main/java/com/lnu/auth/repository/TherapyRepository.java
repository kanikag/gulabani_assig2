package com.lnu.auth.repository;

import com.lnu.auth.entities.Therapy;
import com.lnu.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TherapyRepository extends JpaRepository<Therapy, Long> {
    List<Therapy> findByDoctor(User doctor);
}
