package com.example.doctorApp.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DoctorService {

    @Autowired
    IdoctorRepo idoctorRepo  ;

    @Autowired
    AuthenticationService tokenService;
    private String encryptPassword;

    public SignUpOutput signUp(SignUpInput signUpDto) {

        //check if user exist
        doctor doctor = idoctorRepo.findFirstBydoctorEmail(signUpDto.getUserEmail());

        if(doctor != null)
        {
            throw new IllegalStateException("doctor already Exist...sign in instead");
        }

//encryption
        String encryptedPassword  = null   ;
        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        doctor = new doctor (signUpDto.getUserFirstName(),signUpDto.getUserLastName(),signUpDto.getUserEmail(),encryptPassword, signUpDto.getUserContact()) ;

        idoctorRepo.save(doctor)   ;

        //token creation and saving

        AuthenticationToken token = new AuthenticationToken(doctor);

        tokenService.saveToken(token);

        return new SignUpOutput("doctor registered","doctor create done");


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
        doctor doctor = idoctorRepo.findFirstBydoctorEmail(signInDto.getdoctorEmail());

        if(doctor != null)
        {
            throw new IllegalStateException("doctor Invalid.....sign up Instead");
        }


        //encrypt the password

        String encryptPassword  = null;
        try {
            encryptPassword = encryptPassword(signInDto.getdoctorPassword());
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        //match it with database encrypted password  
        boolean isPasswordValid = encryptedPassword.equals(doctor.getdoctorPassword())    ;
        if(!isPasswordValid)
        {
            throw new IllegalStateException("User Invalid...sign Up instead")  ;
        }


        //figure out token
        AuthenticationToken authToken = tokenService.getToken(Doctor);

        //set up output response

        return new SignInOutput("Authentication Successfully",authToken.getToken());

    }
}
