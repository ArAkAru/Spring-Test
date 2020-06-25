package com.dins.arakaru;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import com.dins.arakaru.DTO.User;


@SpringBootTest
class ArakaruApplicationTests {
	private TestRestTemplate restTemplate;
	@BeforeEach
	void init() {
		restTemplate = new TestRestTemplate();
		
		
	}
	
	@Test
	void add() {
		
		User user1 = new User(10,"fssa322ss","sdvanov");
		 
		ResponseEntity<User> response=restTemplate.postForEntity("http://localhost:8080/users", user1, User.class);
			assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		
		    assertThat(response.getBody().getId(), notNullValue());
		    assertThat(response.getBody().getFirstName(), is("fssa322ss"));
		    assertThat(response.getBody().getLastName(), is("sdvanov"));
		    
		   
	}
	
	@Test
	void getById() {
		
		restTemplate = new TestRestTemplate();
		ResponseEntity<List<User>> response=restTemplate.exchange("http://localhost:8080/users", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
		int id = response.getBody().get(response.getBody().size()-1).getId()+1;
		User tempUser = new User(id,"TEMP66","TEMP");
		restTemplate.postForEntity("http://localhost:8080/users", tempUser, User.class);
		ResponseEntity<User> userresponse=restTemplate.getForEntity("http://localhost:8080/users/"+id, User.class);
		assertThat(userresponse.getStatusCode(), is(HttpStatus.OK));
		assertThat(userresponse.getBody().getFirstName(), is("TEMP66"));
		assertThat(userresponse.getBody().getLastName(), is("TEMP"));
		
	}


}
