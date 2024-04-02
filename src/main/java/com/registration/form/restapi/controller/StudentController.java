package com.registration.form.restapi.controller;
import com.registration.form.restapi.jpaRepository.StudentDetailsRepository;
import com.registration.form.restapi.model.StudentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentDetailsRepository studentDetailsRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @CrossOrigin
    @PostMapping("/addStudent")
    public ResponseEntity<String> postStudentdetails(@RequestBody StudentDetails studentDetails) {
        try {
            String email = studentDetails.getEmailAddress();
            System.out.println(email);
            String emaildatabase =  studentDetailsRepository.findEmailByemailAddress(email);
            System.out.println(emaildatabase);
            if(email.equals(emaildatabase)){
                return ResponseEntity.badRequest().body("User already registered");
            }
            String hashedPassword = passwordEncoder.encode(studentDetails.getPassword());
            studentDetails.setPassword(hashedPassword);
           studentDetailsRepository.save(studentDetails);
            return ResponseEntity.ok().body("Registration successful");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the request");
        }
    }

    @CrossOrigin
    @PostMapping("/login")
    public boolean postlogindetails(@RequestBody StudentDetails studentDetails) {
        try {
            String emailAddress = studentDetails.getEmailAddress();
            String password = studentDetails.getPassword();
            String storedHashedPassword = studentDetailsRepository.findHashedPasswordByemailAddress(emailAddress);
            if (storedHashedPassword != null) {
                boolean passwordMatches = passwordEncoder.matches(password, storedHashedPassword);

                if (passwordMatches) {
                    System.out.println("Existing user");
                    return true;
                } else {
                    System.out.println("User does not exist");
                    return false;
                }
            } else {
                System.out.println("Wrong credentials");
                return false;
            }
        } catch (DataAccessException ex) {
            System.out.println("Failed to find login details: " + studentDetails + " due to database access issue.");
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            System.out.println("Failed to find login details: " + studentDetails + " due to unexpected error: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }
}
