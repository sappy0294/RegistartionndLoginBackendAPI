package com.registration.form.restapi;
import com.registration.form.restapi.controller.StudentController;
import com.registration.form.restapi.jpaRepository.StudentDetailsRepository;
import com.registration.form.restapi.model.StudentDetails;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RestapiApplicationTests {

	@Mock
	private StudentDetailsRepository studentDetailsRepository;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	private StudentController studentController;


	@Test
	public void postStudentdetailsTest() {
		StudentDetails studentDetails = getStudentDetails();
		String hashedPassword = "$2a$10$1cl5e4Tnxes4PfB2Q1nR4O9/ZYlJcP0qRfJ.FPE3zCxKpPSg4soSu";
		when(passwordEncoder.encode(studentDetails.getPassword())).thenReturn(hashedPassword);
		when(studentDetailsRepository.save(studentDetails)).thenReturn(studentDetails);
		ResponseEntity<String> responseEntity  = studentController.postStudentdetails(studentDetails);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Registration successful", responseEntity.getBody());
		verify(studentDetailsRepository, times(1)).findEmailByemailAddress(studentDetails.getEmailAddress());
		verify(studentDetailsRepository, times(1)).save(studentDetails);
	}

	@Test
	public void postlogindetailsExistingTest(){
		StudentDetails studentDetails = new StudentDetails();
		studentDetails.setEmailAddress("nareshba@gmail.com");
		studentDetails.setPassword("123");
		String storedHashedPassword = "$2a$10$1cl5e4Tnxes4PfB2Q1nR4O9/ZYlJcP0qRfJ.FPE3zCxKpPSg4soSu";
		when(studentDetailsRepository.findHashedPasswordByemailAddress("nareshba@gmail.com")).thenReturn(storedHashedPassword);
		when(passwordEncoder.matches(studentDetails.getPassword(), storedHashedPassword)).thenReturn(true);
		boolean result = studentController.postlogindetails(studentDetails);
		assertEquals(true, result);
		verify(studentDetailsRepository,times(1)).findHashedPasswordByemailAddress("nareshba@gmail.com");
		verify(passwordEncoder, times(1)).matches(studentDetails.getPassword(), storedHashedPassword);
	}
	@Test
	public void postlogindetailsNewTest(){
		StudentDetails studentDetails = new StudentDetails();
		studentDetails.setEmailAddress("nareshrb@gmail.com");
		studentDetails.setPassword("123");
		String storedHashedPassword = "$123";
		when(studentDetailsRepository.findHashedPasswordByemailAddress("nareshrb@gmail.com")).thenReturn(null);
		boolean result = studentController.postlogindetails(studentDetails);
		assertFalse(result);
		verify(studentDetailsRepository,times(1)).findHashedPasswordByemailAddress("nareshrb@gmail.com");
	}
	@Test
	public void postlogindetailsDataExceptionTest(){
		StudentDetails studentDetails = new StudentDetails();
		studentDetails.setEmailAddress("naresh@gmail.com");
		studentDetails.setPassword("1234");
		when(studentDetailsRepository.findHashedPasswordByemailAddress("naresh@gmail.com")).thenThrow(new DataAccessResourceFailureException("Test exception"));
		boolean result = studentController.postlogindetails(studentDetails);
		assertFalse(result);
		verify(studentDetailsRepository,times(1)).findHashedPasswordByemailAddress("naresh@gmail.com");

	}
	@Test
	public void postlogindetailsExceptionTest(){
		StudentDetails studentDetails = new StudentDetails();
		studentDetails.setEmailAddress("naresh@gmail.com");
		studentDetails.setPassword("1234");
		when(studentDetailsRepository.findHashedPasswordByemailAddress("naresh@gmail.com"))
				.thenThrow(new Exception("Data Exception"));
		boolean result = studentController.postlogindetails(studentDetails);
		assertFalse(result);
		verify(studentDetailsRepository,times(1)).findHashedPasswordByemailAddress("naresh@gmail.com");

	}

	private StudentDetails getStudentDetails(){
		return StudentDetails.builder().firstName("fName")
				.lastName("lName")
				.city("tCity")
				.password("tPassword")
				.build();
	}

}
