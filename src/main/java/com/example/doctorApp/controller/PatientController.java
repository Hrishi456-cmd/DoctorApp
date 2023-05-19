package com.example.doctorApp.controller;

import com.example.doctorApp.dto.SignInInput;
import com.example.doctorApp.dto.SignInOutput;
import com.example.doctorApp.dto.SignUpInput;
import com.example.doctorApp.dto.SignUpOutput;
import com.example.doctorApp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

@Autowired
    PatientService patientService;

//sign up
@PostMapping("/signup")
    public SignUpOutput signup(@RequestBody SignUpInput signUpDto)
{
    return patientService.signUp(signUpDto);
}

    //sign in
    @PostMapping("/signin")
    public SignInOutput signup(@RequestBody SignInInput signInDto)
    {
        return patientService.signIn(signInDto);
    }
}
