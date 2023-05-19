package com.example.doctorApp.repository;

import com.example.doctorApp.model.AuthenticationToken;
import com.example.doctorApp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITokenRepo extends JpaRepository<AuthenticationToken,Long> {


    AuthenticationToken findByPatient(Patient patient);
}
