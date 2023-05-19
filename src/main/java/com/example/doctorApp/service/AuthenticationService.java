package com.example.doctorApp.service;

import com.example.doctorApp.model.AuthenticationToken;
import com.example.doctorApp.model.Patient;
import com.example.doctorApp.repository.IPatientRepo;
import com.example.doctorApp.repository.ITokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    ITokenRepo iTokenRepo;

   public void saveToken(AuthenticationToken token)
   {
       iTokenRepo.save(token);
   }


    public AuthenticationToken getToken(Patient patient) {
       
       return iTokenRepo.findByPatient(patient);
   }
}
