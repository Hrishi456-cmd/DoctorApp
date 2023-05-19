package com.example.doctorApp.repository;

import com.example.doctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IPatientRepo extends JpaRepository<Patient,Long> {

    Patient findByUser(Long userId);

    Patient findFirstByPatientEmail(String userEmail);
}
