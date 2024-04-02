package com.registration.form.restapi.jpaRepository;

import com.registration.form.restapi.model.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface StudentDetailsRepository extends JpaRepository<StudentDetails, Integer> {

    @Query("SELECT s.password FROM StudentDetails s WHERE s.emailAddress = ?1")
    String findHashedPasswordByemailAddress(@Param("emailAddress") String emailAddress);
    @Query("SELECT s.emailAddress From StudentDetails s WHERE s.emailAddress = :email")
    String findEmailByemailAddress(String email);
}
