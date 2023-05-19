package com.example.doctorApp.repository;

public interface IDoctorRepo {
    doctor findByUser(Long userId);

    doctor findFirstBydoctorEmail(String userEmail);
}