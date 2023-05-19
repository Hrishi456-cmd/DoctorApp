package com.example.doctorApp.service;

import com.example.doctorApp.dto.SignInInput;
import com.example.doctorApp.dto.SignInOutput;
import com.example.doctorApp.dto.SignUpInput;
import com.example.doctorApp.dto.SignUpOutput;
import com.example.doctorApp.model.AuthenticationToken;
import com.example.doctorApp.model.Patient;
import com.example.doctorApp.repository.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PatientService {
@Autowired
    IPatientRepo iPatientRepo  ;

@Autowired
AuthenticationService tokenService;
    private String encryptPassword;

    public SignUpOutput signUp(SignUpInput signUpDto) {

    //check if user exist
        Patient patient = iPatientRepo.findFirstByPatientEmail(signUpDto.getUserEmail());

        if(patient != null)
        {
            throw new IllegalStateException("Patient already Exist...sign in instead");
        }

//encryption
          String encryptedPassword  = null   ;
        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        patient = new Patient (signUpDto.getUserFirstName(),signUpDto.getUserLastName(),signUpDto.getUserEmail(),encryptPassword, signUpDto.getUserContact()) ;

                iPatientRepo.save(patient)   ;

        //token creation and saving

        AuthenticationToken token = new AuthenticationToken(patient);

        tokenService.saveToken(token);

        return new SignUpOutput("Patient registered","patient create done");
        

    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(userPassword.getBytes());
            byte[] digested = md5.digest();

            String hash = DataTypeConverter.printHexBinary(digested)     ;
            return hash;
    }

    public SignInOutput signIn(SignInInput signInDto) {

        //get Email 
         Patient patient = iPatientRepo.findFirstByPatientEmail(signInDto.getPatientEmail());

         if(patient != null)
         {
             throw new IllegalStateException("Patient Invalid.....sign up Instead");
         }


      //encrypt the password

        String encryptPassword  = null;
           try {
               encryptPassword = encryptPassword(signInDto.getPatientPassword());
           }
           catch (NoSuchAlgorithmException e){
               e.printStackTrace();
           }
              //match it with database encrypted password  
          boolean isPasswordValid = encryptedPassword.equals(patient.getPatientPassword())    ;
           if(!isPasswordValid)
           {
               throw new IllegalStateException("User Invalid...sign Up instead")  ;
           }


        //figure out token
       AuthenticationToken authToken = tokenService.getToken(patient);

          //set up output response

              return new SignInOutput("Authentication Successfully",authToken.getToken());

    }
}
