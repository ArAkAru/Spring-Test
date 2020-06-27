package com.dins.arakaru;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import com.dins.arakaru.DTO.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@SpringBootTest
public class NegativeTest {
	@Test
	void negativeTestForAdd() {
		TestRestTemplate restTemplate = new TestRestTemplate();
		User user1 = new User("A", "Arut");
		User user16 = new User("ArkadiyArkadiyAr", "Arut");
		ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:8080/users", user1, User.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		response = restTemplate.postForEntity("http://localhost:8080/users", user16, User.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		User empty = new User("","Arut");
		response = restTemplate.postForEntity("http://localhost:8080/users", empty, User.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		User symbols = new User("\n\r\t","Arut");
		response = restTemplate.postForEntity("http://localhost:8080/users", symbols, User.class);
		assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
		
		
	}
	
	@Test
	void negativeTestForGetByID() {
		TestRestTemplate restTemplate = new TestRestTemplate();
		int id=-1;
		ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:8080/users/"+id, User.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
		char symbols ='a';
		restTemplate.getForEntity("http://localhost:8080/users/"+symbols, User.class);
		assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
		
	}
	
	
	
	
}
